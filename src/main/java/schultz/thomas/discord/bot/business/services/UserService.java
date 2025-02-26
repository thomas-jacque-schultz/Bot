package schultz.thomas.discord.bot.business.services;

import schultz.thomas.discord.bot.model.entity.UserEntity;
import schultz.thomas.discord.bot.model.enums.UserPrivilegeEnum;

public interface UserService {

    void createUser(UserEntity ue);

    void updateUser(UserEntity ue);

    UserPrivilegeEnum getRole(String discordId);

    UserEntity get(String discordId);
}
