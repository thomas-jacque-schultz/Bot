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
            throw new IllegalArgumentException("Vous êtes un génie, vraiment personne n'avait pensé à cette commande avant vous : " + commandName);
        }

        if (!command.hasRight(userService.getRole(discordContext.getAuthor().getId()))) {
            throw new IllegalArgumentException("Un manant à tenté un commande impie : " + commandName);
        }

        if (!command.isValid(context)) {
            throw new IllegalArgumentException("Donnez à cette homme un dictionnaire il a tenté d'écrire : " + commandName);
        }

        command.execute(context);
    }

}
