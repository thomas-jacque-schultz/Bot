package schultz.thomas.discord.bot.business.service;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import schultz.thomas.discord.bot.model.entity.ChannelEntity;
import schultz.thomas.discord.bot.model.entity.DockerStateReport;
import schultz.thomas.discord.bot.model.entity.GamingServerEntity;

import java.util.List;

public interface ServerService {

    public boolean createGamingServer(GamingServerEntity gsEntity);

    public boolean updateGamingServer(GamingServerEntity gsEntity);

    public boolean subscribeDiscordChannel(ChannelEntity channelEntity);

    /**
     * Create a message state for each gaming server entity
     * use jda to call this function outside of listener scope
     * @param gsEntity
     * @param jda
     */
    public void createMessageStateFromGamingServer(GamingServerEntity gsEntity, JDA jda);

    /**
     * Create a message state for each channel entity
     * use jda to call this function outside of listener scope
     * @param channelEntity
     * @param jda
     */
    public void createMessageStateFromDiscordChannel(ChannelEntity channelEntity, JDA jda);

    /**
     * Update the message state for each gaming server entity
     * use jda to call this function outside of listener scope
     * @param reports
     * @param jda
     */
    public void updateMessageStateFromDockerReport(DockerStateReport reports, JDA jda);

}