package schultz.thomas.discord.bot.business.service;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import org.springframework.stereotype.Component;
import schultz.thomas.discord.bot.model.entity.GamingServerEntity;

import java.awt.*;
import java.util.Objects;
import java.util.stream.Collectors;


@Component
public class MessageWriter {


    /**
        * Sends a message to the channel with the Game server name
        * Only if the server is not already in the channel
     * @param channel  the channel to send the message
     * @param gamingServerEntity  the server to send
     * @return the message ID
     */
    public String sendMessage(TextChannel channel, GamingServerEntity gamingServerEntity) {
        return channel.sendMessageEmbeds(createEmbedFromServer(gamingServerEntity)).complete().getId();
    }

    /*
        * Updates the message in the channel with the Game server name
     */
    public String updateMessage(GamingServerEntity gamingServerEntity, Message message) {
        return message.editMessageEmbeds(createEmbedFromServer(gamingServerEntity)).complete().getId();
    }

    /*
        * With all fields of the serverEntity, we can create a message a embeded message
     */
    public MessageEmbed createEmbedFromServer(GamingServerEntity gamingServerEntity) {
        // Création d'un EmbedBuilder
        EmbedBuilder embedBuilder = new EmbedBuilder();

        // Définir le titre comme le nom du serveur
        embedBuilder.setTitle(gamingServerEntity.getName() + "[" + gamingServerEntity.getIdentifier() + "]");
        embedBuilder.setColor(Color.CYAN);

        // Ajouter les champs principaux
        embedBuilder.addField("URL :", gamingServerEntity.getUrlConnection(), false);
        embedBuilder.addField("Nombre de joueurs max", String.valueOf(gamingServerEntity.getPlayersMax()), true);
        embedBuilder.addField("Version", Objects.requireNonNullElse(gamingServerEntity.getVersion(),""), true);

        if (gamingServerEntity.getInstallation() != null && !gamingServerEntity.getInstallation().isEmpty()) {
            embedBuilder.addField("Installation :", gamingServerEntity.getInstallation(), false);
        }

        // Ajouter une description si elle existe
        if (gamingServerEntity.getDescription() != null && !gamingServerEntity.getDescription().isEmpty()) {
            embedBuilder.setDescription(gamingServerEntity.getDescription());
        }

        // Ajouter les auteurs autorisés si la liste n'est pas vide
        if (gamingServerEntity.getAdmins() != null && !gamingServerEntity.getAdmins().isEmpty()) {
            String authors = gamingServerEntity.getAdmins().stream().collect(Collectors.joining(", "));
            embedBuilder.addField("Admin", authors, false);
        }

        // Ajouter un pied de page avec l'ID du serveur
        embedBuilder.setFooter("Statut : online", null);

        // Retourner l'embed
        return embedBuilder.build();
    }
}
