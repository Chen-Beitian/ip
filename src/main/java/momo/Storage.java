package momo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles saving and loading tasks from disk.
 */
public class Storage {
    private final Path filePath;

    /**
     * Creates a storage that reads/writes tasks to the given file path.
     *
     * @param filePath Path to the save file.
     */
    public Storage(String filePath) {
        this.filePath = Paths.get(filePath);
    }

    /**
     * Loads tasks from file.
     * If file does not exist, returns empty list.
     */
    public List<Task> load() {
        if (!Files.exists(filePath)) {
            return new ArrayList<>();
        }
        List<Task> tasks = new ArrayList<>();
        try {
            for (String line : Files.readAllLines(filePath)) {
                Task task = parseLine(line);
                if (task != null) {
                    tasks.add(task);
                }
            }
        } catch (IOException e) {
            return new ArrayList<>();
        }
        return tasks;
    }

    /**
     * Saves tasks to file, creating folder if needed.
     *
     * @param tasks Tasks to save.
     * @throws IOException If writing fails.
     */
    public void save(List<Task> tasks) throws IOException {
        Files.createDirectories(filePath.getParent());
        List<String> lines = new ArrayList<>();
        for (Task t : tasks) {
            lines.add(toLine(t));
        }
        Files.write(filePath, lines);
    }

    private static String toLine(Task t) {
        String done = t.isDone() ? "1" : "0";
        if (t instanceof Deadline) {
            Deadline d = (Deadline) t;
            return "D | " + done + " | " + t.description + " | " + d.getBy().toString();
        }
        if (t instanceof Event) {
            Event e = (Event) t;
            return "E | " + done + " | " + t.description + " | " + e.getFrom() + " | " + e.getTo();
        }
        return "T | " + done + " | " + t.description;
    }

    private static Task parseLine(String line) {
        if (line == null) {
            return null;
        }
        String trimmed = line.trim();
        if (trimmed.isEmpty()) {
            return null;
        }
        String[] parts = trimmed.split("\\s*\\|\\s*");
        if (parts.length >= 2 && (parts[0].equals("0") || parts[0].equals("1"))) {
            Task t = new Todo(parts[1].trim());
            if (parts[0].equals("1")) {
                t.markAsDone();
            }
            return t;
        }
        if (parts.length < 3) {
            return null;
        }
        String type = parts[0].trim();
        boolean done = parts[1].trim().equals("1");
        String desc = parts[2].trim();
        Task t;
        switch (type) {
        case "T":
            t = new Todo(desc);
            break;
        case "D":
            if (parts.length < 4) {
                return null;
            }
            t = new Deadline(desc, LocalDateTime.parse(parts[3].trim()));
            break;
        case "E":
            if (parts.length < 5) {
                return null;
            }
            t = new Event(desc, parts[3].trim(), parts[4].trim());
            break;
        default:
            return null;
        }
        if (done) {
            t.markAsDone();
        }
        return t;
    }
}
