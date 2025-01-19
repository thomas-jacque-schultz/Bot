package schultz.thomas.discord.bot.model.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document( collection = "guilds" )
public class GuildEntity {
    @Id
    private String id;
    private String name;
    private List<ChannelEntity> channels;
}
