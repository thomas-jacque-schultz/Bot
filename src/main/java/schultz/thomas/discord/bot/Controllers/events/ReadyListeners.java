package schultz.thomas.discord.bot.Controllers.events;

import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.GatewayPingEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import org.springframework.stereotype.Component;
import schultz.thomas.discord.bot.business.service.GamingServerService;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class ReadyListeners extends ListenerAdapter {


    private  final GamingServerService gamingServerService;


    private final List<CommandData> commandMap;


    @Override
    public void onReady(ReadyEvent event) {

            log.info("The Bot has started");

            gamingServerService.updateAll(event.getJDA());
            log.info("The Bot has finished updating the servers");

            commandMap.forEach(c -> {
                event.getJDA().getGuilds().forEach(g -> {
                    g.upsertCommand(c).queue();
                });
            });

            log.info("The Bot has finished creating discord commands");

    }

    @Override
    public void onGatewayPing(@Nonnull GatewayPingEvent event) {
        log.info("The Bot has pinged the gateway");
    }


}
