package schultz.thomas.discord.bot.business.service;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import schultz.thomas.discord.bot.business.service.command.Command;
import schultz.thomas.discord.bot.model.entity.UserEntity;
import schultz.thomas.discord.bot.model.enums.UserPrivilegeEnum;

public interface UserService {
    //TODO Créer une CreateUserCommand
    void createUser(UserEntity ue);
    //TODO UpdateUserCommand
    void updateUser(UserEntity ue);

    public UserPrivilegeEnum getRole(String discordId);

    UserEntity get(String discordId);
}
