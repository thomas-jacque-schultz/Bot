package schultz.thomas.discord.bot.Controllers.events;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.stereotype.Component;
import schultz.thomas.discord.bot.business.service.CommandExecutorService;

@Slf4j
@RequiredArgsConstructor
@Component
public class CommandListeners extends ListenerAdapter  {


    private final CommandExecutorService commandExecutorService;

    @Override
    public void onMessageReceived(MessageReceivedEvent event) throws RuntimeException {
        if (filterMessage(event)) return;
        commandExecutorService.handleCommand(event);
    }

    public boolean filterMessage(MessageReceivedEvent event){
        return event.getAuthor().isBot() || !event.getMessage().getContentRaw().startsWith("/");
    }
}
