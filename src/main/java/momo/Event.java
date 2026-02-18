package momo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a task that occurs within a time range.
 */
public class Event extends Task {
    private static final DateTimeFormatter OUTPUT_FORMAT =
            DateTimeFormatter.ofPattern("MMM dd yyyy HH:mm");
    private final LocalDateTime from;
    private final LocalDateTime to;

    /**
     * Constructs an event task.
     *
     * @param description Description of the task.
     * @param from Start time string.
     * @param to End time string.
     */
    public Event(String description, LocalDateTime from, LocalDateTime to) {
        super(description);
        assert from != null : "Start time must not be null";
        assert to != null : "End time must not be null";
        this.from = from;
        this.to = to;
    }

    /**
     * Returns the start time string.
     *
     * @return Start time string.
     */
    public LocalDateTime getFrom() {
        return from;
    }

    /**
     * Returns the end time string.
     *
     * @return End time string.
     */
    public LocalDateTime getTo() {
        return to;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString()
                + " (from: " + from.format(OUTPUT_FORMAT)
                + " to: " + to.format(OUTPUT_FORMAT) + ")";
    }
}
