package schultz.thomas.discord.bot.business.service.command.action;

import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.internal.interactions.CommandDataImpl;
import org.springframework.stereotype.Component;
import schultz.thomas.discord.bot.business.service.exceptions.CommandFailedException;
import schultz.thomas.discord.bot.business.service.GamingServerService;
import schultz.thomas.discord.bot.business.service.command.Command;
import schultz.thomas.discord.bot.business.service.command.CommandContext;
import schultz.thomas.discord.bot.model.enums.CommandEnum;
import schultz.thomas.discord.bot.model.enums.UserPrivilegeEnum;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Component
public class RefreshGamingServerMessageCommand implements Command {

    private final GamingServerService gamingServerService;

    public List<UserPrivilegeEnum> roleNeeded(){
        return new ArrayList<>( List.of(UserPrivilegeEnum.ADMINISTRATOR, UserPrivilegeEnum.OWNER));
    }

    @Override
    public CommandData getCommandData() {
        return  new CommandDataImpl("refresh-status", "mets à jour les autorisations d'un utilisateur")
                .addOptions(new OptionData(OptionType.STRING, "gaming-serveur-identifiant", "id discord", true));
    }

    @Override
    public CommandEnum getEnum(){
        return CommandEnum.REFRESH_GAMING_SERVER_MESSAGE;
    }

    @Override
    public String execute(CommandContext context) {
        try {
            var gs = gamingServerService.getGameServerEntityByIdentifier(context.getOptions().get("gaming-serveur-identifiant"));
            gamingServerService.refreshGamingServerMessage(gs, context.getJda());
        } catch (IllegalArgumentException e) {
            throw new CommandFailedException("Impossible de refresh le message : " + context.getOptions().get("gaming-serveur-identifiant"));
        }
        return "Message mis à jour";
    }

}
