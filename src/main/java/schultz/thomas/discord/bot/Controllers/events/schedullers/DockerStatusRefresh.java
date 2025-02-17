package schultz.thomas.discord.bot.Controllers.events.schedullers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import schultz.thomas.discord.bot.business.services.DockerService;
import schultz.thomas.discord.bot.business.services.GamingServerService;
import schultz.thomas.discord.bot.controllers.events.models.GamingServerEvent;

@Slf4j
@RequiredArgsConstructor
@Component
/**
 * for later use in V3
 */
public class DockerStatusRefresh {

    private final DockerService dockerService;

    private final GamingServerService gamingServerService;

    private final ApplicationEventPublisher applicationEventPublisher;

    @Scheduled(fixedRate = 60000) // every minute
    public void refreshDockerStatus() {
        log.info("Refreshing docker status");
        gamingServerService
                .getAllGameServerEntities()
                .parallelStream()
                .filter(dockerService::fetchAndNotifyGamingServerContainerStatus)
                .forEach( gamingServerEntity -> {applicationEventPublisher.publishEvent(new GamingServerEvent(this, gamingServerEntity, GamingServerEvent.GamingServerEventType.SERVER_STATUS_CHANGED));});
    }
}

