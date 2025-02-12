package schultz.thomas.discord.bot.Controllers.schedullers;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import schultz.thomas.discord.bot.business.service.DockerService;
import schultz.thomas.discord.bot.business.service.GamingServerService;

@RequiredArgsConstructor
@Component
public class DockerStatusRefresh {

    private final DockerService dockerService;

    private final GamingServerService gamingServerService;

    @Scheduled(fixedRate = 60000) // every minute
    public void refreshDockerStatus() {
        gamingServerService.getAllGameServerEntities().parallelStream().forEach(dockerService::fetchAndNotifyGamingServerContainerStatus);
    }
}

