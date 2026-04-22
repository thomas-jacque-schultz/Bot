package schultz.thomas.discord.bot.controllers.events.listeners;

import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.JDA;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import schultz.thomas.discord.bot.business.services.DiscordMessageService;
import schultz.thomas.discord.bot.controllers.events.models.GamingServerEvent;
import schultz.thomas.discord.bot.model.entity.GamingServerEntity;
import schultz.thomas.discord.bot.model.repository.GamingServerRepository;

@Component
@RequiredArgsConstructor
public class GamingServersEventListener {

    private final JDA jda;

    private final DiscordMessageService discordMessageService;

    private final GamingServerRepository gamingServerRepository;

    @Async
    @EventListener
    public void handleGamingServerEvent(GamingServerEvent event) {
        GamingServerEntity gamingServerEntity = event.getGamingServerEntity();
        if (gamingServerEntity == null) {
            return;
        }

        switch (event.getGamingServerEventType()) {
            case GamingServerEvent.GamingServerEventType.SERVER_CREATED:
                gamingServerRepository.save(gamingServerEntity);
                discordMessageService.createOrUpdateMessageForGamingServerEntity(gamingServerEntity, jda);
                break;
            case GamingServerEvent.GamingServerEventType.SERVER_STATUS_CHANGED:
                gamingServerRepository.save(gamingServerEntity);
                discordMessageService.createOrUpdateMessageForGamingServerEntity(gamingServerEntity, jda);
                break;
            case GamingServerEvent.GamingServerEventType.SERVER_DELETED:
                discordMessageService.deleteMessageForGamingServerEntity(gamingServerEntity, jda);
                break;
            default:
                break;
        }
        // handle event
    }
}
