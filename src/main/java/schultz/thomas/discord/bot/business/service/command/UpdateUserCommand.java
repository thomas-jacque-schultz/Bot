package schultz.thomas.discord.bot.business.service.command;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import schultz.thomas.discord.bot.business.service.UserService;
import schultz.thomas.discord.bot.business.service.parser.UserParser;
import schultz.thomas.discord.bot.model.enums.CommandEnum;
import schultz.thomas.discord.bot.model.enums.UserPrivilegeEnum;

import java.util.List;

@RequiredArgsConstructor
@Component
public class UpdateUserCommand implements Command{

    private final UserService userService;

    private final UserParser userParser;

    public List<UserPrivilegeEnum> roleNeeded(){ return List.of(UserPrivilegeEnum.ADMINISTRATOR); }

    public boolean isValid(CommandContext context){
        return true;
    }

    @Override
    public void execute(CommandContext context) {
        userService.updateUser(userParser.toUserEntity(context.getPayload()));
    }

    @Override
    public CommandEnum getEnum(){
        return CommandEnum.UPDATE_USER;
    }

}
