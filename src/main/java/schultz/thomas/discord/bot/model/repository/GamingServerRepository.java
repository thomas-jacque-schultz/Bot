package schultz.thomas.discord.bot.model.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import schultz.thomas.discord.bot.model.entity.GamingServerEntity;

@Repository
public interface GamingServerRepository extends MongoRepository<GamingServerEntity, String> {
}
