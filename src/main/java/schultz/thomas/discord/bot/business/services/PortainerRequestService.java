package schultz.thomas.discord.bot.business.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;
import schultz.thomas.discord.bot.model.transitory.DockerContainerState;

import java.util.Map;

@RequiredArgsConstructor
@Service("portainerRequestService")
public class PortainerRequestService implements ContainerRequestService {

    @Qualifier("portainerRestClient")
    private final RestClient restClient;

    private final PortainerErrorLogger portainerErrorLogger;

    @Override
    public boolean startContainer(Integer stackId) {
        try {
            restClient.post()
                    .uri("/api/stacks/{id}/start?endpointId=2", stackId)
                    .retrieve()
                    .body(String.class);
            return true;
        } catch (RestClientException e) {
            portainerErrorLogger.logRestClientException("start-stack", String.valueOf(stackId), e);
            throw e;
        }
    }

    @Override
    public boolean stopContainer(Integer stackId) {
        try {
            restClient.post()
                    .uri("/api/stacks/{id}/stop?endpointId=2", stackId)
                    .retrieve()
                    .body(String.class);
            return true;
        } catch (RestClientException e) {
            portainerErrorLogger.logRestClientException("stop-stack", String.valueOf(stackId), e);
            throw e;
        }
    }

    @Override
    public DockerContainerState getContainerState(Integer stackId) {
        try {
            Map<String, Object> stack = restClient.get()
                    .uri("/api/stacks/{id}", stackId)
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .body(new ParameterizedTypeReference<Map<String, Object>>() {});

            DockerContainerState state = new DockerContainerState();
            if (stack != null) {
                state.setRunning(Integer.valueOf(1).equals(stack.get("Status")));
            }
            return state;
        } catch (RestClientException e) {
            portainerErrorLogger.logRestClientException("state", String.valueOf(stackId), e);
            throw e;
        }
    }

}
