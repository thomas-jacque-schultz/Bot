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

        @Named("stringToUserPrivilegeEnum")
        public static UserPrivilegeEnum stringToUserPrivilegeEnum(String privilege) {
                if (privilege == null) {
                        return null;
                }
                for (UserPrivilegeEnum value : UserPrivilegeEnum.values()) {
                        if (value.getPrivilegeName().equalsIgnoreCase(privilege)) {
                                return value;
                        }
                }
                throw new IllegalArgumentException("Invalid privilege: " + privilege);
        }

}

