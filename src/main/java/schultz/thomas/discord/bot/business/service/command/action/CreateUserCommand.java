package schultz.thomas.discord.bot.business.service.command.action;


import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.internal.interactions.CommandDataImpl;
import org.springframework.stereotype.Component;
import schultz.thomas.discord.bot.business.service.UserService;
import schultz.thomas.discord.bot.business.service.command.Command;
import schultz.thomas.discord.bot.business.service.command.CommandContext;
import schultz.thomas.discord.bot.business.service.parser.UserParser;
import schultz.thomas.discord.bot.model.enums.CommandEnum;
import schultz.thomas.discord.bot.model.enums.UserPrivilegeEnum;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class CreateUserCommand implements Command {

    private final UserService userService;

    private final UserParser userParser;

    public List<UserPrivilegeEnum> roleNeeded(){
        return new ArrayList<>( List.of(UserPrivilegeEnum.ADMINISTRATOR));
    }

    @Override
    public CommandData getCommandData() {
        return  new CommandDataImpl("create-user", "mets à jour les autorisations d'un utilisateur")
                .addOptions(new OptionData(OptionType.STRING, "discordId", "id discord", true))
                .addOptions(new OptionData(OptionType.STRING, "discord-username", "pseudo", true))
                .addOptions(new OptionData(OptionType.STRING, "privilege", "privilège attribué", true));
    }

    @Override
    public CommandEnum getEnum(){
        return CommandEnum.CREATE_USER;
    }

    @Override
    public void execute(CommandContext context) {
        Map<String, String> options =  context.getCommand().getOptions().stream().collect(Collectors.toMap(OptionMapping::getName, OptionMapping::getAsString));
        userService.createUser(userParser.toUserEntity(options));
        "".to
    }


}
