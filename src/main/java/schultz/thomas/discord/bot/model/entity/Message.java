package schultz.thomas.discord.bot.model.entity;

import lombok.Data;

@Data
public class Message {

    public String guildId;
    public String channelId;
    public String messageId;
    public String messageContent;

}
