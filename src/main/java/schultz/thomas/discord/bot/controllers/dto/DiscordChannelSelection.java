package schultz.thomas.discord.bot.controllers.dto;

public record DiscordChannelSelection(
        String guildId,
        String channelId,
        String channelName
) {
}
