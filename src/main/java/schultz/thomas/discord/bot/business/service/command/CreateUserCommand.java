package schultz.thomas.discord.bot.business.service.command;


import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.events.GenericEvent;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import schultz.thomas.discord.bot.business.service.ServerService;
import schultz.thomas.discord.bot.model.enums.CommandEnum;
import schultz.thomas.discord.bot.model.enums.UserPrivilegeEnum;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Component
public class CreateUserCommand implements Command{

    private final ServerService serverService;

    public List<UserPrivilegeEnum> roleNeeded(){ return List.of(UserPrivilegeEnum.ADMINISTRATOR); }

    public boolean isValid(CommandContext context){
        return true;
    }

    @Override
    public void execute(CommandContext context) {


    }

    @Override
    public CommandEnum getEnum(){
        return CommandEnum.CREATE_USER;
    }

}
