package schultz.thomas.discord.bot.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;

@Getter
@RequiredArgsConstructor
public enum GamesNameEnum {

    MINECRAFT("Minecraft"),
    VALHEIM("Valheim"),
    ARK("Ark"),
    RUST("Rust"),
    GARRYSMOD("Garry's Mod"),
    PALWORLD("PalWorld"),
    FIVEM("FiveM");

    private final String gameName;
}
