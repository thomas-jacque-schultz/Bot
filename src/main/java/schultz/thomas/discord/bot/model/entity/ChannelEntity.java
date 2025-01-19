package schultz.thomas.discord.bot.model.entity;

import java.util.List;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document( collection = "channels" )
public class ChannelEntity {

    private String channelId;
    private String name;
    private List<MessageEntity> messages;

}
