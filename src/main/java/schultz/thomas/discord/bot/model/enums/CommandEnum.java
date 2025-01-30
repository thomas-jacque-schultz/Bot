package schultz.thomas.discord.bot.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CommandEnum {

    SUSBSCRIBE_A_CHANNEL("subscribe"),                      // start following a channel for righting or listening messages

    UPDATE_ALL_EXISTING_MESSAGES("refresh"),               // update all existing messages for all gaming servers
    UPDATE_ALL_EXISTING_MESSAGES_SGAMING("refresh-server"),       // update all existing messages for one sGaming server

    START_SGAMING("start"),                              // start sGaming server
    STOP_SGAMING("pause"),                               // stop sGaming server

    CREATE_USER("create-user"),
    UPDATE_USER("update-user"),

    CREATE_GAMING_SERVER("create-gaming-server"),
    UPDATE_GAMING_SERVER("update-gaming-server");

    private final String commandName;

}
