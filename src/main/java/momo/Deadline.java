package momo;

/**
 * Represents a task that has a deadline.
 */
public class Deadline extends Task {
    private final String by;

    /**
     * Constructs a deadline task.
     *
     * @param description Description of the task.
     * @param by Deadline time string.
     */
    public Deadline(String description, String by) {
        super(description);
        this.by = by;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by + ")";
    }
}