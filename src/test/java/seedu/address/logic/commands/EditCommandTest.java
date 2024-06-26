package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static seedu.address.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_ALICE_PAULINE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BENSON;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_GEORGE_BEST;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalPersons.getTypicalVersionedAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.messages.EditMessages;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.VersionedAddressBook;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.PersonBuilder;

//@@author yleeyilin
/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code EditCommand}.
 */
public class EditCommandTest {
    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() throws CommandException {
        try {
            Person editedPerson = new PersonBuilder().build();
            EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(editedPerson).build();
            EditCommand editCommand = new EditCommand(ALICE.getName(), descriptor);

            // expected model result
            Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
            expectedModel.setPerson(model.getFilteredPersonList().get(0), editedPerson);

            // execute method for model
            editCommand.execute(model);
            assertEquals(model.getFilteredPersonList(), expectedModel.getFilteredPersonList());
        } catch (CommandException e) {
            fail();
        }
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        try {
            Person lastPerson = model.findPersonByName(new Name(VALID_NAME_GEORGE_BEST),
                    EditMessages.MESSAGE_INVALID_EDIT_PERSON);
            PersonBuilder personInList = new PersonBuilder(lastPerson);
            Person editedPerson = personInList.withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                    .withTags(VALID_TAG).build();
            EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                    .withPhone(VALID_PHONE_BOB).withTags(VALID_TAG).build();
            EditCommand editCommand = new EditCommand(lastPerson.getName(), descriptor);

            // execute method for model
            Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
            expectedModel.setPerson(lastPerson, editedPerson);

            // execute method for model
            editCommand.execute(model);
            assertEquals(model.getFilteredPersonList(), expectedModel.getFilteredPersonList());
        } catch (CommandException e) {
            fail();
        }
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        try {
            EditCommand editCommand = new EditCommand(ALICE.getName(), new EditPersonDescriptor());
            editCommand.execute(model);
            AddressBook addressBookCopy = new VersionedAddressBook(model.getAddressBook());
            assertEquals(getTypicalVersionedAddressBook(), addressBookCopy);
        } catch (CommandException e) {
            fail();
        }
    }

    @Test
    public void execute_filteredList_success() {
        try {
            Person personInFilteredList = model.findPersonByName(new Name(VALID_NAME_ALICE_PAULINE),
                    EditMessages.MESSAGE_INVALID_EDIT_PERSON);
            Person editedPerson = new PersonBuilder(personInFilteredList).withName(VALID_NAME_BOB).build();
            EditCommand editCommand = new EditCommand(ALICE.getName(),
                    new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build());

            // expected model result
            Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
            expectedModel.setPerson(model.getFilteredPersonList().get(0), editedPerson);

            // execute method for model
            editCommand.execute(model);
            assertEquals(model.getFilteredPersonList(), expectedModel.getFilteredPersonList());
        } catch (CommandException e) {
            fail();
        }
    }

    @Test
    public void execute_invalidPersonNameUnfilteredList_failure() {
        Name invalidName = new Name(INVALID_NAME);
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_BENSON).build();
        EditCommand editCommand = new EditCommand(invalidName, descriptor);
        assertCommandFailure(editCommand, model, EditMessages.MESSAGE_INVALID_EDIT_PERSON);
    }

    @Test
    public void equals() {
        final EditCommand standardCommand = new EditCommand(ALICE.getName(), DESC_AMY);

        // same values -> returns true
        EditPersonDescriptor copyDescriptor = new EditPersonDescriptor(DESC_AMY);
        EditCommand commandWithSameValues = new EditCommand(ALICE.getName(), copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different name -> returns false
        assertFalse(standardCommand.equals(new EditCommand(BENSON.getName(), DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCommand(ALICE.getName(), DESC_BOB)));
    }

    @Test
    public void toStringMethod() {
        Name name = ALICE.getName();
        EditPersonDescriptor editPersonDescriptor = new EditPersonDescriptor();
        EditCommand editCommand = new EditCommand(ALICE.getName(), editPersonDescriptor);
        String expected = EditCommand.class.getCanonicalName() + "{name=" + name + ", editPersonDescriptor="
                + editPersonDescriptor + "}";
        assertEquals(expected, editCommand.toString());
    }

}
