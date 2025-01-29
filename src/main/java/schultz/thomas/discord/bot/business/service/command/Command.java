package schultz.thomas.discord.bot.business.service.command;

import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import schultz.thomas.discord.bot.model.enums.CommandEnum;
import schultz.thomas.discord.bot.model.enums.UserPrivilegeEnum;

import java.util.HashSet;
import java.util.List;

public interface Command {


    /**
     * Renvoie la liste des roles necessaires pour exécuté la commande.
     * @return
     */
    List<UserPrivilegeEnum> roleNeeded();

    /**
     * interaction configuration in discord client
     * @return
     */
    CommandData getCommandData();

    /**
     * Renvoie l'enume de la commande.
     * @return
     */
    CommandEnum getEnum();

    /**
     * Renvoie true si les roles donnée (de l'utilisateur permette d'exécuté la commande).
     * @param role
     * @return
     */
    default boolean hasRight(UserPrivilegeEnum role){
        return  roleNeeded().contains(role);
    }

    /**
     * Execute la commande.
     */
    void execute(CommandContext context);


}
