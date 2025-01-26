package schultz.thomas.discord.bot.business.service.command;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import schultz.thomas.discord.bot.business.service.ServerService;
import schultz.thomas.discord.bot.model.enums.CommandEnum;
import schultz.thomas.discord.bot.model.enums.UserPrivilegeEnum;

import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class StartServerGamingCommand implements Command{

    private final ServerService serverService;


    public List<UserPrivilegeEnum> roleNeeded(){ return List.of(UserPrivilegeEnum.ADMINISTRATOR); }

    @Override
    public boolean isValid(CommandContext context) {
        return false;
    }

    @Override
    public void execute(CommandContext context) {

    }

    @Override
    public CommandEnum getEnum(){
        return CommandEnum.START_SGAMING;
    }

}
