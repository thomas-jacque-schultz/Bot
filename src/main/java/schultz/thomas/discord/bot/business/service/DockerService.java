package schultz.thomas.discord.bot.business.service;

import schultz.thomas.discord.bot.model.entity.GamingServerEntity;

public interface DockerService {

    public boolean serverStatusChanged(GamingServerEntity gamingServerEntity);

    public boolean startServer(GamingServerEntity gamingServerEntity);

    public boolean stopServer(GamingServerEntity gamingServerEntity);
}
