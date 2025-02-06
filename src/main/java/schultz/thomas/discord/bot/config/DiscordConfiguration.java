package schultz.thomas.discord.bot.config;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.hooks.EventListener;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import schultz.thomas.discord.bot.stub.JdaStub;

import java.util.List;

@Configuration
@Profile("!offline")
public class DiscordConfiguration {

    @Value("${discord.token}")
    private String token;


    @Bean()
    public JDA jda(List<ListenerAdapter> listeners) throws InterruptedException {
        return new JdaStub();
    }
}
