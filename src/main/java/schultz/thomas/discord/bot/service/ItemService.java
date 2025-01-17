package schultz.thomas.discord.bot.service;


import schultz.thomas.discord.bot.model.GuildEntity;

import java.util.List;
import java.util.Optional;

public interface ItemService {

    List<GuildEntity> findAllItems();

    Optional<GuildEntity> findItemById(String id);

    GuildEntity saveItem(GuildEntity item);

    void deleteItem(String id);

    GuildEntity updateItem(String id, GuildEntity itemDetails);

    
}