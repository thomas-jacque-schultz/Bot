package schultz.thomas.discord.bot.business.service.command;

import org.springframework.stereotype.Component;
import schultz.thomas.discord.bot.model.enums.CommandEnum;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class CommandSelector {


    private final Map<CommandEnum, Command> commands;

    public CommandSelector(List<Command> commands){
        this.commands = commands.stream()
                .collect(Collectors.toMap(Command::getEnum, Function.identity()));
    }

    public Command getCommand(String commandName /* message ?*/) {
        CommandEnum com = CommandEnum.valueOf(commandName);
        if(commands.containsKey(com)) {
            return commands.get(com);
        }
        // Renvoyer command inconnue au bataillon
        return null;
    }


}
