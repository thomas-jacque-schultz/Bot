package schultz.thomas.discord.bot.business.services;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import schultz.thomas.discord.bot.model.entity.ChannelEntity;
import schultz.thomas.discord.bot.model.entity.GamingServerEntity;
import schultz.thomas.discord.bot.model.entity.MessageEntity;
import schultz.thomas.discord.bot.model.repository.ChannelRepository;

import java.awt.*;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class DiscordMessageService {


    private final JDA jda;

    private final ChannelRepository channelRepository;

    private List<ChannelEntity> subscribedChannelsCache;

    @PostConstruct
    public void init() {
        subscribedChannelsCache = channelRepository.findAll();
    }

    public boolean subscribeDiscordChannel(ChannelEntity channelEntity) {
        if (subscribedChannelsCache.stream().anyMatch(channel -> channel.getChannelId().equals(channelEntity.getChannelId()))) {
            throw new IllegalArgumentException("Channel already exists");
        }
        return subscribedChannelsCache.add(channelRepository.save(channelEntity));
    }

    /**
        * Sends a message to the channel with the Game server name
        * Only if the server is not already in the channel
     * @param channel  the channel to send the message
     * @param gamingServerEntity  the server to send
     * @return the message ID
     */
    public String sendMessage(ChannelEntity channel, GamingServerEntity gamingServerEntity) {
        TextChannel channelDiscord = jda.getTextChannelById(channel.getId());
        return channelDiscord.sendMessageEmbeds(createEmbedFromServer(gamingServerEntity)).complete().getId();
    }

    /**
        * Updates the message in the channel with the Game server name
     */
    public String updateMessage(GamingServerEntity gamingServerEntity,ChannelEntity channel, Message message) {
        return message.editMessageEmbeds(createEmbedFromServer(gamingServerEntity)).complete().getId();
    }

    public void deleteMessageForGamingServerEntity(GamingServerEntity gamingServerEntity, JDA jda) {
    }


    public void createOrUpdateMessageForGamingServerEntity(GamingServerEntity gsEntity, JDA jda) {
        //for each subscribed channel we get the message or null to create it
        subscribedChannelsCache.forEach(channelEntity -> {
            MessageEntity existingMessage = channelEntity
                    .getMessages()
                    .stream()
                    .filter(messageEntity -> messageEntity.getEntityId().equals(gsEntity.getId())).findFirst().orElse(null);

            //create Message
            if(Objects.isNull(existingMessage)){
                sendMessage(channelEntity, gsEntity);
            }
            //update Message or if failed recreate it
            else{
                updateMessage(gsEntity, jda.getTextChannelById(channelEntity.getId()).retrieveMessageById(existingMessage.getMessageId()).complete());
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
}
