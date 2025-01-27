package schultz.thomas.discord.bot.business.service.parser;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import schultz.thomas.discord.bot.model.entity.GamingServerEntity;

@Mapper
public interface  GameServerParser extends AbstractMapper {

    @Mapping(source = "payload", target = "serverInformation", qualifiedByName = "extractStringValue")
    @Mapping(source = "payload", target = "name", qualifiedByName = "extractStringValue")
    @Mapping(source = "payload", target = "url", qualifiedByName = "extractStringValue")
    @Mapping(source = "payload", target = "playerCount", qualifiedByName = "extractIntValue")
    @Mapping(source = "payload", target = "mods", qualifiedByName = "extractStringValue")
    @Mapping(source = "payload", target = "version", qualifiedByName = "extractStringValue")
    @Mapping(source = "payload", target = "regles", qualifiedByName = "extractStringValue")
    @Mapping(source = "payload", target = "allowedAuthors", qualifiedByName = "extractStringListValue")
    GamingServerEntity toServerEntity(String payload);

}
