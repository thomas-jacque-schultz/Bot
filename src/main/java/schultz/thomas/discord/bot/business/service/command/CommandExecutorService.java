package schultz.thomas.discord.bot.business.service.command;

import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import org.springframework.stereotype.Service;
import schultz.thomas.discord.bot.business.service.UserService;

import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommandExecutorService {

    private final CommandSelector commandSelector;

    private final UserService userService;

    public void handleCommand(SlashCommandInteractionEvent discordContext) {
        Map<String, String> options =  discordContext.getOptions().stream().collect(Collectors.toMap(OptionMapping::getName, OptionMapping::getAsString));
        options.put("user-id", discordContext.getUser().getId());
        options.put("user-name", discordContext.getUser().getName());
        options.put("channel-id", discordContext.getChannel().getId());
        options.put("channel-name", discordContext.getChannel().getName());
        options.put("guild-id", discordContext.getGuild().getId());
        options.put("guild-name", discordContext.getGuild().getName());

        String commandName = discordContext.getName();
        JDA jda = discordContext.getJDA();

        CommandContext context = new CommandContext(jda, commandName, options);


        Command command = commandSelector.getCommand(discordContext.getName());

        if (command == null) {
            discordContext.reply("RTFM : " + discordContext.getName()).queue();
            throw new IllegalArgumentException("RTFM : " + discordContext.getName());
        }

        if (!command.hasRight(userService.getRole(discordContext.getUser().getId())) ) {
            discordContext.reply("The emperor of Holy Terra didn't allow you to : " + discordContext.getName()).queue();
           throw new IllegalArgumentException("The emperor of Holy Terra didn't allow you to : " + discordContext.getName());
        }


        String commandResult = command.execute(context);

        discordContext.reply(commandResult).queue();
    }

}
