package schultz.thomas.discord.bot.model.entity;

import lombok.Data;

@Data
public class MessageEntity {

    public String guildId;
    public String channelId;
    public String messageId;
    public String messageContent;

}
