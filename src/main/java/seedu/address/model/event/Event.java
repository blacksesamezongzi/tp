package seedu.address.model.event;

import java.util.Objects;

import javafx.collections.ObservableList;
import seedu.address.model.person.ExternalParty;
import seedu.address.model.person.Staff;
import seedu.address.model.person.Student;
import seedu.address.model.person.UniqueExternalPartyList;
import seedu.address.model.person.UniqueStaffList;
import seedu.address.model.person.UniqueStudentList;


/**
 * Represents an Event in the address book.
 */
public class Event {

    // Event fields
    private final EventName eventName;
    private final EventStartTime eventStartTime;
    private final EventEndTime eventEndTime;
    private final UniqueStudentList students;
    private final UniqueStaffList staffs;
    private final UniqueExternalPartyList externalParties;

    /**
     * Constructs an {@code Event} with the given details.
     */
    public Event(EventName eventName, EventStartTime eventStartTime, EventEndTime eventEndTime) {
        this.eventName = eventName;
        this.eventStartTime = eventStartTime;
        this.eventEndTime = eventEndTime;
        this.students = new UniqueStudentList();
        this.staffs = new UniqueStaffList();
        this.externalParties = new UniqueExternalPartyList();
    }
    /**
     * Constructs an {@code Event} with the given details and list of students, staff and external parties.
     */
    public Event(EventName eventName, EventStartTime eventStartTime, EventEndTime eventEndTime,
                 UniqueStudentList students, UniqueStaffList staffs, UniqueExternalPartyList externalParties) {
        this.eventName = eventName;
        this.eventStartTime = eventStartTime;
        this.eventEndTime = eventEndTime;
        this.students = students;
        this.staffs = staffs;
        this.externalParties = externalParties;
    }

    public EventName getEventName() {
        return eventName;
    }

    public EventStartTime getEventStartTime() {
        return eventStartTime;
    }

    public EventEndTime getEventEndTime() {
        return eventEndTime;
    }

    /**
     * Returns true if both events have the same name, start_time, and end_time.
     */
    public boolean isSameEvent(Event otherEvent) {
        if (otherEvent == this) {
            return true;
        }

        return otherEvent != null
                && eventName.equals(otherEvent.eventName)
                && eventStartTime.equals(otherEvent.eventStartTime)
                && eventEndTime.equals(otherEvent.eventEndTime);
    }

    /**
     * Returns true if both events have the same name and data fields.
     * This defines a stronger notion of equality between two events.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Event)) {
            return false;
        }

        Event otherEvent = (Event) other;

        return eventName.equals(otherEvent.eventName)
                && eventStartTime.equals(otherEvent.eventStartTime)
                && eventEndTime.equals(otherEvent.eventEndTime)
                && students.equals(otherEvent.students)
                && staffs.equals(otherEvent.staffs)
                && externalParties.equals(otherEvent.externalParties);
    }

    /**
     * Adds a student to the event.
     * Ensures no duplicate students are added.
     */
    public void addStudent(Student student) {
        students.add(student);
    }


    public ObservableList<Student> getStudents() {
        return students.asUnmodifiableObservableList();
    }

    /**
     * Removes a student from the event.
     * Ensures the student exists before removal.
     */
    public void removeStudent(Student student) {
        students.remove(student);
    }

    /**
     * Adds a staff member to the event.
     * Ensures no duplicate staff members are added.
     */
    public void addStaff(Staff staff) {
        staffs.add(staff);
    }

    public ObservableList<Staff> getStaff() {
        return staffs.asUnmodifiableObservableList();
    }

    /**
     * Removes a staff member from the event.
     * Ensures the staff exists before removal.
     */
    public void removeStaff(Staff staff) {
        staffs.remove(staff);
    }

    /**
     * Adds an external party to the event.
     * Ensures no duplicate external parties are added.
     */
    public void addExternalParty(ExternalParty externalParty) {
        externalParties.add(externalParty);
    }

    /**
     * Removes an external party from the event.
     * Ensures the external party exists before removal.
     */
    public void removeExternalParty(ExternalParty externalParty) {
        externalParties.remove(externalParty);
    }

    /**
     * Returns an unmodifiable list of external parties involved in the event.
     */
    public ObservableList<ExternalParty> getExternalParties() {
        return externalParties.asUnmodifiableObservableList();
    }

    /**
     * Returns true if the {@code staff} is participating in this event's staff list.
     */
    public boolean isStaffInEvent(Staff staff) {
        return staffs.contains(staff);
    }

    /**
     * Returns true if the {@code student} is participating in this event's student list.
     */
    public boolean isStudentInEvent(Student student) {
        return students.contains(student);
    }

    /**
     * Returns true if the {@code externalParty} is participating in this event's external party list.
     */
    public boolean isExternalPartyInEvent(ExternalParty externalParty) {
        return externalParties.contains(externalParty);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(eventName, eventStartTime, eventEndTime);
    }

    @Override
    public String toString() {
        return String.format("%s (From: %s, To: %s)",
                eventName.fullEventName,
                eventStartTime.toString(),
                eventEndTime.toString());
    }

}
