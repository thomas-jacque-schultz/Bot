package schultz.thomas.discord.bot.business.services;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import schultz.thomas.discord.bot.model.entity.GamingServerEntity;
import schultz.thomas.discord.bot.model.entity.GamingServerStatusHistoryEntry;
import schultz.thomas.discord.bot.model.enums.ServerStatusEnum;
import schultz.thomas.discord.bot.model.transitory.DockerContainerState;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
/**
 * Docker service implementation
 * Call portainer API to manage docker containers
 */
public class DockerService {

    @Qualifier("portainerRequestService")
    private final ContainerRequestService containerRequestService;

    /**
     * Fetch container status from Portainer and return true if the status changed.
     * Sets lastStatusCheckAt on every call.
     * Maps: Portainer unreachable → UNREACHABLE, running=true → ONLINE, running=false → OFFLINE.
     * null status (app just started) is treated as a change so the first poll fires an event.
     * On change, appends a new history entry with the status start timestamp.
     */
    public boolean fetchAndNotifyGamingServerContainerStatus(GamingServerEntity gamingServerEntity) {
        ServerStatusEnum newStatus;
        Instant now = Instant.now();
        try {
            DockerContainerState state = containerRequestService.getContainerState(gamingServerEntity.getPortainerStackId());
            newStatus = resolveStatus(state);
        } catch (Exception e) {
            log.warn(
                    "Portainer check failed for server='{}' with exception='{}': {}",
                    gamingServerEntity.getIdentifier(),
                    e.getClass().getSimpleName(),
                    e.getMessage()
            );
            newStatus = ServerStatusEnum.UNREACHABLE;
        }

        gamingServerEntity.setLastStatusCheckAt(now);

        ServerStatusEnum currentStatus = gamingServerEntity.getStatus();
        if (newStatus.equals(currentStatus)) {
            return false;
        }

        gamingServerEntity.setStatus(newStatus);
        gamingServerEntity.setLastStatusChangeAt(now);
        appendStatusHistory(gamingServerEntity, newStatus, now);
        return true;
    }

    private void appendStatusHistory(GamingServerEntity gamingServerEntity, ServerStatusEnum status, Instant startedAt) {
        List<GamingServerStatusHistoryEntry> history = gamingServerEntity.getStatusHistory();
        if (history == null) {
            history = new ArrayList<>();
            gamingServerEntity.setStatusHistory(history);
        }
        history.add(new GamingServerStatusHistoryEntry(status, startedAt));
    }

    private ServerStatusEnum resolveStatus(DockerContainerState state) {
        if (state == null) return ServerStatusEnum.UNREACHABLE;
        return state.isRunning() ? ServerStatusEnum.ONLINE : ServerStatusEnum.OFFLINE;
    }

    public boolean startServer(GamingServerEntity gamingServerEntity) {
        return containerRequestService.startContainer(gamingServerEntity.getPortainerStackId());
    }

    public boolean stopServer(GamingServerEntity gamingServerEntity) {
        return containerRequestService.stopContainer(gamingServerEntity.getPortainerStackId());
    }
}
