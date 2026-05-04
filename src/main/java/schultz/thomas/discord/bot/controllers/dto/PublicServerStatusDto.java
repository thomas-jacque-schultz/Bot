package schultz.thomas.discord.bot.controllers.dto;

import java.time.Instant;

public record PublicServerStatusDto(
        String name,
        String status,
        Instant lastStatusCheckAt
) {
}
