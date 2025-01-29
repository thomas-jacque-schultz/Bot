package schultz.thomas.discord.bot.config;

import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.internal.interactions.CommandDataImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import schultz.thomas.discord.bot.business.service.command.Command;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Configuration
public class CommandConfiguration {

    @Bean
    public List<CommandData> getCommandMap(List<Command> commands) {
        return commands.stream().map(Command::getCommandData).collect(Collectors.toList());
    }
}
