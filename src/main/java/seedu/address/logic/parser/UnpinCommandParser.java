package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.messages.UnpinMessages.FAILED_TO_UNPIN;
import static seedu.address.logic.messages.UnpinMessages.UNPIN;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.logging.Level;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.UnpinCommand;
import seedu.address.logic.messages.UnpinMessages;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Name;

/**
 * Parses input arguments and creates a new UnpinCommand object
 */
public class UnpinCommandParser implements Parser<UnpinCommand> {
    private final Logger logger = LogsCenter.getLogger(getClass());
    /**
     * Parses the given {@code String} of arguments in the context of the UnpinCommand
     * and returns an UnpinCommand object for execution. Parameter {@code args} cannot be null.
     * @throws ParseException If the user input does not conform to he expected format.
     */
    public UnpinCommand parse(String args) throws ParseException {
        requireNonNull(args);
        assert (args != null) : "argument to pass for unpin command is null";

        logger.log(Level.INFO, "Going to start parsing for unpin command.");

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME);

        // validates user command fields
        ParserUtil.verifyNoUnknownPrefix(args, UnpinCommand.MESSAGE_USAGE, UNPIN,
                FAILED_TO_UNPIN, PREFIX_NAME);
        ParserUtil.verifyNoMissingField(argMultimap, UnpinCommand.MESSAGE_USAGE, UNPIN,
                FAILED_TO_UNPIN, PREFIX_NAME);

        Name name = ParserUtil.mapName(argMultimap, UnpinMessages.MESSAGE_UNPIN_INVALID_NAME);

        return new UnpinCommand(name);
    }
}
