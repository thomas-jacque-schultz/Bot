package schultz.thomas.discord.bot.service;

import schultz.thomas.discord.bot.model.GuildEntity;
import schultz.thomas.discord.bot.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;

    @Autowired
    public ItemServiceImpl(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }
    
    @Override
    public List<GuildEntity> findAllItems() {
        return itemRepository.findAll();
    }

    @Override
    public Optional<GuildEntity> findItemById(String id) {
        return itemRepository.findById(id);
    }

    @Override
    public GuildEntity saveItem(GuildEntity item) {
        return itemRepository.save(item);
    }

    @Override
    public void deleteItem(String id) {
        itemRepository.deleteById(id);
    }

    @Override
    public GuildEntity updateItem(String id, GuildEntity itemDetails) {
        return itemRepository.findById(id)
                .map(item -> {
                    item.setName(itemDetails.getName());
                    item.setDescription(itemDetails.getDescription());
                    return itemRepository.save(item);
                }).orElseThrow(() -> new RuntimeException("Item not found with id " + id));
    }
}