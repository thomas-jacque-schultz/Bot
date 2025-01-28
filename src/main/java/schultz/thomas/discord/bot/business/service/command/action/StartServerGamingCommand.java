package schultz.thomas.discord.bot.business.service.command.action;


import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.internal.interactions.CommandDataImpl;
import org.springframework.stereotype.Component;
import schultz.thomas.discord.bot.business.service.GamingServerService;
import schultz.thomas.discord.bot.business.service.command.Command;
import schultz.thomas.discord.bot.business.service.command.CommandContext;
import schultz.thomas.discord.bot.model.enums.CommandEnum;
import schultz.thomas.discord.bot.model.enums.UserPrivilegeEnum;

import java.util.List;

@Component
@RequiredArgsConstructor
public class StartServerGamingCommand implements Command {

    private final GamingServerService gamingServerService;


    public List<UserPrivilegeEnum> roleNeeded(){ return List.of(UserPrivilegeEnum.ADMINISTRATOR); }

    @Override
    public CommandData getCommandData() {
        return  new CommandDataImpl("start", "lance le serveur de jeu")
                .addOptions(new OptionData(OptionType.STRING, "identifier", "identifiant du serveur", true));
    }

    @Override
    public CommandEnum getEnum(){
        return CommandEnum.START_SGAMING;
    }

    @Override
    public void execute(CommandContext context) {

    }
}
