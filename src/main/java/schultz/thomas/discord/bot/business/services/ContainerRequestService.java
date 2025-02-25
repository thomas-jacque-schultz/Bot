package schultz.thomas.discord.bot.business.services;

import org.springframework.stereotype.Service;
import schultz.thomas.discord.bot.model.transitory.DockerContainerState;

@Service
public interface ContainerRequestService {

    public boolean startContainer(String containerName);
    public boolean stopContainer(String containerName);
    public DockerContainerState getContainerState(String containerName);
}
