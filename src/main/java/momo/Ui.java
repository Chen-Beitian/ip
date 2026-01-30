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

    public void printWelcome() {
        printLine();
        System.out.println("    Hello! I'm Momo");
        System.out.println("    What can I do for you?");
        printLine();
    }

    public void printError(String message) {
        printLine();
        System.out.println("    " + message);
        printLine();
    }

    public void printGoodbye() {
        printLine();
        System.out.println("    Bye. Hope to see you again soon!");
        printLine();
    }

    public void showAdded(Task task, int size) {
        printLine();
        System.out.println("    Got it. I've added this task:");
        System.out.println("       " + task);
        System.out.println("    Now you have " + size + " tasks in the list.");
        printLine();
    }

    public void showDeleted(Task task, int size) {
        printLine();
        System.out.println("    Noted. I've removed this task:");
        System.out.println("       " + task);
        System.out.println("    Now you have " + size + " tasks in the list.");
        printLine();
    }

    public void showMarked(Task task, boolean done) {
        printLine();
        System.out.println(done
                ? "    Nice! I've marked this task as done:"
                : "    OK, I've marked this task as not done yet:");
        System.out.println("       " + task);
        printLine();
    }

    public void showList(TaskList tasks) {
        printLine();
        System.out.println("    Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println("    " + (i + 1) + "." + tasks.get(i));
        }
        printLine();
    }

<<<<<<< Updated upstream
=======
    /**
     * Displays the tasks that match a keyword search.
     * Matching tasks are numbered starting from 1 in the displayed result.
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
>>>>>>> Stashed changes
    public String readCommand() {
        return scanner.nextLine();
    }
}