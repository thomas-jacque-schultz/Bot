package schultz.thomas.discord.bot.business.service.mapper;

import org.mapstruct.*;
import schultz.thomas.discord.bot.model.entity.ServerEntity;

@Mapper(componentModel = "spring", collectionMappingStrategy = CollectionMappingStrategy.TARGET_IMMUTABLE)
public interface ServeurEntityMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "allServersMessages", ignore = true)
    void updateServeurEntityFromSource(ServerEntity source, @MappingTarget ServerEntity target);
}
