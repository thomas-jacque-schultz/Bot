package schultz.thomas.discord.bot.config;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.List;

@Configuration
@Profile("!offline")
public class DiscordConfiguration {

    @Value("${discord.token}")
    private String token;


    @Bean
    public JDA jda(List<ListenerAdapter> listeners) throws InterruptedException {
        return JDABuilder
                .createDefault(token)
                .enableIntents(GatewayIntent.MESSAGE_CONTENT)
                .enableIntents(GatewayIntent.GUILD_MEMBERS)
                .enableIntents(GatewayIntent.GUILD_MESSAGE_TYPING)
                .enableIntents(GatewayIntent.GUILD_MESSAGES)
                .addEventListeners(listeners.toArray(new ListenerAdapter[0]))
                .build()
                .awaitReady();
    }
}
