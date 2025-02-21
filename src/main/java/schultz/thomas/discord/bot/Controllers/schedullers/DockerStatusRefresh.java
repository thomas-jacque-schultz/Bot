package schultz.thomas.discord.bot.Controllers.schedullers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import schultz.thomas.discord.bot.business.service.DockerService;
import schultz.thomas.discord.bot.business.service.GamingServerService;

@Slf4j
@RequiredArgsConstructor
@Component
/**
 * for later use in V3
 */
public class DockerStatusRefresh {
//
   // private final DockerService dockerService;
//
   // private final GamingServerService gamingServerService;
//
   // @Scheduled(fixedRate = 60000) // every minute
   // public void refreshDockerStatus() {
   //     log.info("Refreshing docker status");
   //     gamingServerService
   //             .getAllGameServerEntities()
   //             .parallelStream()
   //             .filter(dockerService::fetchAndNotifyGamingServerContainerStatus)
   //             .forEach(gamingServerService::refreshGamingServerMessage);
   // }
}

