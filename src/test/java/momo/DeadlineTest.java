package momo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

public class DeadlineTest {

    @Test
    public void parseBy_dateOnly_returnsStartOfDay() {
        LocalDateTime dt = Deadline.parseBy("2019-10-15");
        assertEquals(LocalDateTime.of(2019, 10, 15, 0, 0), dt);
    }

    @Test
    public void parseBy_dateTime_returnsExactTime() {
        LocalDateTime dt = Deadline.parseBy("2019-10-15 18:00");
        assertEquals(LocalDateTime.of(2019, 10, 15, 18, 0), dt);
    }

    @Test
    public void parseBy_invalidFormat_throws() {
        assertThrows(IllegalArgumentException.class, () -> Deadline.parseBy("15-10-2019"));
        assertThrows(IllegalArgumentException.class, () -> Deadline.parseBy("2019-10-15 1800"));
        assertThrows(IllegalArgumentException.class, () -> Deadline.parseBy("random"));
    }
}
