package schultz.thomas.discord.bot.business.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.springframework.stereotype.Service;
import schultz.thomas.discord.bot.business.service.mapper.GameServerParser;
import schultz.thomas.discord.bot.business.service.mapper.MessageWriter;
import schultz.thomas.discord.bot.business.service.mapper.ServeurEntityMapper;
import schultz.thomas.discord.bot.model.entity.MessageLocation;
import schultz.thomas.discord.bot.model.entity.ServerEntity;
import schultz.thomas.discord.bot.model.repository.ServerRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class ServerServiceImpl implements ServerService {


    private List<ServerEntity> serveurStateCache;

    private final ServerRepository serverRepository;

    private final DockerService dockerService;

    private final MessageWriter messageWriter;

    private final ServeurEntityMapper serveurEntityMapper;

    private final List<String> authorizedServersNames = List.of("technique","bot");

    @PostConstruct
    public void init() {
        serveurStateCache = serverRepository.findAll();
    }

    @Override
    public void refreshServers(GenericEvent genericEvent) {
        List<ServerEntity> toUpdate = new ArrayList<>();

        serveurStateCache.forEach( server -> {                      // trouver les serveurs qui ont changé de status
            if(dockerService.serverStatusChanged(server)) {
                toUpdate.add(server);
            }
        });

        toUpdate.forEach(server -> {                            // mettre à jour les messages
            server.getAllServersMessages().forEach(messageLocation -> {
                String channelId = messageLocation.getChannelId();
                String messageId = messageLocation.getMessageId();
                Message message = genericEvent.getJDA().getTextChannelById(channelId).getHistory().getMessageById(messageId);
                messageWriter.updateMessage(server, message);
            });
        });
    }

    @Override
    public void syncExistingChannels(GenericEvent genericEvent) {
        genericEvent.getJDA().getGuilds().forEach(guild -> {
            guild.getChannels().forEach(channel -> {
                if(channel instanceof TextChannel textChannel && authorizedServersNames.contains(textChannel.getName())) {
                    serveurStateCache.forEach(server -> {
                        MessageLocation messageLocation = server.getAllServersMessages().stream().filter(location -> location.getChannelId().equals(textChannel.getId())).findFirst().orElse(null);
                        // si le serveur n'a pas de message dans le canal
                        if(messageLocation == null) {
                            messageLocation = new MessageLocation();
                            messageLocation.setGuildId(guild.getId());
                            messageLocation.setChannelId(textChannel.getId());
                            messageLocation.setMessageId(messageWriter.sendMessage(textChannel, server));
                            server.addMessageLocation(messageLocation);
                        }
                        else {
                            messageWriter.updateMessage(server, Objects.requireNonNull(textChannel.retrieveMessageById(messageLocation.getMessageId()).complete() ));
                        }
                    });
                }
            });
        });
        serverRepository.saveAll(serveurStateCache);
    }

    @Override
    public void answerHello(MessageReceivedEvent event) {
        event.getAuthor();
    }

    @Override
    public void addNewGamingServer(MessageReceivedEvent event) {
        ServerEntity server =  GameServerParser.parseServer(event.getMessage().getContentRaw());
        ServerEntity candidate = serveurStateCache.stream().filter(serverEntity -> serverEntity.getIdentifier().equals(server.getIdentifier())).findFirst().orElse(null);
        //si le serveur est déjà dans la liste
        if(candidate != null) {
            serveurEntityMapper.updateServeurEntityFromSource(server, candidate);
        }
        else {
            serveurStateCache.add(server);
        }
        syncExistingChannels(event);
    }

}