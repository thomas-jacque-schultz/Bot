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

    private Map<String, String> containersStateCache = new HashMap<>();

    @Qualifier("portainerRequestService")
    private final ContainerRequestService containerRequestService;


    /**
     * Fetch container status and return if the container changed status
     *
     * @param gamingServerEntity
     * @return
     */
    public boolean fetchAndNotifyGamingServerContainerStatus(GamingServerEntity gamingServerEntity) {
        boolean before = gamingServerEntity.isRunning();
        gamingServerEntity.setRunning(fetchAndNotifyGamingServerContainerStatus(gamingServerEntity.getIdentifier()));
        return before != gamingServerEntity.isRunning();
    }

    /**
     * Fetch container status and return if the container is running
     *
     * @param containerName
     * @return
     */
    public boolean fetchAndNotifyGamingServerContainerStatus(String containerName) {
        LinkedHashMap<String, Object> currentState = containerRequestService.getContainerState(containerName);
        log.info("Container state : " + currentState.get("State"));

        if (currentState.get("State") == null || !(currentState.get("State") instanceof LinkedHashMap)) {
            throw new IllegalArgumentException("Container not found");
        }
        // access nested linkedHashMap
        LinkedHashMap<String, Object> state = (LinkedHashMap<String, Object>) currentState.get("State");
        return state.get("isRunning").equals("true");
    }

    public boolean startServer(GamingServerEntity gamingServerEntity) {
        return containerRequestService.startContainer(gamingServerEntity.getIdentifier());
    }

    public boolean stopServer(GamingServerEntity gamingServerEntity) {
        return containerRequestService.stopContainer(gamingServerEntity.getIdentifier());
    }
}
