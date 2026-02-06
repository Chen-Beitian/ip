package momo;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the stored task list.
 */
public class TaskList {
    private final ArrayList<Task> tasks;

    /**
     * Constructs a TaskList with an initial list of tasks.
     *
     * @param initialTasks The initial tasks to populate the list.
     */
    public TaskList(List<Task> initialTasks) {
        this.tasks = new ArrayList<>(initialTasks);
    }

    /**
     * Returns the number of tasks in the list.
     *
     * @return The number of tasks.
     */
    public int size() {
        return tasks.size();
    }

    /**
     * Adds a task to the task list.
     *
     * @param task The task to add.
     */
    public void add(Task task) {
        tasks.add(task);
    }

    /**
     * Returns the task at the given index.
     *
     * @param index The index of the task.
     * @return The task at the specified index.
     * @throws MomoException If the index is invalid.
     */
    public Task get(int index) {
        return tasks.get(index);
    }

    /**
     * Deletes and returns the task at the given index.
     *
     * @param index The index of the task to delete.
     * @return The deleted task.
     * @throws MomoException If the index is invalid.
     */
    public Task delete(int index) throws MomoException {
        if (index < 0 || index >= tasks.size()) {
            throw new MomoException("Task index is out of range.");
        }
        return tasks.remove(index);
    }

    /**
     * Returns the list of tasks for storage purposes.
     *
     * @return The list of tasks.
     */
    public List<Task> asListForStorage() {
        return tasks;
    }
}
