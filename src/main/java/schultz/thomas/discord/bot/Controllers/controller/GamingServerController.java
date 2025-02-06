package schultz.thomas.discord.bot.Controllers.controller;

import io.netty.handler.codec.http.HttpResponseStatus;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.messages.MessagePoll;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.SlashCommandInteraction;
import net.dv8tion.jda.internal.interactions.command.SlashCommandInteractionImpl;
import org.apache.http.protocol.HTTP;
import org.springframework.web.bind.annotation.*;
import schultz.thomas.discord.bot.business.service.command.CommandExecutorService;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


@RestController
@RequestMapping("/gaming-server")
@RequiredArgsConstructor
public class GamingServerController {

    private final CommandExecutorService commandExecutorService;

    private final JDA jda;


    @PostMapping("/command/{command}")
    public String getCommand(@PathVariable String command, @RequestBody String event) {
        SlashCommandInteractionImpl slashCommandInteraction = new SlashCommandInteractionImpl(null, null);
        SlashCommandInteractionEvent slashCommandInteractionEvent = new SlashCommandInteractionEvent(jda, 0, slashCommandInteraction);
        commandExecutorService.handleCommand(slashCommandInteractionEvent);
        return HttpResponseStatus.OK.toString();
    }



}
