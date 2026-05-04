package schultz.thomas.discord.bot.business.command;

import org.springframework.stereotype.Component;
import schultz.thomas.discord.bot.model.enums.CommandEnum;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class CommandSelector {


    private final Map<String, Command> commands;

    public CommandSelector(List<Command> commands){
        this.commands = commands.stream()
                .collect(Collectors.toMap(command -> command.getEnum().getCommandName(), Function.identity()));
    }

    public Command getCommand(String commandName /* message ?*/) {
        if(commands.containsKey(commandName)) {
            return commands.get(commandName);
        }
        // Renvoyer command inconnue au bataillon
        return null;
    }




}
