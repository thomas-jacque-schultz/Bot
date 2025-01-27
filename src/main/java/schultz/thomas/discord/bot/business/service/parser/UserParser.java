package schultz.thomas.discord.bot.business.service.parser;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import schultz.thomas.discord.bot.model.entity.UserEntity;

import java.util.List;

@Mapper
public interface UserParser extends AbstractMapper {

    @Mapping(source = "payload", target = "discordId", qualifiedByName = "extractStringValue")
    @Mapping(source = "payload", target = "discordUsername", qualifiedByName = "extractStringValue")
    @Mapping(source = "payload", target = "privilege", qualifiedByName = "extractUserPrivilegeEnumValue")
    UserEntity toUserEntity(List<String> payload);

}
