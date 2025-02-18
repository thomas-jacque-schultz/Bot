package schultz.thomas.discord.bot.controllers.events.listeners;

import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.GatewayPingEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import org.springframework.stereotype.Component;
import schultz.thomas.discord.bot.business.services.DockerService;
import schultz.thomas.discord.bot.business.services.GamingServerService;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class ReadyListeners extends ListenerAdapter {

    private final List<CommandData> getCommandMap;

    @Override
    public void onReady(ReadyEvent event) {

        log.info("The Bot has started");

        getCommandMap.forEach(c -> {
            event.getJDA().getGuilds().forEach(g -> {
                g.upsertCommand(c).queue();
            });
        });

        log.info("The Bot has finished creating discord commands");

    }
}
