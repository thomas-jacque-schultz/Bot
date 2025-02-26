package schultz.thomas.discord.bot.business.command.action;


import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.internal.interactions.CommandDataImpl;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import schultz.thomas.discord.bot.business.command.Command;
import schultz.thomas.discord.bot.business.command.CommandContext;
import schultz.thomas.discord.bot.business.exceptions.CommandFailedException;
import schultz.thomas.discord.bot.business.services.DockerService;
import schultz.thomas.discord.bot.business.services.GamingServerService;
import schultz.thomas.discord.bot.model.entity.GamingServerEntity;
import schultz.thomas.discord.bot.model.enums.CommandEnum;
import schultz.thomas.discord.bot.model.enums.UserPrivilegeEnum;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@Component
@RequiredArgsConstructor
public class StopServerGamingCommand implements Command {

    private final GamingServerService gamingServerService;

    private final DockerService dockerService;

    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    private final ApplicationEventPublisher applicationEventPublisher;

    public List<UserPrivilegeEnum> roleNeeded(){ return new ArrayList<>( List.of(UserPrivilegeEnum.ADMINISTRATOR, UserPrivilegeEnum.OWNER)); }

    @Override
    public CommandData getCommandData() {
        return  new CommandDataImpl("pause", "->PAUSE<- le serveur de jeu")
                .addOptions(new OptionData(OptionType.STRING, "identifier", "identifiant du serveur", true));
    }

    @Override
    public CommandEnum getEnum(){
        return CommandEnum.STOP_SGAMING;
    }

    @Override
    public String execute(CommandContext context) {
        GamingServerEntity gamingServerEntity = gamingServerService.getGameServerEntityByIdentifier(context.getOptions().get("identifier"));
        if (gamingServerEntity == null) {
            throw new CommandFailedException("Le serveur de jeu n'existe pas");
        }
        boolean startSuccess = dockerService.stopServer(gamingServerEntity);
        if(!startSuccess){
            throw new CommandFailedException("Impossible de pauser le serveur de jeu");
        }

        scheduler.schedule(() -> applicationEventPublisher.publishEvent(new schultz.thomas.discord.bot.controllers.events.models.GamingServerEvent(this, gamingServerEntity, schultz.thomas.discord.bot.controllers.events.models.GamingServerEvent.GamingServerEventType.SERVER_STATUS_CHANGED)), 10, java.util.concurrent.TimeUnit.SECONDS);

        return "Serveur de jeu est en pause";
    }
}
