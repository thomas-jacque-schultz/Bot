package schultz.thomas.discord.bot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import schultz.thomas.discord.bot.service.ItemService;
import schultz.thomas.discord.bot.model.GuildEntity;

@RestController
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping
    public List<GuildEntity> getAllItems() {
        return itemService.findAllItems();
    }

    @GetMapping("/{id}")
    public ResponseEntity<GuildEntity> getItemById(@PathVariable String id) {
        return itemService.findItemById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public GuildEntity createItem(@RequestBody GuildEntity item) {
        return itemService.saveItem(item);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GuildEntity> updateItem(@PathVariable String id, @RequestBody GuildEntity itemDetails) {
        try {
            GuildEntity updatedItem = itemService.updateItem(id, itemDetails);
            return ResponseEntity.ok(updatedItem);
        } catch (RuntimeException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteItem(@PathVariable String id) {
        try {
            itemService.deleteItem(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException ex) {
            return ResponseEntity.notFound().build();
        }
    }
}
