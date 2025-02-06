package schultz.thomas.discord.bot.Controllers.events;

import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.events.GatewayPingEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import org.springframework.stereotype.Component;
import schultz.thomas.discord.bot.business.service.DockerService;
import schultz.thomas.discord.bot.business.service.GamingServerService;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class ReadyListeners extends ListenerAdapter {


    private  final GamingServerService gamingServerService;

    private final DockerService dockerService;
    private final List<CommandData> getCommandMap;


    @Override
    public void onReady(ReadyEvent event) {

            log.info("The Bot has started");

            //gamingServerService.updateAll(event.getJDA());

            log.info("The Bot has finished updating the servers");

            getCommandMap.forEach(c -> {
                event.getJDA().getGuilds().forEach(g -> {
                    g.upsertCommand(c).queue();
                });
            });

            log.info("The Bot has finished creating discord commands");

    }

    @Override
    public void onGatewayPing(@Nonnull GatewayPingEvent event) {

        event.getJDA().getGuilds().forEach(g -> {
            log.info("The Bot has pinged the gateway" + g.getName());
        });

        gamingServerService.getAllGameServerEntities().forEach(g -> {
            if(dockerService.serverStatusChanged(g)){
                gamingServerService.updateMessageStateFromGamingServer(g, event.getJDA());
            }
            log.info("The Bot has updated the server status");
        });

        log.info("The Bot has pinged the gateway");
    }

}
