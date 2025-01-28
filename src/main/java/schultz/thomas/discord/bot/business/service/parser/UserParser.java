package schultz.thomas.discord.bot.business.service.parser;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import schultz.thomas.discord.bot.model.entity.UserEntity;

import java.util.Map;

@Mapper(componentModel = "spring")
public interface UserParser   {

    @Mapping(target = "id", ignore = true)
    UserEntity toUserEntity(Map<String, String> arguments);

}
