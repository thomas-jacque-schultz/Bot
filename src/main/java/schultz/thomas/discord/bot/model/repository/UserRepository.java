package schultz.thomas.discord.bot.model.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import schultz.thomas.discord.bot.model.entity.UserEntity;

@Repository
public interface UserRepository extends MongoRepository<UserEntity, String> {

    UserEntity findByDiscordId(String discordId);
}
