package schultz.thomas.discord.bot.model.entity;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import schultz.thomas.discord.bot.model.enums.UserPrivilegeEnum;

@Data
@Document( collection = "users" )
public class UserEntity {

    @Id
    private String id;
    private String discordId;
    private String discordUsername;
    private UserPrivilegeEnum privilege;

}
