package schultz.thomas.discord.bot.controllers.controller;

import io.netty.handler.codec.http.HttpResponseStatus;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.JDA;
import org.springframework.web.bind.annotation.*;
import schultz.thomas.discord.bot.business.command.Command;
import schultz.thomas.discord.bot.business.command.CommandContext;
import schultz.thomas.discord.bot.business.command.CommandExecutorService;
import schultz.thomas.discord.bot.business.command.CommandSelector;
import schultz.thomas.discord.bot.model.enums.CommandEnum;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/gaming-server")
@RequiredArgsConstructor
public class GamingServerController {

    private final CommandExecutorService commandExecutorService;

    private final CommandSelector commandSelector;

    private final JDA jda;


    @PostMapping("/command/{commandName}")
    public String getCommand(@PathVariable String commandName, @RequestBody String serveurIdentifiant) {

        Map<String, String> options = new HashMap<>();
        options.put("gaming-serveur-identifiant", serveurIdentifiant);
        CommandContext context = new CommandContext(jda, CommandEnum.REFRESH_GAMING_SERVER_MESSAGE.getCommandName(), null);
        Command command = commandSelector.getCommand(commandName);

        command.execute(context);

        return HttpResponseStatus.OK.toString();
    }



}
