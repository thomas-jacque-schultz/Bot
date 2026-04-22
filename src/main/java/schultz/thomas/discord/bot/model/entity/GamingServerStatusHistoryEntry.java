package schultz.thomas.discord.bot.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import schultz.thomas.discord.bot.model.enums.ServerStatusEnum;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GamingServerStatusHistoryEntry {

    private ServerStatusEnum status;

    /** Timestamp when this status started. End is inferred by the next history entry. */
    private Instant startedAt;
}
