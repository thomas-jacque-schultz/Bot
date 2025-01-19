package schultz.thomas.discord.bot.model.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import schultz.thomas.discord.bot.model.entity.ServerEntity;

public interface ServerRepository extends MongoRepository<ServerEntity, String> {
}
