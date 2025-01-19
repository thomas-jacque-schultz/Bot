package schultz.thomas.discord.bot.model.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import schultz.thomas.discord.bot.model.entity.GuildEntity;

public interface GuildRepository extends MongoRepository<GuildEntity, String> {
}
