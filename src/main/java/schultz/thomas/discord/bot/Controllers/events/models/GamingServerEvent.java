package schultz.thomas.discord.bot.controllers.events.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;
import schultz.thomas.discord.bot.model.entity.GamingServerEntity;

@Getter
@Setter
public class GamingServerEvent extends ApplicationEvent {

    private GamingServerEntity gamingServerEntity;
    private GamingServerEventType gamingServerEventType;

    public GamingServerEvent(Object source, GamingServerEntity gamingServerEntity, GamingServerEventType gamingServerEventType) {
        super(source);
        this.gamingServerEntity = gamingServerEntity;
        this.gamingServerEventType = gamingServerEventType;
    }

    public enum GamingServerEventType {
        SERVER_STATUS_CHANGED,
        SERVER_CREATED,
        SERVER_DELETED
    }

}
