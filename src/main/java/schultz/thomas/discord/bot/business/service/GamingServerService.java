package schultz.thomas.discord.bot.business.service;

import net.dv8tion.jda.api.JDA;
import schultz.thomas.discord.bot.model.entity.ChannelEntity;
import schultz.thomas.discord.bot.model.entity.GamingServerEntity;

import java.util.List;

public interface GamingServerService {

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
     * @param gsEntity
     * @param jda
     */
    public void refreshGamingServerMessage(GamingServerEntity gsEntity, JDA jda);

    /**
     * return all gaming server entities
     * @return
     */
    public List<GamingServerEntity> getAllGameServerEntities();

    /**
     * return the gaming server entity with the given identifier
     * @param identifier
     * @return
     */
    public GamingServerEntity getGameServerEntityByIdentifier(String identifier);

    /**
     * Update all message states for each gaming server entity
     *
     * @param jda
     * @return
     */
    public boolean updateAll(JDA jda);

}