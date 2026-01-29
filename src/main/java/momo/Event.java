package momo;

/**
 * Represents a task that occurs within a time range.
 */
public class Event extends Task {
    private final String from;
    private final String to;

    /**
     * Constructs an event task.
     *
     * @param description Description of the task.
     * @param from Start time string.
     * @param to End time string.
     */
    public Event(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString()
                + " (from: " + from + " to: " + to + ")";
    }
}