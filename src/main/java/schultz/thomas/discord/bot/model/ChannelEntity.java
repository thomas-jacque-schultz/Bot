package schultz.thomas.discord.bot.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document
public class ChannelEntity {
    
    @Id
    private String id;

    private String name;

    private List<AbstractMessageEntity> messages;

}
