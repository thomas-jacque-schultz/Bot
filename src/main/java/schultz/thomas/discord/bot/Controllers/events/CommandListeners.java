package schultz.thomas.discord.bot.Controllers.events;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.stereotype.Component;
import schultz.thomas.discord.bot.business.service.ServerService;

@Slf4j
@RequiredArgsConstructor
@Component
public class CommandListeners extends ListenerAdapter  {

    private final ServerService serverService;

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {


        if (event.getAuthor().isBot()) {
            return;
        }

        if(event.getMessage().getContentRaw().startsWith("/addServer")) {
            log.info("Command received: " + event.getMessage().getContentDisplay() + " from " + event.getClass().getName());
            serverService.addNewGamingServer(event);
        }

        if(event.getMessage().getContentRaw().startsWith("/syncChannels")) {
            log.info("Command received: " + event.getMessage().getContentDisplay() + " from " + event.getClass().getName());
            serverService.syncExistingChannels(event);
        }

        log.info("Message received: " + event.getMessage().getContentDisplay() + " from " + event.getClass().getName());
        //serverService.answerHello(jdaProvider.getJda(), event);

        String message = event.getMessage().getContentRaw();
    }
}
