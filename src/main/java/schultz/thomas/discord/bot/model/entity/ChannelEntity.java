package schultz.thomas.discord.bot.model.entity;

import java.util.List;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document( collection = "channels" )
public class ChannelEntity {

    @Id
    private String id;

    private String channelId;   // ref to discord channel id
    private String name;        // ref to discord channel name
    private String guildId;     // ref to discord server id

    private boolean read = false;
    private boolean write = false;
}
