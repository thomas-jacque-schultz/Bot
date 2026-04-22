package schultz.thomas.discord.bot.business.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Component
public class PortainerErrorLogger {

    private static final Pattern ENDPOINT_ID_PATTERN = Pattern.compile(".*/api/endpoints/(\\d+)/docker/?$");

    @Value("${portainer.base-url}")
    private String portainerBaseUrl;

    public void logRestClientException(String operation, String containerName, RestClientException exception) {
        String endpointId = extractEndpointId(portainerBaseUrl);

        if (exception instanceof ResourceAccessException) {
            log.error(
                    "Portainer connectivity error for op='{}', container='{}', baseUrl='{}', endpointId='{}'. Cause: {}",
                    operation,
                    containerName,
                    portainerBaseUrl,
                    endpointId,
                    exception.getMessage()
            );
            return;
        }

        if (exception instanceof RestClientResponseException responseException) {
            int status = responseException.getStatusCode().value();
            String body = responseException.getResponseBodyAsString();

            if (status == 401 || status == 403) {
                log.error(
                        "Portainer auth error for op='{}', container='{}', baseUrl='{}', endpointId='{}', status={}. Check PORTAINER_TOKEN. Body: {}",
                        operation,
                        containerName,
                        portainerBaseUrl,
                        endpointId,
                        status,
                        body
                );
                return;
            }

            if (status == 404) {
                if (body != null && body.toLowerCase().contains("environment")) {
                    log.error(
                            "Portainer endpoint mismatch for op='{}', container='{}', baseUrl='{}', endpointId='{}', status=404. Endpoint ID is likely invalid/deleted. Body: {}",
                            operation,
                            containerName,
                            portainerBaseUrl,
                            endpointId,
                            body
                    );
                    return;
                }

                log.warn(
                        "Portainer returned 404 for op='{}', container='{}', baseUrl='{}', endpointId='{}'. This can be a wrong container name or wrong URL path. Body: {}",
                        operation,
                        containerName,
                        portainerBaseUrl,
                        endpointId,
                        body
                );
                return;
            }

            log.error(
                    "Portainer HTTP error for op='{}', container='{}', baseUrl='{}', endpointId='{}', status={}. Body: {}",
                    operation,
                    containerName,
                    portainerBaseUrl,
                    endpointId,
                    status,
                    body
            );
            return;
        }

        log.error(
                "Portainer client error for op='{}', container='{}', baseUrl='{}', endpointId='{}'. Cause: {}",
                operation,
                containerName,
                portainerBaseUrl,
                endpointId,
                exception.getMessage(),
                exception
        );
    }

    public void logInvalidPayloadMissingState(String operation, String containerName, Map<String, Object> response) {
        log.error(
                "Portainer invalid payload for op='{}', container='{}', baseUrl='{}', endpointId='{}': missing 'State' key. Raw payload keys='{}'",
                operation,
                containerName,
                portainerBaseUrl,
                extractEndpointId(portainerBaseUrl),
                response == null ? "null" : response.keySet()
        );
    }

    public void logInvalidPayloadStateType(String operation, String containerName, Object stateObject) {
        log.error(
                "Portainer invalid payload for op='{}', container='{}', baseUrl='{}', endpointId='{}': 'State' is not an object ({})",
                operation,
                containerName,
                portainerBaseUrl,
                extractEndpointId(portainerBaseUrl),
                stateObject == null ? "null" : stateObject.getClass().getName()
        );
    }

    private String extractEndpointId(String baseUrl) {
        if (baseUrl == null || baseUrl.isBlank()) {
            return "unknown";
        }

        Matcher matcher = ENDPOINT_ID_PATTERN.matcher(baseUrl);
        return matcher.matches() ? matcher.group(1) : "not-found-in-url";
    }
}
