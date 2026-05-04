package schultz.thomas.discord.bot.model.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import schultz.thomas.discord.bot.model.entity.UserEntity;

public interface UserRepository extends MongoRepository<UserEntity, String> {
}
