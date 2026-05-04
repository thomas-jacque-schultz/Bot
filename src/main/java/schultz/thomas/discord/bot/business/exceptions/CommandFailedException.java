package schultz.thomas.discord.bot.business.exceptions;

import lombok.experimental.StandardException;

@StandardException
public class CommandFailedException extends RuntimeException {
    public CommandFailedException(String message) {
        super(message);
    }
}
