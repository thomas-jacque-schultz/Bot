package schultz.thomas.discord.bot.business.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import schultz.thomas.discord.bot.model.entity.GamingServerEntity;
import schultz.thomas.discord.bot.model.response.DockerContainerInfo;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;


@Slf4j
@Service
@RequiredArgsConstructor
/**
 * Docker service implementation
 * Call portainer API to manage docker containers
 */
public class DockerService {

    private Map<String,String> containersStateCache = new HashMap<>();

    @Qualifier("portainerRequestService")
    private final ContainerRequestService containerRequestService;

    public void fetchAndNotifyGamingServerContainerStatus(GamingServerEntity gamingServerEntity) {
        fetchAndNotifyGamingServerContainerStatus(gamingServerEntity.getIdentifier());
    }

    public void fetchAndNotifyGamingServerContainerStatus(String containerName) {
        LinkedHashMap<String, Object> currentState = containerRequestService.getContainerState(containerName);
        log.info("Container state : " + currentState.get("State"));
        // send notification in spring event queue
    }

    public boolean startServer(GamingServerEntity gamingServerEntity) {
        return containerRequestService.startContainer(gamingServerEntity.getIdentifier());
    }

    public boolean stopServer(GamingServerEntity gamingServerEntity) {
        return containerRequestService.stopContainer(gamingServerEntity.getIdentifier());
    }
}
