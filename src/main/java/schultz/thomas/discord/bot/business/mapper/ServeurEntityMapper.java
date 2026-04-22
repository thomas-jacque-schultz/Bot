package schultz.thomas.discord.bot.business.mapper;

import org.mapstruct.*;
import schultz.thomas.discord.bot.controllers.dto.GamingServerDto;
import schultz.thomas.discord.bot.controllers.dto.GamingServerStatusHistoryEntryDto;
import schultz.thomas.discord.bot.model.entity.GamingServerEntity;
import schultz.thomas.discord.bot.model.entity.GamingServerStatusHistoryEntry;
import schultz.thomas.discord.bot.model.enums.GamesNameEnum;
import schultz.thomas.discord.bot.model.enums.ServerStatusEnum;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Mapper(componentModel = "spring", collectionMappingStrategy = CollectionMappingStrategy.TARGET_IMMUTABLE)
public interface ServeurEntityMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "lastStatusCheckAt", ignore = true)
    @Mapping(target = "lastStatusChangeAt", ignore = true)
    @Mapping(target = "statusHistory", ignore = true)
    void updateServeurEntityFromSource(GamingServerEntity source, @MappingTarget GamingServerEntity target);

    @Mapping(target = "status", ignore = true)
    @Mapping(target = "lastStatusCheckAt", ignore = true)
    @Mapping(target = "lastStatusChangeAt", ignore = true)
    @Mapping(target = "statusHistory", ignore = true)
    @Mapping(target = "gameName", source = "gameName", qualifiedByName = "stringToGamesNameEnum")
    GamingServerEntity toEntity(GamingServerDto dto);

    @Mapping(target = "gameName", source = "gameName", qualifiedByName = "gamesNameEnumToString")
    @Mapping(target = "status", source = "status", qualifiedByName = "serverStatusEnumToString")
    @Mapping(target = "statusHistory", source = "statusHistory", qualifiedByName = "statusHistoryToDto")
    GamingServerDto toDto(GamingServerEntity entity);

    @Named("stringToGamesNameEnum")
    default GamesNameEnum stringToGamesNameEnum(String value) {
        if (value == null) return null;
        return Arrays.stream(GamesNameEnum.values())
                .filter(g -> g.name().equalsIgnoreCase(value) || g.getGameName().equalsIgnoreCase(value))
                .findFirst()
                .orElse(null);
    }

    @Named("gamesNameEnumToString")
    default String gamesNameEnumToString(GamesNameEnum value) {
        return value != null ? value.name() : null;
    }

    @Named("serverStatusEnumToString")
    default String serverStatusEnumToString(ServerStatusEnum value) {
        return value != null ? value.name() : null;
    }

    @Named("statusHistoryToDto")
    default List<GamingServerStatusHistoryEntryDto> statusHistoryToDto(List<GamingServerStatusHistoryEntry> value) {
        if (value == null) return Collections.emptyList();
        return value.stream()
                .map(this::statusHistoryEntryToDto)
                .toList();
    }

    default GamingServerStatusHistoryEntryDto statusHistoryEntryToDto(GamingServerStatusHistoryEntry value) {
        if (value == null) return null;
        return new GamingServerStatusHistoryEntryDto(serverStatusEnumToString(value.getStatus()), value.getStartedAt());
    }
}
