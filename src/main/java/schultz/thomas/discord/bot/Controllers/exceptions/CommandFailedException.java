package schultz.thomas.discord.bot.Controllers.exceptions;

import lombok.experimental.StandardException;

@StandardException
public class CommandFailedException extends RuntimeException {
    public CommandFailedException(String message) {
        super(message);
    }
}
