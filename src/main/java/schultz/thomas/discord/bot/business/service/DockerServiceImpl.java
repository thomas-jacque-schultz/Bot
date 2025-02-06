package schultz.thomas.discord.bot.business.service;


import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.core.DockerClientBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import schultz.thomas.discord.bot.model.entity.GamingServerEntity;



@Slf4j
@Service
@RequiredArgsConstructor
/**
 * Docker service implementation
 * Call portainer API to manage docker containers
 */
public class DockerServiceImpl implements DockerService{

    // http client object to call portainer API
    






    @Override
    public boolean serverStatusChanged(GamingServerEntity gamingServerEntity) {

        // call portainer API to get container status


        return true;
    }

    @Override
    /**
     * Start a container
     * /api/endpoints/<ENVIRONMENT_ID>/docker/containers/<CONTAINER_ID>/start
     */
    public boolean startServer(GamingServerEntity gamingServerEntity) {
        // call docker socket to start container
        return true;
    }

    @Override
    public boolean stopServer(GamingServerEntity gamingServerEntity) {
        // call docker socket to pause container
        return true;
    }
}
