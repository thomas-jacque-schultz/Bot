package schultz.thomas.discord.bot.business.service;

import org.springframework.stereotype.Service;
import schultz.thomas.discord.bot.model.response.DockerContainerInfo;

import java.util.LinkedHashMap;
import java.util.Objects;

@Service
public interface ContainerRequestService {

    public boolean startContainer(String containerName);
    public boolean stopContainer(String containerName);
    public LinkedHashMap<String, Object> getContainerState(String containerName);
}
