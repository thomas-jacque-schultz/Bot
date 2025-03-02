package schultz.thomas.discord.bot.model.entity;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import schultz.thomas.discord.bot.model.enums.GamesNameEnum;
import schultz.thomas.discord.bot.model.transitory.DockerContainerState;

import java.util.ArrayList;
import java.util.List;

@Data
@Document( collection = "servers" )
public  class GamingServerEntity {

   @Id
   private String id;            // auto generated uuid
   private String identifier;    // foreign key to other software
   private String name;
   private String urlConnection;
   private GamesNameEnum gameName;
   private Integer playersMax;
   private String installation;
   private String version;
   private String description;
   private List<String> admins = new ArrayList<>(); // evol to list of userEntity

   @Transient
    private DockerContainerState status;

}
