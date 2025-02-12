package schultz.thomas.discord.bot.business.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import schultz.thomas.discord.bot.model.response.DockerContainerInfo;

import java.util.LinkedHashMap;
import java.util.Objects;

@RequiredArgsConstructor
@Service("portainerRequestService")
public class PortainerRequestService implements ContainerRequestService{

    @Qualifier("portainerRestClient")
    private final RestClient restClient;

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
    public LinkedHashMap<String, Object> getContainerState(String containerName) {
        return restClient.get()
                .uri("/containers/{name}/json", containerName)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(new ParameterizedTypeReference<LinkedHashMap<String, Object>>() {});
    }
}
