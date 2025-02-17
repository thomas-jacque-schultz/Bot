package schultz.thomas.discord.bot.business.command.action;


import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.internal.interactions.CommandDataImpl;
import org.springframework.stereotype.Component;
import schultz.thomas.discord.bot.business.command.Command;
import schultz.thomas.discord.bot.business.command.CommandContext;
import schultz.thomas.discord.bot.business.service.exceptions.CommandFailedException;
import schultz.thomas.discord.bot.business.service.parser.GameServerParser;
import schultz.thomas.discord.bot.model.entity.ChannelEntity;
import schultz.thomas.discord.bot.model.enums.CommandEnum;
import schultz.thomas.discord.bot.model.enums.UserPrivilegeEnum;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Component
public class SubscribeChannelCommand implements Command {

    private final GamingServerService gamingServerService;

    private final GameServerParser gameServerParser;

    public List<UserPrivilegeEnum> roleNeeded(){
        return new ArrayList<>( List.of(UserPrivilegeEnum.OWNER));
    }

    @Override
    public CommandData getCommandData() {
        return  new CommandDataImpl(CommandEnum.SUSBSCRIBE_A_CHANNEL.getCommandName(), "affiche l'état des serveurs dans ce channel");
    }

    @Override
    public CommandEnum getEnum(){
        return CommandEnum.SUSBSCRIBE_A_CHANNEL;
    }

    @Override
    public String execute(CommandContext context) {
        ChannelEntity channel = new ChannelEntity();
        channel.setChannelId(context.getOptions().get("channel-id"));
        channel.setName(context.getOptions().get("channel-name"));
        channel.setGuildId(context.getOptions().get("guild-id"));
        channel.setUsedForGamingServerStatus(true);

        try {
            gamingServerService.subscribeDiscordChannel(channel);
            gamingServerService.createMessageStateFromDiscordChannel(channel, context.getJda());
        } catch (IllegalArgumentException e) {
            throw new CommandFailedException("Impossible de créer le channel : " + e.getMessage());
        }
        return "J'utilise maintenant ce channel pour les updates";
    }


}
