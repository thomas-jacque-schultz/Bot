package schultz.thomas.discord.bot.service;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import org.springframework.stereotype.Component;

@Component
public class GlobalStateProvider {
    private final Map<String, Guild> guilds = new HashMap<>();
    private final Map<String, TextChannel> channels = new HashMap<>();

    public void addGuild(Guild guild) {
        guilds.put(guild.getId(), guild);
    }

    public void addChannel(TextChannel channel) {
        channels.put(channel.getId(), channel);
    }

    public Guild getGuildById(String guildId) {
        return guilds.get(guildId);
    }

    public TextChannel getChannelById(String channelId) {
        return channels.get(channelId);
    }

    public Map<String, Guild> getAllGuilds() {
        return guilds;
    }

    public Map<String, TextChannel> getAllChannels() {
        return channels;
    }
}
