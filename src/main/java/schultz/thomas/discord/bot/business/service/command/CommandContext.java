package schultz.thomas.discord.bot.business.service.command;

import lombok.Data;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import schultz.thomas.discord.bot.model.entity.UserEntity;

import java.util.List;

@Data
public class CommandContext {

    private MessageReceivedEvent jda;
    private String commandName;
    private List<String> arguments;
    private List<String> payload;

    public CommandContext(MessageReceivedEvent jda, String commandName, List<String> arguments, List<String> payload) {
        this.jda = jda;
        this.commandName = commandName;
        this.arguments = arguments;
        this.payload = payload;
    }

}
