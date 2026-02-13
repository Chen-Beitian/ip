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
    private static final String TYPE_TODO = "T";
    private static final String TYPE_DEADLINE = "D";
    private static final String TYPE_EVENT = "E";
    private static final String DONE = "1";
    private static final String NOT_DONE = "0";
    private static final String FIELD_SEP = " | ";
    private static final String FIELD_SPLIT_REGEX = "\\s*\\|\\s*";
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
            // If loading fails unexpectedly, start with an empty list to keep the app usable.
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

    private static String toLine(Task task) {
        String done = task.isDone() ? DONE : NOT_DONE;
        if (task instanceof Deadline) {
            Deadline deadline = (Deadline) task;
            return TYPE_DEADLINE + FIELD_SEP + done + FIELD_SEP + task.description
                    + FIELD_SEP + deadline.getBy().toString();
        }
        if (task instanceof Event) {
            Event event = (Event) task;
            return TYPE_EVENT + FIELD_SEP + done + FIELD_SEP + task.description + FIELD_SEP + event.getFrom()
                    + FIELD_SEP + event.getTo();
        }
        return TYPE_TODO + FIELD_SEP + done + FIELD_SEP + task.description;
    }

    private static Task parseLine(String line) {
        if (line == null) {
            return null;
        }
        String trimmed = line.trim();
        if (trimmed.isEmpty()) {
            return null;
        }
        String[] parts = trimmed.split(FIELD_SPLIT_REGEX);
        if (parts.length >= 2 && (parts[0].equals(NOT_DONE) || parts[0].equals(DONE))) {
            Task t = new Todo(parts[1].trim());
            if (parts[0].equals(DONE)) {
                t.markAsDone();
            }
            return t;
        }
        if (parts.length < 3) {
            return null;
        }
        String type = parts[0].trim();
        boolean done = parts[1].trim().equals(DONE);
        String desc = parts[2].trim();
        Task t;
        switch (type) {
        case TYPE_TODO:
            t = new Todo(desc);
            break;
        case TYPE_DEADLINE:
            if (parts.length < 4) {
                return null;
            }
            t = new Deadline(desc, LocalDateTime.parse(parts[3].trim()));
            break;
        case TYPE_EVENT:
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
