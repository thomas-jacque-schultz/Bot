package schultz.thomas.discord.bot.business.service.parser;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import schultz.thomas.discord.bot.model.entity.GamingServerEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Mapper
public interface  GameServerParser {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "allServersMessages", ignore = true)
    @Mapping(target = "admins")
    GamingServerEntity toServerEntity(Map<String, String> arguments);

    default List<String> toAdminsList(String admins) {
        if (admins != null && !admins.isEmpty()) {
            return Arrays.stream(admins.split("\\|"))
                    .map(String::trim)
                    .collect(Collectors.toList());
        }
        return null;
    }

}
