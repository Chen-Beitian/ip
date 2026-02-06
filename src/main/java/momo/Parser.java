package momo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Parses user input into executable commands.
 */
public class Parser {

    /**
     * Parses the user input and returns the corresponding command.
     *
     * @param input The user input string.
     * @return A Command representing the user input.
     * @throws MomoException If the input is invalid or cannot be parsed.
     */
    public static Command parse(String input) throws MomoException {
        String trimmed = input == null ? "" : input.trim();
        if (trimmed.isEmpty()) {
            throw new MomoException("Please enter a command.");
        }

        String[] parts = trimmed.split("\\s+", 2);
        String command = parts[0];
        switch (command) {
        case "bye":
            return new ByeCommand();
        case "list":
            return new ListCommand();
        case "mark":
            return parseMark(trimmed, true);
        case "unmark":
            return parseMark(trimmed, false);
        case "delete":
            return parseDelete(trimmed);
        case "todo":
            return parseTodo(trimmed);
        case "deadline":
            return parseDeadline(trimmed);
        case "event":
            return parseEvent(trimmed);
        case "find":
            return parseFind(trimmed);
        default:
            throw new MomoException("Sorry, I haven't learned this instruction yet.");
        }
    }

    private static Command parseMark(String trimmed, boolean done) throws MomoException {
        String[] parts = trimmed.split("\\s+", 2);
        if (parts.length < 2 || parts[1].trim().isEmpty()) {
            throw new MomoException("Usage: " + (done ? "mark" : "unmark") + " <index>");
        }
        int index;
        try {
            index = Integer.parseInt(parts[1].trim()) - 1;
        } catch (NumberFormatException e) {
            throw new MomoException("Task index must be a number.");
        }
        return new MarkCommand(index, done);
    }

    private static Command parseDelete(String trimmed) throws MomoException {
        String[] parts = trimmed.split("\\s+", 2);
        if (parts.length < 2 || parts[1].trim().isEmpty()) {
            throw new MomoException("Usage: delete <index>");
        }
        int index;
        try {
            index = Integer.parseInt(parts[1].trim()) - 1;
        } catch (NumberFormatException e) {
            throw new MomoException("Task index must be a number.");
        }
        return new DeleteCommand(index);
    }

    private static Command parseTodo(String trimmed) throws MomoException {
        if (!trimmed.startsWith("todo ")) {
            throw new MomoException("Please provide a task description.");
        }
        String description = trimmed.substring(5).trim();
        if (description.isEmpty()) {
            throw new MomoException("Please provide a task description.");
        }
        return new AddCommand(new Todo(description));
    }

    private static Command parseDeadline(String trimmed) throws MomoException {
        if (!trimmed.startsWith("deadline ")) {
            throw new MomoException("Usage: deadline <description> /by <yyyy-mm-dd or yyyy-mm-dd HHmm>");
        }
        String rest = trimmed.substring(9).trim();
        String[] parts = rest.split(" /by ", 2);
        if (parts.length < 2 || parts[0].trim().isEmpty() || parts[1].trim().isEmpty()) {
            throw new MomoException("Usage: deadline <description> /by <yyyy-mm-dd or yyyy-mm-dd HHmm>");
        }
        try {
            return new AddCommand(new Deadline(parts[0].trim(), Deadline.parseBy(parts[1].trim())));
        } catch (IllegalArgumentException e) {
            throw new MomoException("Date must be in yyyy-mm-dd or yyyy-mm-dd HHmm format.");
        }
    }

    private static Command parseEvent(String trimmed) throws MomoException {
        if (!trimmed.startsWith("event ")) {
            throw new MomoException("Usage: event <description> /from <start> /to <end>");
        }
        String rest = trimmed.substring(6).trim();
        String[] fromParts = rest.split(" /from ", 2);
        if (fromParts.length < 2 || fromParts[0].trim().isEmpty()) {
            throw new MomoException("Usage: event <description> /from <start> /to <end>");
        }
        String description = fromParts[0].trim();
        String[] toParts = fromParts[1].split(" /to ", 2);
        if (toParts.length < 2 || toParts[0].trim().isEmpty() || toParts[1].trim().isEmpty()) {
            throw new MomoException("Usage: event <description> /from <start> /to <end>");
        }
        return new AddCommand(new Event(description, toParts[0].trim(), toParts[1].trim()));
    }

    private static Command parseFind(String trimmed) throws MomoException {
        String[] parts = trimmed.split("\\s+", 2);
        if (parts.length < 2 || parts[1].trim().isEmpty()) {
            throw new MomoException("Usage: find <keyword>");
        }
        return new FindCommand(parts[1].trim());
    }


    private static void saveQuietly(Storage storage, TaskList tasks) {
        try {
            storage.save(tasks.asListForStorage());
        } catch (IOException e) {
            System.out.println("Warning: failed to save tasks.");
        }
    }

    private static final class ByeCommand extends Command {
        @Override
        public void execute(TaskList tasks, Ui ui, Storage storage) {
            ui.printGoodbye();
        }

        @Override
        public boolean isExit() {
            return true;
        }
    }

    private static final class ListCommand extends Command {
        @Override
        public void execute(TaskList tasks, Ui ui, Storage storage) {
            ui.showList(tasks);
        }
    }

    private static final class MarkCommand extends Command {
        private final int index;
        private final boolean done;

        private MarkCommand(int index, boolean done) {
            this.index = index;
            this.done = done;
        }

        @Override
        public void execute(TaskList tasks, Ui ui, Storage storage) throws MomoException {
            if (index < 0 || index >= tasks.size()) {
                throw new MomoException("Task index is out of range.");
            }
            Task t = tasks.get(index);
            if (done) {
                t.markAsDone();
            } else {
                t.markAsNotDone();
            }
            saveQuietly(storage, tasks);
            ui.showMarked(t, done);
        }
    }

    private static final class DeleteCommand extends Command {
        private final int index;

        private DeleteCommand(int index) {
            this.index = index;
        }

        @Override
        public void execute(TaskList tasks, Ui ui, Storage storage) throws MomoException {
            Task removed = tasks.delete(index);
            saveQuietly(storage, tasks);
            ui.showDeleted(removed, tasks.size());
        }
    }

    private static final class AddCommand extends Command {
        private final Task task;

        private AddCommand(Task task) {
            this.task = task;
        }

        @Override
        public void execute(TaskList tasks, Ui ui, Storage storage) {
            tasks.add(task);
            saveQuietly(storage, tasks);
            ui.showAdded(task, tasks.size());
        }
    }

    private static final class FindCommand extends Command {
        private final String keyword;

        private FindCommand(String keyword) {
            this.keyword = keyword;
        }

        @Override
        public void execute(TaskList tasks, Ui ui, Storage storage) {
            List<Task> matched = new ArrayList<>();
            for (int i = 0; i < tasks.size(); i++) {
                Task task = tasks.get(i);
                if (task.containsKeyword(keyword)) {
                    matched.add(task);
                }
            }
            ui.showFindResults(matched);
        }
    }

}
