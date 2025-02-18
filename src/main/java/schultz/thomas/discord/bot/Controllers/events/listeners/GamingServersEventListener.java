package schultz.thomas.discord.bot.controllers.events.listeners;

import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.JDA;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import schultz.thomas.discord.bot.business.services.DiscordMessageService;
import schultz.thomas.discord.bot.controllers.events.models.GamingServerEvent;

@Component
@RequiredArgsConstructor
public class GamingServersEventListener {

    private final JDA jda;

    private final DiscordMessageService discordMessageService;

    @Async
    @EventListener
    public void handleGamingServerEvent(GamingServerEvent event) {
        switch (event.getGamingServerEventType()) {
            case GamingServerEvent.GamingServerEventType.SERVER_CREATED:
                discordMessageService.createOrUpdateMessageForGamingServerEntity(event.getGamingServerEntity(), jda);
                break;
            case GamingServerEvent.GamingServerEventType.SERVER_STATUS_CHANGED:
                discordMessageService.createOrUpdateMessageForGamingServerEntity(event.getGamingServerEntity(), jda);
                break;
            case GamingServerEvent.GamingServerEventType.SERVER_DELETED:
                discordMessageService.deleteMessageForGamingServerEntity(event.getGamingServerEntity(), jda);
                break;
            default:
                break;
        }
        // handle event
    }
}
