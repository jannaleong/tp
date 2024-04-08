package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.logic.parser.Prefix;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * A list of persons that enforces uniqueness between its elements and does not allow nulls.
 * A person is considered unique by comparing using {@code Person#isSamePerson(Person)}. As such, adding and updating of
 * persons uses Person#isSamePerson(Person) for equality so as to ensure that the person being added or updated is
 * unique in terms of identity in the UniquePersonList. However, the removal of a person uses Person#equals(Object) so
 * as to ensure that the person with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Person#isSamePerson(Person)
 */
public class UniquePersonList implements Iterable<Person> {

    private final ObservableList<Person> internalList = FXCollections.observableArrayList();
    private final ObservableList<Person> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent person as the given argument.
     */
    public boolean contains(Person toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSamePerson);
    }

    /**
     * Adds a person to the list.
     * The person must not already exist in the list.
     */
    public void add(Person toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicatePersonException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the list.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the list.
     */
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new PersonNotFoundException();
        }

        if (!target.isSamePerson(editedPerson) && contains(editedPerson)) {
            throw new DuplicatePersonException();
        }

        internalList.set(index, editedPerson);
    }

    /**
     * Removes the equivalent person from the list.
     * The person must exist in the list.
     */
    public void remove(Person toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new PersonNotFoundException();
        }
    }

    public void setPersons(UniquePersonList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setPersons(List<Person> persons) {
        requireAllNonNull(persons);
        if (!personsAreUnique(persons)) {
            throw new DuplicatePersonException();
        }

        internalList.setAll(persons);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Person> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    /**
     * Sorts the list based on the isPinned field.
     */
    public void sortByPinnedStatus() {
        internalList.sort(Comparator.comparing(Person::isPinned).reversed());
    }

    /**
     * Sorts the list based on the specified field.
     */
    public void sortBy(Prefix prefix) {
        String field = prefix.getPrefix();

        if (field.equalsIgnoreCase("name")) {
            internalList.sort(Comparator.comparing(p -> p.getName().toString()));
        } else if (field.equalsIgnoreCase("phone")) {
            internalList.sort(Comparator.comparing(p -> Integer.parseInt(p.getPhone().toString())));
        } else if (field.equalsIgnoreCase("email")) {
            internalList.sort(Comparator.comparing(p -> p.getEmail().toString()));
        } else if (field.equalsIgnoreCase("address")) {
            internalList.sort(Comparator.comparing(p -> p.getAddress().toString()));
        } else if (field.equalsIgnoreCase("tag")) {
            internalList.sort(Comparator.comparing(p -> p.getTags().toString()));
        } else if (field.equalsIgnoreCase("salary")) {
            internalList.sort(Comparator.comparing(p -> {
                if (p instanceof Staff) {
                    Staff staff = (Staff) p;
                    String salary = staff.getSalary().toString();
                    return "0" + salary.length() + salary;
                }
                return "1" + p.getName().toString();
            }));
        } else if (field.equalsIgnoreCase("employment")) {
            internalList.sort(Comparator.comparing(p -> {
                if (p instanceof Staff) {
                    Staff staff = (Staff) p;
                    return "0" + staff.getEmployment().toString();
                }
                return "1" + p.getName().toString();
            }));
        } else if (field.equalsIgnoreCase("product")) {
            internalList.sort(Comparator.comparing(p -> {
                if (p instanceof Supplier) {
                    Supplier supplier = (Supplier) p;
                    String product = supplier.getProduct().toString();
                    return "0" + product.length() + product;
                }
                return "1" + p.getName().toString();
            }));
        } else if (field.equalsIgnoreCase("price")) {
            internalList.sort(Comparator.comparing(p -> {
                if (p instanceof Supplier) {
                    Supplier supplier = (Supplier) p;
                    return "0" + supplier.getPrice().toString();
                }
                return "1" + p.getName().toString();
            }));
        } else if (field.equalsIgnoreCase("commission")) {
            internalList.sort(Comparator.comparing(p -> {
                if (p instanceof Maintainer) {
                    Maintainer maintainer = (Maintainer) p;
                    String commission = maintainer.getCommission().toString();
                    return "0" + commission.length() + commission;
                }
                return "1" + p.getName().toString();
            }));
        } else if (field.equalsIgnoreCase("skill")) {
            internalList.sort(Comparator.comparing(p -> {
                if (p instanceof Maintainer) {
                    Maintainer maintainer = (Maintainer) p;
                    return "0" + maintainer.getSkill().toString();
                }
                return "1" + p.getName().toString();
            }));
        } else if (field.equalsIgnoreCase("note")) {
            internalList.sort(Comparator.comparing((Person p) ->
                    p.getNote().toString()).reversed());
        } else if (field.equalsIgnoreCase("pin")) {
            internalList.sort(Comparator.comparing((Person p) ->
                    p.getPin().toString()).reversed());
        } else if (field.equalsIgnoreCase("rating")) {
            internalList.sort(Comparator.comparing((Person p) ->
                    p.getRating().toString()).reversed());
        } else {
            internalList.sort(Comparator.comparing(p -> p.getName().toString()));
        }

        internalList.sort(Comparator.comparing((Person p) ->
                p.getPin().toString()).reversed());
    }

    @Override
    public Iterator<Person> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UniquePersonList)) {
            return false;
        }

        UniquePersonList otherUniquePersonList = (UniquePersonList) other;
        return internalList.equals(otherUniquePersonList.internalList);
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    @Override
    public String toString() {
        return internalList.toString();
    }

    /**
     * Returns true if {@code persons} contains only unique persons.
     */
    private boolean personsAreUnique(List<Person> persons) {
        for (int i = 0; i < persons.size() - 1; i++) {
            for (int j = i + 1; j < persons.size(); j++) {
                if (persons.get(i).isSamePerson(persons.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
