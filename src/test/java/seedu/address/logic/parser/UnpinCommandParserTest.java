package seedu.address.logic.parser;

import static seedu.address.logic.messages.Messages.MESSAGE_COMMAND_FORMAT;
import static seedu.address.logic.messages.Messages.MESSAGE_MISSING_FIELD_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.UnpinCommand;
import seedu.address.model.person.Name;

public class UnpinCommandParserTest {
    private UnpinCommandParser parser = new UnpinCommandParser();

    @Test
    public void parse_missingNamePrefix_failure() {
        // no field specified
        ArrayList<String> undetectedFields = new ArrayList<>();
        undetectedFields.add("name");
        String exception = String.format(MESSAGE_MISSING_FIELD_FORMAT, undetectedFields);
        String expectedMessage = exception + "\n"
                + String.format(MESSAGE_COMMAND_FORMAT, UnpinCommand.MESSAGE_USAGE);
        String userInput = UnpinCommand.COMMAND_WORD + " "
                + "Alice Pauline";
        assertParseFailure(parser, userInput, expectedMessage);
    }

    @Test
    public void parse_validArgs_returnsPinCommand() {
        String userInput = UnpinCommand.COMMAND_WORD + " " + PREFIX_NAME
                + "Alice Pauline";
        assertParseSuccess(parser, userInput,
                new UnpinCommand(new Name("Alice Pauline")));
    }
}
