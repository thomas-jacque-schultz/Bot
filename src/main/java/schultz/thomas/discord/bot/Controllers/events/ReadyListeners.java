package schultz.thomas.discord.bot.Controllers.events;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.events.GatewayPingEvent;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import org.springframework.stereotype.Component;
import schultz.thomas.discord.bot.business.service.ServerService;

@Slf4j
@RequiredArgsConstructor
@Component
public class ReadyListeners implements EventListener {


    private  final ServerService serverService;

    @Override
    public void onEvent(GenericEvent genericEvent) {

        if (genericEvent instanceof ReadyEvent){
            log.info("The Bot has started");
            serverService.refreshServers(genericEvent);
        }

        if(genericEvent instanceof GatewayPingEvent){
            serverService.refreshServers(genericEvent);
        }
    }


}
