package schultz.thomas.discord.bot.business.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.springframework.stereotype.Service;
import schultz.thomas.discord.bot.business.service.tools.GameServerParser;
import schultz.thomas.discord.bot.business.service.tools.MessageWriter;
import schultz.thomas.discord.bot.model.entity.MessageLocation;
import schultz.thomas.discord.bot.model.entity.ServerEntity;
import schultz.thomas.discord.bot.model.repository.ServerRepository;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ServerServiceImpl implements ServerService {


    private List<ServerEntity> serveurStateCache;

    private final ServerRepository serverRepository;

    private final DockerService dockerService;

    private final MessageWriter messageWriter;

    private final List<String> authorizedServersNames = List.of("technique");

    @PostConstruct
    public void init() {
        serveurStateCache = serverRepository.findAll();
    }

    @Override
    public void refreshServers(GenericEvent genericEvent) {
        List<ServerEntity> toUpdate = new ArrayList<>();

        // trouver les serveurs qui ont changé de status

        serveurStateCache.forEach( server -> {
            if(dockerService.serverStatusChanged(server)) {
                toUpdate.add(server);
            }
        });

        // mettre à jour les messages

        toUpdate.forEach(server -> {
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
                        // si le serveur n'a pas de message dans le canal
                        if(server.getAllServersMessages().stream().noneMatch(messageLocation -> messageLocation.getChannelId().equals(textChannel.getId()))) {
                            MessageLocation messageLocation = new MessageLocation();
                            messageLocation.setChannelId(textChannel.getId());
                            messageLocation.setMessageId(messageWriter.sendMessage(textChannel, server));
                            server.addMessageLocation(messageLocation);
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
        serveurStateCache.add(server);
        syncExistingChannels(event);
    }

}