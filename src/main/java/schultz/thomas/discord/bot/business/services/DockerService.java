package schultz.thomas.discord.bot.business.services;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import schultz.thomas.discord.bot.business.dtos.DockerContainerStateDto;
import schultz.thomas.discord.bot.controllers.events.models.GamingServerEvent;
import schultz.thomas.discord.bot.model.entity.ChannelEntity;
import schultz.thomas.discord.bot.model.entity.GamingServerEntity;
import schultz.thomas.discord.bot.model.response.DockerContainerInfo;

import java.util.*;


@Slf4j
@Service
@RequiredArgsConstructor
/**
 * Docker service implementation
 * Call portainer API to manage docker containers
 */
public class DockerService {

    private final ApplicationEventPublisher applicationEventPublisher;

    @Qualifier("portainerRequestService")
    private final ContainerRequestService containerRequestService;

    /**
     * Fetch container status and return if the container is running
     *
     * @param
     * @return
     */
    public boolean fetchAndNotifyGamingServerContainerStatus(GamingServerEntity gamingServerEntity) {
        DockerContainerStateDto state = containerRequestService.getContainerState(gamingServerEntity.getIdentifier());
        if (Objects.nonNull(state) && state.isRunnning() != gamingServerEntity.isRunning() ) {
            gamingServerEntity.setRunning(state.isRunnning());
            applicationEventPublisher.publishEvent(new GamingServerEvent(this, gamingServerEntity, GamingServerEvent.GamingServerEventType.SERVER_STATUS_CHANGED));
        }
    }

    public boolean startServer(GamingServerEntity gamingServerEntity) {
        return containerRequestService.startContainer(gamingServerEntity.getIdentifier());
    }

    public boolean stopServer(GamingServerEntity gamingServerEntity) {
        return containerRequestService.stopContainer(gamingServerEntity.getIdentifier());
    }
}
