package seedu.address.model.person;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * Represents a Person's Note with Deadline in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidNote(String)}
 */
public class DeadlineNote extends Note {

    private String deadline;
    public static final String MESSAGE_INVALID_DATE = "Date must be in the format yyyy-mm-dd (e.g., 2019-10-15)";

    /**
     * Constructs an {@code Note}.
     *
     * @param note A valid note.
     * @param note A valid deadline.
     */
    public DeadlineNote (String note, String deadline) {
        super(note);
        // should only accept deadline that is already
        // in correct format
        this.deadline = convertDate(deadline);
//        setDeadlineNoteToTrue();
    }

    /**
     * Returns true if date is in valid format, else
     * returns false.
     *
     * @return Boolean indicating if it is in a
     *         correct format.
     */
    public static Boolean isValidDate(String test) {
        if (!test.matches(VALIDATION_REGEX)) {
            return false;
        }
        try {
            LocalDate.parse(test);
            return true;
        } catch (DateTimeException e) {
            // don't change the date in this case
            // as it is not a valid date, return false.
        }
        return false;
    }

    /**
     * Returns date converted into a more
     * readable format.
     */
    public String convertDate(String deadline) {
        LocalDate currDate = LocalDate.parse(deadline);
        return currDate.format(DateTimeFormatter.ofPattern("MMM d yyyy"));
    }


    @Override
    public String toString() {
        return super.toString() + " by: " + this.deadline;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeadlineNote)) {
            return false;
        }

        DeadlineNote otherNote = (DeadlineNote) other;
        return super.equals(otherNote) && deadline.equals(otherNote.deadline);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), deadline);
    }
}