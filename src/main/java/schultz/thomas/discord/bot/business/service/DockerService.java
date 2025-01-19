package schultz.thomas.discord.bot.business.service;

import schultz.thomas.discord.bot.model.entity.ServerEntity;

public interface DockerService {

    public boolean serverStatusChanged(ServerEntity serverEntity);
}
