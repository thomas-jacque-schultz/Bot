package schultz.thomas.discord.bot.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Configuration WebClient for Portainer API
 */
public class PortainerApiConfiguration {

    @Value("${portainer.api.url}")
    private String PORTAINER_BASE_URL ;
    @Value("${portainer.api.username}")
    private static final String USERNAME = "admin";
    @Value("${portainer.api.password}")
    private static final String PASSWORD = "your-password";
    private static final String LOGIN_ENDPOINT = "/api/auth";

    private final AtomicReference<String> tokenCache = new AtomicReference<>();


    @Bean
    public WebClient portainerWebClient(WebClient.Builder builder) {
        return WebClient.builder()
                .filter(ExchangeFilterFunction.ofRequestProcessor(
                        (ClientRequest request) -> Mono.just(
                                ClientRequest.from(request)
                                        .header("Access-Token", getValidToken())
                                        .build()
                        )
                ))
                .build();
    }

    private void refreshToken() {
        WebClient webClient = WebClient.create(PORTAINER_BASE_URL);

        String token = webClient.post()
                .uri(LOGIN_ENDPOINT)
                .bodyValue(Map.of("username", USERNAME, "password", PASSWORD))
                .retrieve()
                .bodyToMono(Map.class)
                .map(response -> (String) response.get("jwt"))
                .block(); // Blocking to ensure token is set at startup

        tokenCache.set(token);
    }

    private String getValidToken() {
        if (tokenCache.get() == null  || isTokenExpired(tokenCache.get())) {
            refreshToken();
        }
        return tokenCache.get();
    }

    private boolean isTokenExpired(String token) {
        // Check if token is expired
        // extract expiration date from token
        // compare with current date


        return false;
    }
}
