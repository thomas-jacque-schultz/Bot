package schultz.thomas.discord.bot.business.services;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.requests.RestAction;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import schultz.thomas.discord.bot.model.entity.ChannelEntity;
import schultz.thomas.discord.bot.model.entity.GamingServerEntity;
import schultz.thomas.discord.bot.model.entity.MessageEntity;
import schultz.thomas.discord.bot.model.repository.ChannelRepository;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.awt.*;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Slf4j
@RequiredArgsConstructor
@Service
public class DiscordMessageService {

    private final ChannelRepository channelRepository;

    private List<ChannelEntity> subscribedChannelsCache;

    @PostConstruct
    public void init() {
        subscribedChannelsCache = channelRepository.findAll();
    }

    public boolean subscribeDiscordChannel(ChannelEntity channelEntity) {
        if (subscribedChannelsCache.stream().anyMatch(channel -> channel.getChannelId().equals(channelEntity.getChannelId()))) {
            throw new EntityExistsException("Channel already exists");
        }
        return subscribedChannelsCache.add(channelRepository.save(channelEntity));
    }

    public boolean unsubscribeDiscordChannel(ChannelEntity channelEntity) {
        if (subscribedChannelsCache.stream().noneMatch(channel -> channel.getChannelId().equals(channelEntity.getChannelId()))) {
            throw new EntityNotFoundException("Channel doesn't exist");
        }
        subscribedChannelsCache.remove(channelEntity);
        channelRepository.saveAll(subscribedChannelsCache);
        return true;
    }

    /**
        * Sends a message to the channel with the Game server name
        * if successful return save the message ID
     * @param channel  the channel to send the message
     * @param gamingServerEntity  the server to send
     * @return the message ID
     */
    public void sendMessage(ChannelEntity channel, GamingServerEntity gamingServerEntity, JDA jda) {
        TextChannel textChannel = jda.getTextChannelById(channel.getChannelId());
        if (textChannel == null) {
            log.error("TextChannel with ID {} not found", channel.getChannelId());
        }
        textChannel.sendMessageEmbeds(createEmbedFromServer(gamingServerEntity)).queue(
                message -> {
                    MessageEntity messageEntity = new MessageEntity();
                    messageEntity.setEntityId(gamingServerEntity.getId());
                    messageEntity.setMessageId(message.getId());
                    channel.getMessages().add(messageEntity);
                    channelRepository.save(channel);
                    log.info("Message sent and saved for GamingServerEntity ID {}", gamingServerEntity.getId());
                },
                throwable -> log.error("Failed to send message for GamingServerEntity ID {}", gamingServerEntity.getId(), throwable)
        );
    }

    /**
     * Updates the message in the channel with the Game server name
     * if failed : remove the message and call sendMessage
     */
    public void updateMessageOrCreate(GamingServerEntity gamingServerEntity, ChannelEntity channel, MessageEntity message, JDA jda) {
        TextChannel textChannel = jda.getTextChannelById(channel.getChannelId());
        if (textChannel == null) {
            log.error("TextChannel with ID {} not found", channel.getChannelId());
        }
        textChannel.editMessageEmbedsById(message.getMessageId(), createEmbedFromServer(gamingServerEntity)).queue(
                success -> log.info("Message updated for GamingServerEntity ID {}", gamingServerEntity.getId()),
                failure -> {
                    log.warn("Failed to update message for GamingServerEntity ID {}, recreating message", gamingServerEntity.getId());
                    channel.getMessages().remove(message);
                    channelRepository.save(channel);
                    sendMessage(channel, gamingServerEntity, jda);
                }
        );
    }

    /**
     * Handle the three situations
     * 1. The message doesn't exist, we create it
     * 2. The message exists we update it
     * 3. The message where deleted outside of the bot, we recreate it
     * @param gsEntity the server to handle
     */
    public void createOrUpdateMessageForGamingServerEntity(GamingServerEntity gsEntity, JDA jda) {
        subscribedChannelsCache.forEach(channelEntity -> {
            MessageEntity existingMessage = channelEntity.getMessages().stream()
                    .filter(messageEntity -> messageEntity.getEntityId().equals(gsEntity.getId()))
                    .findFirst()
                    .orElse(null);

            if (existingMessage == null) {
                sendMessage(channelEntity, gsEntity, jda);
            } else {
                updateMessageOrCreate(gsEntity, channelEntity, existingMessage, jda);
            }
        });
    }

    /**
        * With all fields of the serverEntity, we can create a message a embeded message
     */
    private MessageEmbed createEmbedFromServer(GamingServerEntity gamingServerEntity) {
        // Création d'un EmbedBuilder
        EmbedBuilder embedBuilder = new EmbedBuilder();

        // Définir le titre comme le nom du serveur
        if(gamingServerEntity.getName() == null || gamingServerEntity.getName().isEmpty()){
            embedBuilder.setTitle(gamingServerEntity.getName());
        }
        else {
            embedBuilder.setTitle(gamingServerEntity.getGameName().getGameName() + " - " + gamingServerEntity.getName());
        }
        embedBuilder.setColor(gamingServerEntity.isRunning() ? Color.GREEN : Color.RED);

        // Ajouter les champs principaux
        embedBuilder.addField("URL : ```" + gamingServerEntity.getUrlConnection()+ "```","", false);
        embedBuilder.addField("Nombre de joueurs max", String.valueOf(gamingServerEntity.getPlayersMax()), true);
        embedBuilder.addField("Version", Objects.requireNonNullElse(gamingServerEntity.getVersion(),""), true);

        if (gamingServerEntity.getInstallation() != null && !gamingServerEntity.getInstallation().isEmpty()) {
            embedBuilder.addField("Installation :", gamingServerEntity.getInstallation(), false);
        }

        // Ajouter une description si elle existe
        if (gamingServerEntity.getDescription() != null && !gamingServerEntity.getDescription().isEmpty()) {
            embedBuilder.setDescription(gamingServerEntity.getDescription());
        }

        // Ajouter les auteurs autorisés si la liste n'est pas vide
        if (gamingServerEntity.getAdmins() != null && !gamingServerEntity.getAdmins().isEmpty()) {
            String authors = gamingServerEntity.getAdmins().stream().collect(Collectors.joining(", "));
            embedBuilder.addField("Admin :", authors, false);
        }

        // Ajouter un pied de page avec l'ID du serveur
        embedBuilder.setFooter("Statut : " +  (gamingServerEntity.isRunning() ? "\uD83D\uDFE2":"\uD83D\uDD34"), null);

        embedBuilder.setThumbnail(gamingServerEntity.getGameName().getIconUrl());

        // Retourner l'embed
        return embedBuilder.build();
    }

    public void deleteMessageForGamingServerEntity(GamingServerEntity gamingServerEntity, JDA jda) {
    }
}
