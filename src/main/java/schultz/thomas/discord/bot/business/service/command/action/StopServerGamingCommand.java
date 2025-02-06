package schultz.thomas.discord.bot.business.service.command.action;


import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.internal.interactions.CommandDataImpl;
import org.springframework.stereotype.Component;
import schultz.thomas.discord.bot.Controllers.exceptions.CommandFailedException;
import schultz.thomas.discord.bot.business.service.DockerService;
import schultz.thomas.discord.bot.business.service.GamingServerService;
import schultz.thomas.discord.bot.business.service.command.Command;
import schultz.thomas.discord.bot.business.service.command.CommandContext;
import schultz.thomas.discord.bot.model.entity.GamingServerEntity;
import schultz.thomas.discord.bot.model.enums.CommandEnum;
import schultz.thomas.discord.bot.model.enums.UserPrivilegeEnum;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class StopServerGamingCommand implements Command {

    private final GamingServerService gamingServerService;

    private final DockerService dockerService;


    public List<UserPrivilegeEnum> roleNeeded(){ return new ArrayList<>( List.of(UserPrivilegeEnum.ADMINISTRATOR, UserPrivilegeEnum.OWNER)); }

    @Override
    public CommandData getCommandData() {
        return  new CommandDataImpl("stop", "->PAUSE<- le serveur de jeu")
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
            throw new CommandFailedException("Impossible de lancer le serveur de jeu");
        }
        return "Serveur de jeu est en pause";
    }
}
