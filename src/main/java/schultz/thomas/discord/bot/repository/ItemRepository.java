package schultz.thomas.discord.bot.repository;

import schultz.thomas.discord.bot.model.GuildEntity;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ItemRepository extends MongoRepository<GuildEntity, String> {
}
