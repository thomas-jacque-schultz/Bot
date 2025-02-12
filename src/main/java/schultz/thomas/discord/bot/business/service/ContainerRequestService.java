package schultz.thomas.discord.bot.business.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Service
public interface ContainerRequestService {

    public boolean startContainer(String containerName);
    public boolean stopContainer(String containerName);
    public Map<String,String> getContainerState(String containerName);
}
