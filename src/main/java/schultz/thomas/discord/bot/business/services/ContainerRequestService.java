package schultz.thomas.discord.bot.business.services;

import org.springframework.stereotype.Service;
import schultz.thomas.discord.bot.business.dtos.DockerContainerStateDto;
import schultz.thomas.discord.bot.model.response.DockerContainerInfo;

import java.util.LinkedHashMap;
import java.util.Objects;

@Service
public interface ContainerRequestService {

    public boolean startContainer(String containerName);
    public boolean stopContainer(String containerName);
    public DockerContainerStateDto getContainerState(String containerName);
}
