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
