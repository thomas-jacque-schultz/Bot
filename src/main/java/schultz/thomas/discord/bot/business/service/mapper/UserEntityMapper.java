package schultz.thomas.discord.bot.business.service.mapper;

import org.mapstruct.*;
import schultz.thomas.discord.bot.model.entity.ServerEntity;
import schultz.thomas.discord.bot.model.entity.UserEntity;

@Mapper(componentModel = "spring", collectionMappingStrategy = CollectionMappingStrategy.TARGET_IMMUTABLE)
public interface UserEntityMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateServeurEntityFromSource(UserEntity source, @MappingTarget UserEntity target);
}
