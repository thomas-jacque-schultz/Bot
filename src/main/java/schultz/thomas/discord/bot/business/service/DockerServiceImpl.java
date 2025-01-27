package schultz.thomas.discord.bot.business.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import schultz.thomas.discord.bot.model.entity.GamingServerEntity;

@Slf4j
@Service
@RequiredArgsConstructor
public class DockerServiceImpl implements DockerService{


    @Override
    public boolean serverStatusChanged(GamingServerEntity gamingServerEntity) {
        // docker ps -a | grep gamingServerEntity.getId() | awk '{print $7}' | grep -q "Up" && echo "true" || echo "false"
        return false;
    }

    @Override
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
