package seedu.address.model.event;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class EventMatchesPredicateTest {

    @Test
    public void equals_sameObject_returnsTrue() {
        EventMatchesPredicate predicate = new EventMatchesPredicate("Dance", null, null);
        assertTrue(predicate.equals(predicate));
    }

    @Test
    public void equals_nullObject_returnsFalse() {
        EventMatchesPredicate predicate = new EventMatchesPredicate("Dance", null, null);
        assertFalse(predicate.equals(null));
    }

    @Test
    public void equals_differentType_returnsFalse() {
        EventMatchesPredicate predicate = new EventMatchesPredicate("Dance", null, null);
        assertFalse(predicate.equals("String"));
    }

    @Test
    public void equals_differentValues_returnsFalse() {
        EventMatchesPredicate predicate1 = new EventMatchesPredicate("Dance", null, null);
        EventMatchesPredicate predicate2 = new EventMatchesPredicate("Music", null, null);
        assertFalse(predicate1.equals(predicate2));
    }

    @Test
    public void equals_differentStartTime_returnsFalse() {
        EventMatchesPredicate predicate1 = new EventMatchesPredicate("Dance",
                new EventStartTime("2025-02-15 18:00"), new EventEndTime("2025-02-15 21:00"));
        EventMatchesPredicate predicate2 = new EventMatchesPredicate("Dance",
                new EventStartTime("2025-02-15 19:00"), new EventEndTime("2025-02-15 21:00"));
        assertFalse(predicate1.equals(predicate2));
    }

    @Test
    public void equals_differentEndTIme_returnsFalse() {
        EventMatchesPredicate predicate1 = new EventMatchesPredicate("Dance",
                new EventStartTime("2025-02-15 18:00"), new EventEndTime("2025-02-15 21:00"));
        EventMatchesPredicate predicate2 = new EventMatchesPredicate("Dance",
                new EventStartTime("2025-02-15 18:00"), new EventEndTime("2025-02-15 22:00"));
        assertFalse(predicate1.equals(predicate2));
    }
}
