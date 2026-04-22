package schultz.thomas.discord.bot.controllers.dto;

import java.time.Instant;

public record GamingServerStatusHistoryEntryDto(
        String status,
        Instant startedAt
) {
}
