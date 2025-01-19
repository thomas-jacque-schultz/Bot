package schultz.thomas.discord.bot.model.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document( collection = "messages" )
public class MessageEntity {

    private String id;
    private String content;
    private ServerEntity serverEntity;


}
