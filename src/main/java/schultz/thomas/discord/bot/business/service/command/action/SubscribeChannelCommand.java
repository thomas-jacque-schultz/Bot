package schultz.thomas.discord.bot.business.service.command.action;


import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.internal.interactions.CommandDataImpl;
import org.springframework.stereotype.Component;
import schultz.thomas.discord.bot.business.service.GamingServerService;
import schultz.thomas.discord.bot.business.service.UserService;
import schultz.thomas.discord.bot.business.service.command.Command;
import schultz.thomas.discord.bot.business.service.command.CommandContext;
import schultz.thomas.discord.bot.business.service.parser.GameServerParser;
import schultz.thomas.discord.bot.model.entity.ChannelEntity;
import schultz.thomas.discord.bot.model.enums.CommandEnum;
import schultz.thomas.discord.bot.model.enums.UserPrivilegeEnum;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    public void execute(CommandContext context) {
        ChannelEntity channel = new ChannelEntity();
        channel.setChannelId(context.getCommand().getChannel().getId());
        channel.setName(context.getCommand().getChannel().getName());
        channel.setGuildId(context.getCommand().getGuild().getId());
        channel.setUsedForGamingServerStatus(true);

        try {
            gamingServerService.subscribeDiscordChannel(channel);
            gamingServerService.createMessageStateFromDiscordChannel(channel, context.getJda());
        } catch (IllegalArgumentException e) {
            context.getCommand().reply("J'écrit déjà dans ce channel").queue();
            return;
        }
        context.getCommand().reply("J'utilise maintenant ce channel pour les updates").queue();
    }


}
