package schultz.thomas.discord.bot.model.entity;

import lombok.Data;

@Data
public class DockerStateReport {

    public String gamingServerIdentifier;
    public boolean running;

}
