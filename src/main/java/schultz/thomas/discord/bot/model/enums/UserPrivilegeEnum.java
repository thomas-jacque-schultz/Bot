package schultz.thomas.discord.bot.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;

@Getter
@RequiredArgsConstructor
public enum UserPrivilegeEnum {

        USER("user"),
        MODERATOR("modo"),
        ADMINISTRATOR("admin"),
        OWNER("moi");

        private final String privilegeName;
}

