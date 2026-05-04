package schultz.thomas.discord.bot.business.mapper;

import org.mapstruct.*;
import schultz.thomas.discord.bot.model.entity.GamingServerEntity;

@Mapper(componentModel = "spring", collectionMappingStrategy = CollectionMappingStrategy.TARGET_IMMUTABLE)
public interface ServeurEntityMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "status", ignore = true)
    void updateServeurEntityFromSource(GamingServerEntity source, @MappingTarget GamingServerEntity target);
}
