package seedu.address.logic.commands.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_DUPLICATE_EXTERNAL_PARTY_IN_EVENT;
import static seedu.address.logic.Messages.MESSAGE_DUPLICATE_STAFF_IN_EVENT;
import static seedu.address.logic.Messages.MESSAGE_DUPLICATE_STUDENT_IN_EVENT;
import static seedu.address.logic.Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX;
import static seedu.address.logic.Messages.MESSAGE_INVALID_EXTERNAL_PARTY_DISPLAYED_INDEX;
import static seedu.address.logic.Messages.MESSAGE_INVALID_INDEX_OUT_OF_RANGE;
import static seedu.address.logic.Messages.MESSAGE_INVALID_STAFF_DISPLAYED_INDEX;
import static seedu.address.logic.Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_EXTERNAL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_STAFF;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_STUDENT;

import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.Event;
import seedu.address.model.person.ExternalParty;
import seedu.address.model.person.Staff;
import seedu.address.model.person.Student;
import seedu.address.model.person.exceptions.DuplicatePersonException;

/**
 * Adds a member (Student, Staff, or External Party) to an event.
 */
public class AddEventMemberCommand extends Command {

    public static final String COMMAND_WORD = "add_event_member";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a student to an event.\n"
            + "Parameters: <Event Index> " + PREFIX_EVENT_STUDENT + "<Student Index> OR "
            + PREFIX_EVENT_STAFF + "<Staff Index> OR " + PREFIX_EVENT_EXTERNAL + "<External Index>\n"
            + "Example: " + COMMAND_WORD + " 1 " + PREFIX_EVENT_STUDENT + "3";

    public static final String MESSAGE_STUDENT_ADDED_TO_EVENT = "Added student %s to event: %s";
    public static final String MESSAGE_STAFF_ADDED_TO_EVENT = "Added staff %s to event: %s";
    public static final String MESSAGE_EXTERNAL_PARTY_ADDED_TO_EVENT = "Added external party %s to event: %s";
    public static final String MESSAGE_INVALID = "You must specify exactly one member type: stu/, staff/, or ext/.";


    private final Index eventIndex;
    private final Optional<Index> studentIndex;
    private final Optional<Index> staffIndex;
    private final Optional<Index> externalIndex;

    /**
     * Creates an {@code AddEventMemberCommand}.
     *
     * @param eventIndex   The index of the event (required).
     * @param studentIndex The optional index of the student.
     * @param staffIndex   The optional index of the staff member.
     * @param externalIndex The optional index of the external member.
     */
    public AddEventMemberCommand(Index eventIndex, Optional<Index> studentIndex, Optional<Index> staffIndex,
                                 Optional<Index> externalIndex) {
        requireNonNull(eventIndex);
        this.eventIndex = eventIndex;
        this.studentIndex = studentIndex;
        this.staffIndex = staffIndex;
        this.externalIndex = externalIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);


        // Ensure only one type of member is provided
        long count = studentIndex.isPresent() ? 1 : 0;
        count += staffIndex.isPresent() ? 1 : 0;
        count += externalIndex.isPresent() ? 1 : 0;
        if (count != 1) {
            throw new CommandException(MESSAGE_INVALID + "\n" + MESSAGE_USAGE);
        }

        try {
            List<Event> lastShownEventList = model.getFilteredEventList();

            if (eventIndex.getZeroBased() < 0 || eventIndex.getZeroBased() >= lastShownEventList.size()) {
                throw new CommandException(MESSAGE_INVALID_EVENT_DISPLAYED_INDEX + "\n" + MESSAGE_USAGE);
            }
            Event event = lastShownEventList.get(eventIndex.getZeroBased());


            // Add Student
            if (studentIndex.isPresent()) {
                int studentZeroBased = studentIndex.get().getZeroBased();
                if (studentZeroBased < 0 || studentZeroBased >= model.getFilteredStudentList().size()) {
                    throw new CommandException(MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX + "\n" + MESSAGE_USAGE);
                }
                Student student = model.getFilteredStudentList().get(studentZeroBased);
                try {
                    event.addStudent(student);
                } catch (DuplicatePersonException e) {
                    throw new CommandException(MESSAGE_DUPLICATE_STUDENT_IN_EVENT + "\n" + MESSAGE_USAGE);
                }
                return new CommandResult(String.format(MESSAGE_STUDENT_ADDED_TO_EVENT, student.getName().fullName,
                        event.getEventName()));
            }

            // Add Staff
            if (staffIndex.isPresent()) {
                int staffZeroBased = staffIndex.get().getZeroBased();
                if (staffZeroBased < 0 || staffZeroBased >= model.getFilteredStaffList().size()) {
                    throw new CommandException(MESSAGE_INVALID_STAFF_DISPLAYED_INDEX + "\n" + MESSAGE_USAGE);
                }
                Staff staff = model.getFilteredStaffList().get(staffZeroBased);
                try {
                    event.addStaff(staff);
                } catch (DuplicatePersonException e) {
                    throw new CommandException(MESSAGE_DUPLICATE_STAFF_IN_EVENT + "\n" + MESSAGE_USAGE);
                }

                return new CommandResult(String.format(MESSAGE_STAFF_ADDED_TO_EVENT, staff.getName().fullName,
                        event.getEventName()));
            }

            // Add External Member
            if (externalIndex.isPresent()) {
                int externalZeroBased = externalIndex.get().getZeroBased();
                if (externalZeroBased < 0 || externalZeroBased >= model.getFilteredExternalPartyList().size()) {
                    throw new CommandException(MESSAGE_INVALID_EXTERNAL_PARTY_DISPLAYED_INDEX + "\n" + MESSAGE_USAGE);
                }
                ExternalParty external = model.getFilteredExternalPartyList().get(externalZeroBased);
                try {
                    event.addExternalParty(external);
                } catch (DuplicatePersonException e) {
                    throw new CommandException(MESSAGE_DUPLICATE_EXTERNAL_PARTY_IN_EVENT + "\n" + MESSAGE_USAGE);
                }
                return new CommandResult(String.format(MESSAGE_EXTERNAL_PARTY_ADDED_TO_EVENT, external.getName(),
                        event.getEventName()));
            }

            throw new CommandException(MESSAGE_INVALID + "\n" + MESSAGE_USAGE);

        } catch (IndexOutOfBoundsException e) {
            throw new CommandException(MESSAGE_INVALID_INDEX_OUT_OF_RANGE + "\n" + MESSAGE_USAGE);
        }

    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof AddEventMemberCommand)) {
            return false;
        }

        AddEventMemberCommand otherCommand = (AddEventMemberCommand) other;
        return eventIndex.equals(otherCommand.eventIndex)
                && studentIndex.equals(otherCommand.studentIndex)
                && staffIndex.equals(otherCommand.staffIndex)
                && externalIndex.equals(otherCommand.externalIndex);
    }
}
