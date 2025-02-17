package schultz.thomas.discord.bot.business.services;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import schultz.thomas.discord.bot.model.transitoy.DockerContainerState;
import schultz.thomas.discord.bot.controllers.events.models.GamingServerEvent;
import schultz.thomas.discord.bot.model.entity.GamingServerEntity;

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
     * Fetch container status and return if the container status has changed
     *
     * @param
     * @return
     */
    public boolean fetchAndNotifyGamingServerContainerStatus(GamingServerEntity gamingServerEntity) {
        DockerContainerState state = containerRequestService.getContainerState(gamingServerEntity.getIdentifier());
        // if the state is null, the container is unreachable
        if (Objects.isNull(state)){
            throw new RuntimeException("Container state unreachable");
        }
        // if the status is null the state is updated
        if(Objects.isNull(gamingServerEntity.getStatus())){
            gamingServerEntity.setStatus(state);
            return true;
        }
        // if the status is different from the state, the state is updated
        if (state.equals(gamingServerEntity.getStatus())){
            gamingServerEntity.setStatus(state);
            return true;
        }
        //we didn't change the status
        return false;
    }

    public boolean startServer(GamingServerEntity gamingServerEntity) {
        return containerRequestService.startContainer(gamingServerEntity.getIdentifier());
    }

    public boolean stopServer(GamingServerEntity gamingServerEntity) {
        return containerRequestService.stopContainer(gamingServerEntity.getIdentifier());
    }
}
