package schultz.thomas.discord.bot.model.entity;


import kotlin.Pair;
import kotlin.Triple;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@Document( collection = "servers" )
public  class ServerEntity {

   @Id
   private String id;
   private String identifier;
   private String name;
   private String connexionString;
   private int PlayersMax;
   private String InstallationDetails;
   private String version;
   private String description;
   private List<String> allowedAuthors = new ArrayList<>(); // evol to list of userEntity
   private List<MessageLocation> allServersMessages= new ArrayList<>();


   public void addMessageLocation(MessageLocation messageLocation) {
       allServersMessages.add(messageLocation);
   }

}
