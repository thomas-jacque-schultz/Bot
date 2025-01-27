package schultz.thomas.discord.bot.business.service;

import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.springframework.stereotype.Service;
import schultz.thomas.discord.bot.business.service.command.Command;
import schultz.thomas.discord.bot.business.service.command.CommandContext;
import schultz.thomas.discord.bot.business.service.command.CommandSelector;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommandExecutorService {

    private final CommandSelector commandSelector;

    private final UserService userService;

    public void handleCommand(MessageReceivedEvent discordContext) {

        List<String> lines = discordContext.getMessage().getContentRaw().lines().toList();
        List<String> firstLine = List.of(lines.removeFirst().split(" "));

        CommandContext context = new CommandContext(discordContext, firstLine.removeFirst(), firstLine, lines);

        String commandName = context.getCommandName();
        Command command = commandSelector.getCommand((commandName));

        if (command == null) {
            throw new IllegalArgumentException("you thought outside of the box, but there is no : " + commandName);
        }

        if (!command.hasRight(userService.getRole(discordContext.getAuthor().getId()))) {
            throw new IllegalArgumentException("The emperor of Holy Terra didn't allow you to : " + commandName);
        }

        if (!command.isValid(context)) {
            throw new IllegalArgumentException("RTFM : " + commandName);
        }

        command.execute(context);
    }

}
