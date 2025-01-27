package schultz.thomas.discord.bot.model.entity;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@Document( collection = "servers" )
public  class GamingServerEntity {

   @Id
   private String id;            // auto generated uuid
   private String identifier;    // foreign key to other software
   private String name;
   private String connexionString;
   private Integer playersMax;
   private String installationDetails;
   private String version;
   private String description;
   private boolean running = false;
   private List<String> admins = new ArrayList<>(); // evol to list of userEntity
   private List<Message> allServersMessages= new ArrayList<>();


   public void addMessageLocation(Message messageLocation) {
       allServersMessages.add(messageLocation);
   }

}
