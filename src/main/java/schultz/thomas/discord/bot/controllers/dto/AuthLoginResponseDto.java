package schultz.thomas.discord.bot.controllers.dto;

public record AuthLoginResponseDto(String accessToken, String tokenType, long expiresInSeconds) {
}
