package schultz.thomas.discord.bot.business.service.command;

import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.springframework.stereotype.Service;
import schultz.thomas.discord.bot.business.service.UserService;

@Service
@RequiredArgsConstructor
public class CommandExecutorService {

    private final CommandSelector commandSelector;

    private final UserService userService;

    public void handleCommand(SlashCommandInteractionEvent discordContext) {

        CommandContext context = new CommandContext(discordContext.getJDA(), discordContext.getName(), discordContext.getInteraction());

        Command command = commandSelector.getCommand(discordContext.getName());

        if (command == null) {
            throw new IllegalArgumentException("RTFM : " + discordContext.getName());
        }

        if (!command.hasRight(userService.getRole(discordContext.getUser().getId()))) {
            throw new IllegalArgumentException("The emperor of Holy Terra didn't allow you to : " + discordContext.getName());
        }

        command.execute(context);
    }

}
