package schultz.thomas.discord.bot.model.transitory;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class DockerContainerState {

    private boolean running=false;

}
