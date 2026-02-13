package momo;

import java.util.List;
import java.util.Scanner;

/**
 * Handles user interactions.
 */
public class Ui {
    private final Scanner scanner;
    private final StringBuilder buffer = new StringBuilder();

    /**
     * Constructs the user interface and prepares input reading.
     */
    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    private void appendLine(String line) {
        buffer.append(line).append(System.lineSeparator());
        System.out.println(line);
    }

    private void printLine() {
        appendLine("    ____________________________________________________________");
    }

    /**
     * Prints the welcome message when the application starts.
     */
    public void printWelcome() {
        printLine();
        appendLine("    Hello! I'm Momo");
        appendLine("    What can I do for you?");
        printLine();
    }

    /**
     * Prints an error message to the user.
     *
     * @param message The error message to display.
     */
    public void printError(String message) {
        printLine();
        appendLine("    " + message);
        printLine();
    }

    /**
     * Prints the goodbye message before the application exits.
     */
    public void printGoodbye() {
        printLine();
        appendLine("    Bye. Hope to see you again soon!");
        printLine();
    }

    /**
     * Displays a message indicating a task has been added.
     *
     * @param task The task that was added.
     * @param size The total number of tasks after addition.
     */
    public void showAdded(Task task, int size) {
        printLine();
        appendLine("    Got it. I've added this task:");
        appendLine("       " + task);
        appendLine("    Now you have " + size + " tasks in the list.");
        printLine();
    }

    /**
     * Displays a message indicating a task has been deleted.
     *
     * @param task The task that was deleted.
     * @param size The total number of tasks remaining.
     */
    public void showDeleted(Task task, int size) {
        printLine();
        appendLine("    Noted. I've removed this task:");
        appendLine("       " + task);
        appendLine("    Now you have " + size + " tasks in the list.");
        printLine();
    }

    /**
     * Displays a message indicating a task has been marked or unmarked.
     *
     * @param task The task that was updated.
     * @param done The new completion status of the task.
     */
    public void showMarked(Task task, boolean done) {
        printLine();
        appendLine(done
                ? "    Nice! I've marked this task as done:"
                : "    OK, I've marked this task as not done yet:");
        appendLine("       " + task);
        printLine();
    }

    /**
     * Displays the list of tasks to the user.
     *
     * @param tasks The task list to display.
     */
    public void showList(TaskList tasks) {
        printLine();
        appendLine("    Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            appendLine("    " + (i + 1) + "." + tasks.get(i));
        }
        printLine();
    }

    /**
     * Displays the tasks that match a keyword search.
     *
     * @param matchedTasks The list of matching tasks.
     */
    public void showFindResults(List<Task> matchedTasks) {
        printLine();
        appendLine("    Here are the matching tasks in your list:");
        for (int i = 0; i < matchedTasks.size(); i++) {
            appendLine("    " + (i + 1) + "." + matchedTasks.get(i));
        }
        printLine();
    }

    /**
     * Displays a message indicating tags have been added to a task.
     *
     * @param task The task that was tagged.
     * @param tags The tags added (as entered by the user, e.g. "#fun").
     */
    public void showTagged(Task task, List<String> tags) {
        printLine();
        appendLine("    OK, I've added these tags to the task:");
        appendLine("       " + formatTagList(tags));
        appendLine("       " + task);
        printLine();
    }

    /**
     * Displays a message indicating tags have been removed from a task.
     *
     * @param task The task that was untagged.
     * @param tags The tags removed (as entered by the user, e.g. "#fun").
     */
    public void showUntagged(Task task, List<String> tags) {
        printLine();
        appendLine("    OK, I've removed these tags from the task:");
        appendLine("       " + formatTagList(tags));
        appendLine("       " + task);
        printLine();
    }

    /**
     * Displays tasks that match a tag filter.
     *
     * @param tagToken The tag token (e.g. "#fun").
     * @param matchedTasks The list of tasks that have the tag.
     */
    public void showFilterResults(String tagToken, List<Task> matchedTasks) {
        printLine();
        appendLine("    Here are the tasks tagged with " + tagToken + ":");
        for (int i = 0; i < matchedTasks.size(); i++) {
            appendLine("    " + (i + 1) + "." + matchedTasks.get(i));
        }
        printLine();
    }

    private String formatTagList(List<String> tags) {
        if (tags == null || tags.isEmpty()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < tags.size(); i++) {
            if (i > 0) {
                sb.append(" ");
            }
            sb.append(tags.get(i));
        }
        return sb.toString();
    }

    /**
     * Reads a command entered by the user.
     *
     * @return The command string entered by the user.
     */
    public String readCommand() {
        return scanner.nextLine();
    }

    /**
     * Clears all previously accumulated output from the UI buffer.
     */
    public void resetOutput() {
        buffer.setLength(0);
    }

    /**
     * Returns all accumulated output from the UI buffer and clears it.
     *
     * @return The accumulated output as a string.
     */
    public String consumeOutput() {
        String out = buffer.toString();
        buffer.setLength(0);
        return out;
    }
}
