package momo;

import java.util.List;
import java.util.Scanner;

/**
 * Handles user interactions.
 */
public class Ui {
    private final Scanner scanner;

    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    private void printLine() {
        System.out.println("    ____________________________________________________________");
    }

    /**
     * Prints the welcome message when the application starts.
     */
    public void printWelcome() {
        printLine();
        System.out.println("    Hello! I'm Momo");
        System.out.println("    What can I do for you?");
        printLine();
    }

    /**
     * Prints an error message to the user.
     *
     * @param message The error message to display.
     */
    public void printError(String message) {
        printLine();
        System.out.println("    " + message);
        printLine();
    }

    /**
     * Prints the goodbye message before the application exits.
     */
    public void printGoodbye() {
        printLine();
        System.out.println("    Bye. Hope to see you again soon!");
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
        System.out.println("    Got it. I've added this task:");
        System.out.println("       " + task);
        System.out.println("    Now you have " + size + " tasks in the list.");
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
        System.out.println("    Noted. I've removed this task:");
        System.out.println("       " + task);
        System.out.println("    Now you have " + size + " tasks in the list.");
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
        System.out.println(done
                ? "    Nice! I've marked this task as done:"
                : "    OK, I've marked this task as not done yet:");
        System.out.println("       " + task);
        printLine();
    }

    /**
     * Displays the list of tasks to the user.
     *
     * @param tasks The task list to display.
     */
    public void showList(TaskList tasks) {
        printLine();
        System.out.println("    Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println("    " + (i + 1) + "." + tasks.get(i));
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
        System.out.println("    Here are the matching tasks in your list:");
        for (int i = 0; i < matchedTasks.size(); i++) {
            System.out.println("    " + (i + 1) + "." + matchedTasks.get(i));
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
}
