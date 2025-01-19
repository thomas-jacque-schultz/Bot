package schultz.thomas.discord.bot.business.service;

import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public interface ServerService {

    public void refreshServers(GenericEvent genericEvent);

    public void syncExistingChannels(GenericEvent genericEvent);

    public void answerHello(MessageReceivedEvent event);

    public void addNewGamingServer(MessageReceivedEvent event);
}