package schultz.thomas.discord.bot.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

/**
 * Configuration WebClient for Portainer API
 */
@RequiredArgsConstructor
@Configuration
public class PortainerApiConfiguration {

    @Value("${portainer.base-url}")
    private String BASE_URL;

    @Value("${portainer.token}")
    private String TOKEN;

    /**
     * Create a WebClient for Portainer API with X-API-Key header
     * @return
     */
    @Bean("portainerRestClient")
    public RestClient restClient() {
        return RestClient.builder()
                .baseUrl(BASE_URL)
                .defaultHeader("X-API-Key", TOKEN)
                .build();
    }
}
