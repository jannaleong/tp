package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMPLOYMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SALARY;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.messages.AddMessages;
import seedu.address.model.Model;
import seedu.address.model.person.Staff;

//@@author chiageng
/**
 * Adds a staff to the address book.
 */
public class AddStaffCommand extends Command {
    public static final String COMMAND_WORD = "/add-staff";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a staff to the address book. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_EMAIL + "EMAIL "
            + PREFIX_ADDRESS + "ADDRESS "
            + PREFIX_SALARY + "SALARY "
            + PREFIX_EMPLOYMENT + "EMPLOYMENT \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe staff "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_EMAIL + "johnd@example.com "
            + PREFIX_ADDRESS + "311, Clementi Ave 2, #02-25 "
            + PREFIX_SALARY + "$50/hr "
            + PREFIX_EMPLOYMENT + "part-time";

    private final Staff staffToAdd;

    /**
     * Creates an AddStaffCommand object.
     * @param staff The {@code Staff} to add.
     */
    public AddStaffCommand(Staff staff) {
        requireNonNull(staff);
        staffToAdd = staff;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasPerson(staffToAdd)) {
            throw new CommandException(AddMessages.MESSAGE_ADD_DUPLICATE_PERSON);
        }

        model.addPerson(staffToAdd);
        return new CommandResult(String.format(AddMessages.MESSAGE_ADD_PERSON_SUCCESS,
                AddMessages.formatPerson(staffToAdd)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddStaffCommand)) {
            return false;
        }

        AddStaffCommand otherAddCommand = (AddStaffCommand) other;
        return staffToAdd.equals(otherAddCommand.staffToAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("staffToAdd", staffToAdd)
                .toString();
    }
}
