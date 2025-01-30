package schultz.thomas.discord.bot.business.service.parser;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import schultz.thomas.discord.bot.model.entity.GamingServerEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface  GameServerParser {

    //ignored fields are not mapped
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "allServersMessages", ignore = true)
    @Mapping(target = "running", ignore = true)
    //discord command option with uppercase
    @Mapping(target = "playersMax", source = "players-max")
    @Mapping(target = "urlConnection", source = "url-connection")
    //string to list of string
    @Mapping(target = "admins", source = "admins", qualifiedByName = "toAdminsList")
    GamingServerEntity toServerEntity(Map<String, String> arguments);

    @Named("toAdminsList")
    default List<String> toAdminsList(String admins) {
        if (admins != null && !admins.isEmpty()) {
            return Arrays.stream(admins.split("\\|"))
                    .map(String::trim)
                    .collect(Collectors.toList());
        }
        return null;
    }


}
