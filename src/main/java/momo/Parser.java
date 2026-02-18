package momo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Parses user input into executable commands.
 */
public class Parser {
    private static final String TODO_PREFIX = "todo ";
    private static final String DEADLINE_PREFIX = "deadline ";
    private static final String EVENT_PREFIX = "event ";

    private static final String MSG_TODO_EMPTY = "Please provide a task description.";
    private static final String USAGE_DEADLINE =
            "Usage: deadline <description> /by <yyyy-mm-dd or yyyy-mm-dd HH:mm>";
    private static final String USAGE_EVENT =
            "Usage: event <description> /from <yyyy-mm-dd or yyyy-mm-dd HH:mm> "
                    + "/to <yyyy-mm-dd or yyyy-mm-dd HH:mm>";
    private static final String MSG_BAD_DATE =
            "Date must be in yyyy-mm-dd or yyyy-mm-dd HH:mm format.";
    private static final String USAGE_TAG = "Usage: tag <index> #tag ...";
    private static final String USAGE_UNTAG = "Usage: untag <index> #tag ...";
    private static final String USAGE_FILTER = "Usage: filter #tag";

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
        case "tag":
            return parseTag(trimmed);
        case "untag":
            return parseUntag(trimmed);
        case "filter":
            return parseFilter(trimmed);
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
        if (!trimmed.startsWith(TODO_PREFIX)) {
            throw new MomoException(MSG_TODO_EMPTY);
        }

        String description = trimmed.substring(TODO_PREFIX.length()).trim();
        if (description.isEmpty()) {
            throw new MomoException(MSG_TODO_EMPTY);
        }

        ExtractedText extracted = extractTags(description);
        if (extracted.cleanedText.isEmpty()) {
            throw new MomoException(MSG_TODO_EMPTY);
        }

        Task task = new Todo(extracted.cleanedText);
        addTagsToTask(task, extracted.tags);
        return new AddCommand(task);
    }

    private static Command parseDeadline(String trimmed) throws MomoException {
        if (!trimmed.startsWith(DEADLINE_PREFIX)) {
            throw new MomoException(USAGE_DEADLINE);
        }

        String rest = trimmed.substring(DEADLINE_PREFIX.length()).trim();
        String[] parts = rest.split(" /by ", 2);
        if (parts.length < 2 || parts[0].trim().isEmpty() || parts[1].trim().isEmpty()) {
            throw new MomoException(USAGE_DEADLINE);
        }

        ExtractedText desc = extractTags(parts[0].trim());
        ExtractedText by = extractTags(parts[1].trim());
        if (desc.cleanedText.isEmpty() || by.cleanedText.isEmpty()) {
            throw new MomoException(USAGE_DEADLINE);
        }

        try {
            Task task = new Deadline(desc.cleanedText, Deadline.parseBy(by.cleanedText));
            addTagsToTask(task, desc.tags);
            addTagsToTask(task, by.tags);
            return new AddCommand(task);
        } catch (IllegalArgumentException e) {
            throw new MomoException(MSG_BAD_DATE);
        }
    }

    private static Command parseEvent(String trimmed) throws MomoException {
        if (!trimmed.startsWith(EVENT_PREFIX)) {
            throw new MomoException(USAGE_EVENT);
        }

        String rest = trimmed.substring(EVENT_PREFIX.length()).trim();
        String[] fromParts = rest.split(" /from ", 2);
        if (fromParts.length < 2 || fromParts[0].trim().isEmpty()) {
            throw new MomoException(USAGE_EVENT);
        }

        String[] toParts = fromParts[1].split(" /to ", 2);
        if (toParts.length < 2 || toParts[0].trim().isEmpty() || toParts[1].trim().isEmpty()) {
            throw new MomoException(USAGE_EVENT);
        }

        ExtractedText desc = extractTags(fromParts[0].trim());
        ExtractedText from = extractTags(toParts[0].trim());
        ExtractedText to = extractTags(toParts[1].trim());
        if (desc.cleanedText.isEmpty() || from.cleanedText.isEmpty() || to.cleanedText.isEmpty()) {
            throw new MomoException(USAGE_EVENT);
        }

        try {
            java.time.LocalDateTime start = Deadline.parseBy(from.cleanedText);
            java.time.LocalDateTime end = Deadline.parseBy(to.cleanedText);

            if (end.isBefore(start)) {
                throw new MomoException("End time cannot be before start time.");
            }

            Task task = new Event(desc.cleanedText, start, end);
            addTagsToTask(task, desc.tags);
            addTagsToTask(task, from.tags);
            addTagsToTask(task, to.tags);
            return new AddCommand(task);
        } catch (IllegalArgumentException e) {
            throw new MomoException(MSG_BAD_DATE);
        }
    }

    private static Command parseFind(String trimmed) throws MomoException {
        String[] parts = trimmed.split("\\s+", 2);
        if (parts.length < 2 || parts[1].trim().isEmpty()) {
            throw new MomoException("Usage: find <keyword>");
        }
        return new FindCommand(parts[1].trim());
    }

    private static Command parseTag(String trimmed) throws MomoException {
        String[] parts = trimmed.split("\\s+");
        if (parts.length < 3) {
            throw new MomoException(USAGE_TAG);
        }

        int index = parseIndex(parts[1]);
        List<String> tags = parseTagTokens(parts, 2, USAGE_TAG);
        return new TagCommand(index, tags, true);
    }

    private static Command parseUntag(String trimmed) throws MomoException {
        String[] parts = trimmed.split("\\s+");
        if (parts.length < 3) {
            throw new MomoException(USAGE_UNTAG);
        }

        int index = parseIndex(parts[1]);
        List<String> tags = parseTagTokens(parts, 2, USAGE_UNTAG);
        return new TagCommand(index, tags, false);
    }

    private static Command parseFilter(String trimmed) throws MomoException {
        String[] parts = trimmed.split("\\s+");
        if (parts.length != 2) {
            throw new MomoException(USAGE_FILTER);
        }
        String token = parts[1].trim();
        if (!isTagToken(token)) {
            throw new MomoException(USAGE_FILTER);
        }
        return new FilterCommand(token);
    }

    private static int parseIndex(String token) throws MomoException {
        int index;
        try {
            index = Integer.parseInt(token.trim()) - 1;
        } catch (NumberFormatException e) {
            throw new MomoException("Task index must be a number.");
        }
        return index;
    }

    private static List<String> parseTagTokens(String[] parts, int startIndex, String usage) throws MomoException {
        List<String> tags = new ArrayList<>();
        for (int i = startIndex; i < parts.length; i++) {
            String token = parts[i].trim();
            if (!isTagToken(token)) {
                throw new MomoException(usage);
            }
            tags.add(token);
        }
        if (tags.isEmpty()) {
            throw new MomoException(usage);
        }
        return tags;
    }

    private static boolean isTagToken(String token) {
        return token != null && token.length() > 1 && token.charAt(0) == '#';
    }

    private static void addTagsToTask(Task task, List<String> tags) {
        for (String tag : tags) {
            task.addTag(tag);
        }
    }

    private static ExtractedText extractTags(String text) {
        if (text == null || text.isBlank()) {
            return new ExtractedText("", new ArrayList<>());
        }

        String[] tokens = text.trim().split("\\s+");
        List<String> tags = new ArrayList<>();
        StringBuilder cleaned = new StringBuilder();

        for (String token : tokens) {
            if (isTagToken(token)) {
                tags.add(token);
                continue;
            }

            if (!cleaned.isEmpty()) {
                cleaned.append(" ");
            }
            cleaned.append(token);
        }

        return new ExtractedText(cleaned.toString().trim(), tags);
    }

    private static void saveQuietly(Storage storage, TaskList tasks) {
        try {
            storage.save(tasks.asListForStorage());
        } catch (IOException e) {
            System.out.println("Warning: failed to save tasks.");
        }
    }

    private static final class ExtractedText {
        private final String cleanedText;
        private final List<String> tags;

        private ExtractedText(String cleanedText, List<String> tags) {
            this.cleanedText = cleanedText;
            this.tags = tags;
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

    private static final class TagCommand extends Command {
        private final int index;
        private final List<String> tags;
        private final boolean isAdd;

        private TagCommand(int index, List<String> tags, boolean isAdd) {
            this.index = index;
            this.tags = tags;
            this.isAdd = isAdd;
        }

        @Override
        public void execute(TaskList tasks, Ui ui, Storage storage) throws MomoException {
            if (index < 0 || index >= tasks.size()) {
                throw new MomoException("Task index is out of range.");
            }

            Task task = tasks.get(index);
            if (isAdd) {
                for (String tag : tags) {
                    task.addTag(tag);
                }
            } else {
                for (String tag : tags) {
                    task.removeTag(tag);
                }
            }

            saveQuietly(storage, tasks);

            if (isAdd) {
                ui.showTagged(task, tags);
            } else {
                ui.showUntagged(task, tags);
            }
        }
    }

    private static final class FilterCommand extends Command {
        private final String tagToken;

        private FilterCommand(String tagToken) {
            this.tagToken = tagToken;
        }

        @Override
        public void execute(TaskList tasks, Ui ui, Storage storage) {
            List<Task> matched = new ArrayList<>();
            for (int i = 0; i < tasks.size(); i++) {
                Task task = tasks.get(i);
                if (task.hasTag(tagToken)) {
                    matched.add(task);
                }
            }
            ui.showFilterResults(tagToken, matched);
        }
    }
}
