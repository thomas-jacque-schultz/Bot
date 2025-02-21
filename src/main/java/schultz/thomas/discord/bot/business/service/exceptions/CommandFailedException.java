package schultz.thomas.discord.bot.business.service.exceptions;

import lombok.experimental.StandardException;

@StandardException
public class CommandFailedException extends RuntimeException {
    public CommandFailedException(String message) {
        super(message);
    }
}
