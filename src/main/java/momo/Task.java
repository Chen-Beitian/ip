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
        assert description != null : "Task description should not be null";
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
     * Returns whether this task's description contains the given keyword.
     *
     * @param keyword Keyword to search for.
     * @return True if the description contains the keyword, false otherwise.
     */
    public boolean containsKeyword(String keyword) {
        assert keyword != null : "Keyword should not be null";
        return description.contains(keyword);
    }

    @Override
    public String toString() {
        return getStatusIcon() + " " + description;
    }
}
