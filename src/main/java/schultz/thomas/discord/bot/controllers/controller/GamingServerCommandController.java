package schultz.thomas.discord.bot.controllers.controller;

import io.netty.handler.codec.http.HttpResponseStatus;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.JDA;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import schultz.thomas.discord.bot.business.command.Command;
import schultz.thomas.discord.bot.business.command.CommandContext;
import schultz.thomas.discord.bot.business.command.CommandSelector;
import schultz.thomas.discord.bot.model.enums.CommandEnum;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/gaming-server/command")
@RequiredArgsConstructor
public class GamingServerCommandController {

    private final CommandSelector commandSelector;
    private final JDA jda;

    @PostMapping("/{commandName}")
    public String executeCommand(@PathVariable String commandName, @RequestBody String serveurIdentifiant) {
        Map<String, String> options = new HashMap<>();
        options.put("gaming-serveur-identifiant", serveurIdentifiant);

        CommandContext context = new CommandContext(
                jda,
                CommandEnum.REFRESH_GAMING_SERVER_MESSAGE.getCommandName(),
                null
        );

        Command command = commandSelector.getCommand(commandName);
        command.execute(context);

        return HttpResponseStatus.OK.toString();
    }
}
