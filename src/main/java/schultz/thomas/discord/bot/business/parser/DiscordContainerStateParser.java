package schultz.thomas.discord.bot.business.parser;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import schultz.thomas.discord.bot.model.transitory.DockerContainerState;

import java.util.LinkedHashMap;

@Mapper(componentModel = "spring")
public interface DiscordContainerStateParser {

    @Mapping(target = "running", source = "Running", qualifiedByName = "mapToBoolean")
    public DockerContainerState dockerContainerStateDto(LinkedHashMap<String, Object> containerState);

    @Named("mapToBoolean")
    default boolean mapToBoolean(Object value) {
        return (boolean) value;
    }
}
