package schultz.thomas.discord.bot.business.command;

import lombok.Data;
import net.dv8tion.jda.api.JDA;

import java.util.Map;

@Data
public class CommandContext {

    private JDA jda;
    private String commandName;
    private Map<String, String> options;

    public CommandContext(JDA jda, String commandName, Map<String, String> options) {
        this.jda = jda;
        this.commandName = commandName;
        this.options = options;
    }

}
