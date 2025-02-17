package schultz.thomas.discord.bot.controllers.events.schedullers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import schultz.thomas.discord.bot.business.services.DockerService;
import schultz.thomas.discord.bot.business.services.GamingServerService;

@Slf4j
@RequiredArgsConstructor
@Component
/**
 * for later use in V3
 */
public class DockerStatusRefresh {

    private final DockerService dockerService;

    private final GamingServerService gamingServerService;

    @Scheduled(fixedRate = 60000) // every minute
    public void refreshDockerStatus() {
        log.info("Refreshing docker status");
        gamingServerService
                .getAllGameServerEntities()
                .parallelStream()
                .forEach(dockerService::fetchAndNotifyGamingServerContainerStatus);
    }
}

