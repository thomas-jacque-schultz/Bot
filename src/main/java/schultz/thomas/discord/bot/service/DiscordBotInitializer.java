package schultz.thomas.discord.bot.service;

import schultz.thomas.discord.bot.provider.GlobalStateProvider;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class DiscordBotInitializer {

    private final GlobalStateProvider stateProvider;
    private JDA jda;

    public DiscordBotInitializer(GlobalStateProvider stateProvider) {
        this.stateProvider = stateProvider;
    }

    @PostConstruct
    public void init() throws Exception {
        jda = JDABuilder.createDefault("YOUR_BOT_TOKEN").build().awaitReady();

        // Ajouter les guilds et channels au StateProvider
        for (Guild guild : jda.getGuilds()) {
            stateProvider.addGuild(guild);
            for (TextChannel channel : guild.getTextChannels()) {
                stateProvider.addChannel(channel);
            }
        }
    }

    public JDA getJDA() {
        return jda;
    }
}
