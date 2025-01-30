package schultz.thomas.discord.bot.model.entity;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import schultz.thomas.discord.bot.model.enums.GamesNameEnum;

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
   private List<Message> allServersMessages= new ArrayList<>();
   private boolean running = false;


   public void addMessageLocation(Message messageLocation) {
       allServersMessages.add(messageLocation);
   }

}
