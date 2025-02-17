package schultz.thomas.discord.bot.business.services;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.JDA;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import schultz.thomas.discord.bot.business.mapper.ServeurEntityMapper;
import schultz.thomas.discord.bot.controllers.events.models.GamingServerEvent;
import schultz.thomas.discord.bot.model.entity.ChannelEntity;
import schultz.thomas.discord.bot.model.entity.GamingServerEntity;
import schultz.thomas.discord.bot.model.entity.MessageEntity;
import schultz.thomas.discord.bot.model.repository.GamingServerRepository;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class GamingServerService {


    private List<GamingServerEntity> serveurStateCache;
    private final GamingServerRepository gamingServerRepository;
    private final DockerService dockerService;
    private final ServeurEntityMapper serveurEntityMapper;
    private final ApplicationEventPublisher applicationEventPublisher;

    @PostConstruct
    public void init() {
        serveurStateCache = gamingServerRepository.findAll();
    }

    public void createGamingServer(GamingServerEntity gsEntity) {
        if (serveurStateCache.stream().anyMatch(server -> server.getIdentifier().equals(gsEntity.getIdentifier()))) {
            throw new IllegalArgumentException("Server already exists");
        }
        GamingServerEntity gamingServerEntity = gamingServerRepository.save(gsEntity);
        applicationEventPublisher.publishEvent(new GamingServerEvent(this, gamingServerEntity, GamingServerEvent.GamingServerEventType.SERVER_CREATED));
        serveurStateCache.add(gamingServerEntity);
    }

    public void updateGamingServer(GamingServerEntity gsEntity) {
        GamingServerEntity candidate = serveurStateCache.stream()
                .filter(server -> server.getIdentifier().equals(gsEntity.getIdentifier()) || server.getId().equals(gsEntity.getId()))
                .findFirst()
                .orElse(null);
        if (candidate == null) {
            throw new IllegalArgumentException("Server not found");
        }
        serveurEntityMapper.updateServeurEntityFromSource(gsEntity, candidate);
        gamingServerRepository.save(candidate);
        applicationEventPublisher.publishEvent(new GamingServerEvent(this, candidate, GamingServerEvent.GamingServerEventType.SERVER_STATUS_CHANGED));
    }

    public List<GamingServerEntity> getAllGameServerEntities() {
        return this.serveurStateCache;
    }

    public GamingServerEntity getGameServerEntityByIdentifier(String identifier) {
        return this.serveurStateCache.stream().filter(gs -> gs.getIdentifier().equals(identifier)).findFirst().orElse(null);
    }

}