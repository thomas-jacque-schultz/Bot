package schultz.thomas.discord.bot.business.service.tools;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import org.springframework.stereotype.Component;
import schultz.thomas.discord.bot.model.entity.ServerEntity;

import java.awt.*;
import java.util.stream.Collectors;


@Component
public class MessageWriter {


    /*
        * Sends a message to the channel with the Game server name
        * Only if the server is not already in the channel
     */
    public String sendMessage(TextChannel channel, ServerEntity serverEntity) {
        return channel.sendMessageEmbeds(createEmbedFromServer(serverEntity)).complete().getId();
    }

    /*
        * Updates the message in the channel with the Game server name
     */
    public String updateMessage(ServerEntity serverEntity, Message message) {
        return message.editMessageEmbeds(createEmbedFromServer(serverEntity)).complete().getId();
    }

    /*
        * With all fields of the serverEntity, we can create a message a embeded message
     */
    public MessageEmbed createEmbedFromServer(ServerEntity serverEntity) {
        // Création d'un EmbedBuilder
        EmbedBuilder embedBuilder = new EmbedBuilder();

        // Définir le titre comme le nom du serveur
        embedBuilder.setTitle(serverEntity.getName());
        embedBuilder.setColor(Color.CYAN);

        // Ajouter les champs principaux
        embedBuilder.addField("Connexion String", serverEntity.getConnexionString(), false);
        embedBuilder.addField("Players Max", String.valueOf(serverEntity.getPlayersMax()), true);
        embedBuilder.addField("Version", serverEntity.getVersion(), true);

        if (serverEntity.getInstallationDetails() != null && !serverEntity.getInstallationDetails().isEmpty()) {
            embedBuilder.addField("Installation Details", serverEntity.getInstallationDetails(), false);
        }

        // Ajouter une description si elle existe
        if (serverEntity.getDescription() != null && !serverEntity.getDescription().isEmpty()) {
            embedBuilder.setDescription(serverEntity.getDescription());
        }

        // Ajouter les auteurs autorisés si la liste n'est pas vide
        if (serverEntity.getAllowedAuthors() != null && !serverEntity.getAllowedAuthors().isEmpty()) {
            String authors = serverEntity.getAllowedAuthors().stream().collect(Collectors.joining(", "));
            embedBuilder.addField("Allowed Authors", authors, false);
        }

        // Ajouter un pied de page avec l'ID du serveur
        embedBuilder.setFooter("Server ID: " + serverEntity.getId(), null);

        // Retourner l'embed
        return embedBuilder.build();
    }
}
