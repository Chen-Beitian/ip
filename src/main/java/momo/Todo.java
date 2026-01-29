package momo;

/**
 * Represents a to-do task.
 */
public class Todo extends Task {
    /**
     * Constructs a to-do task.
     *
     * @param description Description of the task.
     */
    public Todo(String description) {
        super(description);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}