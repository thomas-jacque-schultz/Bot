package schultz.thomas.discord.bot.model.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import schultz.thomas.discord.bot.model.entity.ChannelEntity;

public interface ChannelRepository extends MongoRepository<ChannelEntity, String> {
}
