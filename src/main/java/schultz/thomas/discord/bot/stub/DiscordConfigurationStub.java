package schultz.thomas.discord.bot.stub;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.List;

@Configuration
@Profile("offline")
public class DiscordConfigurationStub {

    @Value("${discord.token}")
    private String token;


    @Bean()
    public JDA jda(List<ListenerAdapter> listeners) throws InterruptedException {
        return new JdaStub()    ;
    }
}
