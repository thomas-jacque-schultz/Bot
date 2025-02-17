package schultz.thomas.discord.bot.business.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.actuate.metrics.startup.StartupTimeMetricsListener;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import schultz.thomas.discord.bot.business.dtos.DockerContainerStateDto;
import schultz.thomas.discord.bot.business.parser.DiscordContainerStateParser;
import schultz.thomas.discord.bot.model.response.DockerContainerInfo;

import java.util.LinkedHashMap;
import java.util.Objects;

@RequiredArgsConstructor
@Service("portainerRequestService")
public class PortainerRequestService implements ContainerRequestService {

    @Qualifier("portainerRestClient")
    private final RestClient restClient;

    private final DiscordContainerStateParser discordContainerStateParser;

    private final StartupTimeMetricsListener startupTimeMetrics;

    @Override
    public boolean startContainer(String containerName) {
        String response = restClient.post()
                .uri("/containers/{name}/start", containerName)
                .retrieve()
                .body(String.class); // Modify based on actual response

        return response != null;
    }

    @Override
    public boolean stopContainer(String containerName) {
        String response = restClient.post()
                .uri("/containers/{name}/stop", containerName)
                .retrieve()
                .body(String.class);

        return response != null;
    }

    @Override
    public DockerContainerStateDto getContainerState(String containerName) {
        LinkedHashMap<String, Object> answer = restClient.get()
                .uri("/containers/{name}/json", containerName)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(new ParameterizedTypeReference<LinkedHashMap<String, Object>>() {
                });
        return discordContainerStateParser.dockerContainerStateDto((LinkedHashMap<String, Object>) answer.get("State"));
    }
}
