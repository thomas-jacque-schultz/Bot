package schultz.thomas.discord.bot.model.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import schultz.thomas.discord.bot.model.entity.GamingServerEntity;

public interface GamingServerRepository extends MongoRepository<GamingServerEntity, String> {
}
