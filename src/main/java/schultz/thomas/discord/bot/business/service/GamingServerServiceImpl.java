package schultz.thomas.discord.bot.business.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import org.springframework.stereotype.Service;
import schultz.thomas.discord.bot.business.service.mapper.ServeurEntityMapper;
import schultz.thomas.discord.bot.model.entity.ChannelEntity;
import schultz.thomas.discord.bot.model.entity.GamingServerEntity;
import schultz.thomas.discord.bot.model.entity.MessageEntity;
import schultz.thomas.discord.bot.model.repository.ChannelRepository;
import schultz.thomas.discord.bot.model.repository.GamingServerRepository;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class GamingServerServiceImpl implements GamingServerService {


    private List<GamingServerEntity> serveurStateCache;
    private List<ChannelEntity> subscribedChannelsCache;

    private final GamingServerRepository gamingServerRepository;
    private final ChannelRepository channelRepository;

    private final DockerService dockerService;

    private final MessageWriter messageWriter;

    private final ServeurEntityMapper serveurEntityMapper;

    @PostConstruct
    public void init() {
        serveurStateCache = gamingServerRepository.findAll();
        subscribedChannelsCache = channelRepository.findAll();
    }

    @Override
    public boolean createGamingServer(GamingServerEntity gsEntity) {
        if(serveurStateCache.stream().anyMatch(server -> server.getIdentifier().equals(gsEntity.getIdentifier()))) {
            throw new IllegalArgumentException("Server already exists");
        }
        return serveurStateCache.add(gamingServerRepository.save(gsEntity));
    }

    @Override
    public boolean updateGamingServer(GamingServerEntity gsEntity) {
        GamingServerEntity candidate = serveurStateCache.stream()
                .filter(server -> server.getIdentifier().equals(gsEntity.getIdentifier()))
                .findFirst()
                .orElse(null);
        if(candidate == null) {
            throw new IllegalArgumentException("Server not found");
        }
        serveurEntityMapper.updateServeurEntityFromSource(gsEntity, candidate);
        gamingServerRepository.save(candidate);
        return true;
    }

    @Override
    public boolean subscribeDiscordChannel(ChannelEntity channelEntity) {
        if(subscribedChannelsCache.stream().anyMatch(channel -> channel.getChannelId().equals(channelEntity.getChannelId()))) {
            throw new IllegalArgumentException("Channel already exists");
        }
        return subscribedChannelsCache.add(channelRepository.save(channelEntity));
    }

    @Override
    public void createMessageStateFromGamingServer(GamingServerEntity gsEntity,JDA jda) {
        subscribedChannelsCache.forEach( channelEntity -> {
            gsEntity.addMessageLocation(sendMessageFor(gsEntity, channelEntity, jda));
        });
        gamingServerRepository.save(gsEntity);
    }

    @Override
    public void createMessageStateFromDiscordChannel(ChannelEntity channelEntity, JDA jda) {
        serveurStateCache.forEach( gsEntity -> {
            gsEntity.addMessageLocation(sendMessageFor(gsEntity, channelEntity, jda));
        });
        gamingServerRepository.saveAll(serveurStateCache);
    }

   @Override
   public void updateMessageStateFromGamingServer(GamingServerEntity gsEntity, JDA jda) {
        gsEntity.getAllServersMessageEntities().forEach(messageEntity -> {
                try {
                    channel.
                    Message jdaMessage = channel.getHistory().getMessageById(messageEntity.getMessageId());
                    messageWriter.updateMessage(gsEntity, jdaMessage);
                } catch (NullPointerException e) {
                    log.error("Message was removed from discord, creating a new one");
                    messageEntity.setMessageId(messageWriter.sendMessage(Objects.requireNonNull(channel), gsEntity));
                }
        });
        gamingServerRepository.save(gsEntity);
   }

    @Override
    public List<GamingServerEntity> getAllGameServerEntities() {
        return this.serveurStateCache;
    }

    @Override
    public GamingServerEntity getGameServerEntityByIdentifier(String identifier) {
        return this.serveurStateCache.stream().filter(gs -> gs.getIdentifier().equals(identifier)).findFirst().orElse(null);
    }

    @Override
    public boolean updateAll(JDA jda) {
        serveurStateCache.forEach(gsEntity -> {
            gsEntity.getAllServersMessageEntities().forEach(messageEntity -> {
                net.dv8tion.jda.api.entities.Message jdaMessage = jda.getTextChannelById(messageEntity.getChannelId()).getHistory().getMessageById(messageEntity.getMessageId());
                messageWriter.updateMessage(gsEntity, jdaMessage);
            });
        });
        return true;
    }

    private MessageEntity sendMessageFor(GamingServerEntity gsEntity, ChannelEntity channelEntity , JDA jda){
        MessageEntity messageEntity = new MessageEntity();
        messageEntity.setGuildId(channelEntity.getGuildId());
        messageEntity.setChannelId(channelEntity.getChannelId());
        messageEntity.setMessageId(messageWriter.sendMessage(Objects.requireNonNull(jda.getTextChannelById(channelEntity.getChannelId())), gsEntity));
        return messageEntity;
    }



}