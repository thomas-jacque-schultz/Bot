package schultz.thomas.discord.bot.controllers.dto;

public record DiscordChannelDto(
        String id,
        String name,
        boolean subscribed
) {
}
