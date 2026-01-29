package momo;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the stored task list.
 */
public class TaskList {
    private final ArrayList<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public TaskList(List<Task> initialTasks) {
        this.tasks = new ArrayList<>(initialTasks);
    }

    public int size() {
        return tasks.size();
    }

    public void add(Task task) {
        tasks.add(task);
    }

    public Task get(int index) {
        return tasks.get(index);
    }

    public Task delete(int index) throws MomoException {
        if (index < 0 || index >= tasks.size()) {
            throw new MomoException("Task index is out of range.");
        }
        return tasks.remove(index);
    }

    public List<Task> asListForStorage() {
        return tasks;
    }
}
