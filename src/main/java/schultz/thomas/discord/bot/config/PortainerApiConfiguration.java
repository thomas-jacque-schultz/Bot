package schultz.thomas.discord.bot.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

/**
 * Configuration WebClient for Portainer API
 */
@RequiredArgsConstructor
@Configuration
public class PortainerApiConfiguration {

    private final String BASE_URL =  "http://localhost:9000/api/endpoints/1/docker";

    private final String token = "ptr_MW6d8jVA8uddZkVczAPaGscrPa31MlRy5L0B4Lem5+4=";

    /**
     * Create a WebClient for Portainer API with X-API-Key header
     * @return
     */
    @Bean("portainerRestClient")
    public RestClient restClient() {
        return RestClient.builder()
                .baseUrl(BASE_URL)
                .defaultHeader("X-API-Key", token)
                .build();
    }
}
