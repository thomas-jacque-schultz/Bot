package schultz.thomas.discord.bot.business.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import schultz.thomas.discord.bot.model.entity.ServerEntity;

@Slf4j
@Service
@RequiredArgsConstructor
public class DockerServiceImpl implements DockerService{


    @Override
    public boolean serverStatusChanged(ServerEntity serverEntity) {
        return false;
    }
}
