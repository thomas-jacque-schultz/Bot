package schultz.thomas.discord.bot.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Configuration WebClient for Portainer API
 */
@RequiredArgsConstructor
@Configuration
public class PortainerApiConfiguration {

    private final String BASE_URL =  "https://localhost:22002";

    private final String token = "ptr_Ih4JwtMxEHaicDNWwsC0D6cah7GnyEymxjOziN+aQNs=";

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
