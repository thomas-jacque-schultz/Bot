package schultz.thomas.discord.bot.business.service.parser;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import schultz.thomas.discord.bot.model.entity.UserEntity;
import schultz.thomas.discord.bot.model.enums.UserPrivilegeEnum;

import java.util.Map;

@Mapper(componentModel = "spring", uses = UserPrivilegeEnum.class)
public interface UserParser   {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "discordId", source = "discord-id")
    @Mapping(target = "discordUsername", source = "discord-username")
    UserEntity toUserEntity(Map<String, String> arguments);

}
