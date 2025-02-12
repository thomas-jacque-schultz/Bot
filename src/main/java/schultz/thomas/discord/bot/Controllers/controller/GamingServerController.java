package schultz.thomas.discord.bot.Controllers.controller;

import io.netty.handler.codec.http.HttpResponseStatus;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.internal.interactions.command.SlashCommandInteractionImpl;
import org.springframework.web.bind.annotation.*;
import schultz.thomas.discord.bot.business.service.command.Command;
import schultz.thomas.discord.bot.business.service.command.CommandContext;
import schultz.thomas.discord.bot.business.service.command.CommandExecutorService;
import schultz.thomas.discord.bot.business.service.command.CommandSelector;
import schultz.thomas.discord.bot.model.enums.CommandEnum;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;


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
