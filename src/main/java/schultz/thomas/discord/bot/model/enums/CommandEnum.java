package schultz.thomas.discord.bot.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CommandEnum {

    SUSBSCRIBE_A_CHANNEL("/subscribe"),                      // start following a channel for righting or listening messages

    UPDATE_ALL_EXISTING_MESSAGES(""),               // update all existing messages for all gaming servers
    UPDATE_ALL_EXISTING_MESSAGES_SGAMING(""),       // update all existing messages for one sGaming server

    START_SGAMING(""),                              // start sGaming server
    STOP_SGAMING(""),                               // stop sGaming server

    CREATE_USER("createUser"),
    UPDATE_USER("updateUser");

    private final String commandName;

}
