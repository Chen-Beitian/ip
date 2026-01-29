package momo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles saving and loading tasks from disk.
 */
public class Storage {
    private static final Path FILE_PATH = Paths.get("data", "momo.txt");

    /**
     * Loads tasks from file.
     * If file does not exist, returns empty list.
     */
    public List<Task> load() {
        if (!Files.exists(FILE_PATH)) {
            return new ArrayList<>();
        }
        List<Task> tasks = new ArrayList<>();
        try {
            for (String line : Files.readAllLines(FILE_PATH)) {
                String trimmed = line.trim();
                if (trimmed.isEmpty()) {
                    continue;
                }
                String[] parts = trimmed.split("\\s*\\|\\s*", 2);
                if (parts.length < 2) {
                    tasks.add(new Todo(trimmed));
                    continue;
                }
                boolean done = parts[0].trim().equals("1");
                String desc = parts[1].trim();
                Task t = new Todo(desc);
                if (done) {
                    t.markAsDone();
                }
                tasks.add(t);
            }
        } catch (IOException e) {
            return new ArrayList<>();
        }
        return tasks;
    }

    /**
     * Saves tasks to file, creating folder if needed.
     */
    public void save(List<Task> tasks) throws IOException {
        Files.createDirectories(FILE_PATH.getParent());
        List<String> lines = new ArrayList<>();
        for (Task t : tasks) {
            String line = (t.isDone() ? "1" : "0") + " | " + t.description;
            lines.add(line);
        }
        Files.write(FILE_PATH, lines);
    }
}