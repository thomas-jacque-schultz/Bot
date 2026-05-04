package schultz.thomas.discord.bot.controllers.dto;

import java.util.List;

public record DiscordChannelSubscriptionRequest(
        List<DiscordChannelSelection> channels
) {
}
