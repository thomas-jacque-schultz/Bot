package schultz.thomas.discord.bot.business.parser;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import schultz.thomas.discord.bot.model.transitoy.DockerContainerState;

import java.util.LinkedHashMap;

@Mapper(componentModel = "spring")
public interface DiscordContainerStateParser {

    @Mapping(target = "running", source = "Running")
    public DockerContainerState dockerContainerStateDto(LinkedHashMap<String, Object> containerState);
}
