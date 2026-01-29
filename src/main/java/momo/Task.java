package momo;

/**
 * Represents a generic task with a description and completion status.
 */
public class Task {
    protected final String description;
    private boolean isDone;

    /**
     * Constructs a task with the given description.
     *
     * @param description Description of the task.
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Returns whether this task is completed.
     *
     * @return True if completed, false otherwise.
     */
    public boolean isDone() {
        return isDone;
    }

    /**
     * Marks this task as done.
     */
    public void markAsDone() {
        this.isDone = true;
    }

    /**
     * Marks this task as not done.
     */
    public void markAsNotDone() {
        this.isDone = false;
    }

    /**
     * Returns the status icon of this task (done or not done).
     *
     * @return Status icon string.
     */
    protected String getStatusIcon() {
        return isDone ? "[X]" : "[ ]";
    }

    /**
     * Returns a string representation of this task for storage.
     *
     * @return String used to save this task to disk.
     */
    public String toStorageString() {
        return description;
    }

    /**
     * Creates a task from a stored string.
     *
     * @param line A line read from the storage file.
     * @return Task created from the given string.
     */
    public static Task fromStorageString(String line) {
        return new Todo(line);
    }

    @Override
    public String toString() {
        return getStatusIcon() + " " + description;
    }
}