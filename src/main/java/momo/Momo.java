package momo;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.IOException;

/**
 * Main entry point of the Momo task management application.
 * Handles user input and dispatches commands.
 */
public class Momo {
    private static final ArrayList<Task> tasks = new ArrayList<>();
    private static final Storage storage = new Storage();

    private static void printLine() {
        System.out.println("    ____________________________________________________________");
    }

    private static void printWelcome() {
        printLine();
        System.out.println("    Hello! I'm Momo");
        System.out.println("    What can I do for you?");
        printLine();
    }

    private static void printError(String message) {
        printLine();
        System.out.println("    " + message);
        printLine();
    }

    private static void printGoodbye() {
        printLine();
        System.out.println("    Bye. Hope to see you again soon!");
        printLine();
    }

    private static void readAndRespond() {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String input = scanner.nextLine();
            try {
                handleInput(input);
            } catch (MomoException e) {
                printError(e.getMessage());
            }
        }
    }

    private static void handleInput(String input) throws MomoException {
        if (input.equals("bye")) {
            printGoodbye();
            System.exit(0);
        }
        if (input.equals("list")) {
            showList();
            return;
        }
        if (input.startsWith("mark")) {
            markTask(input);
            return;
        }
        if (input.startsWith("unmark")) {
            unmarkTask(input);
            return;
        }
        if (input.startsWith("delete")) {
            deleteTask(input);
            return;
        }
        addTask(input);
    }

    private static void addTask(String input) throws MomoException {
        if (input.startsWith("todo")) {
            addTodo(input);
            return;
        }
        if (input.startsWith("deadline")) {
            addDeadline(input);
            return;
        }
        if (input.startsWith("event")) {
            addEvent(input);
            return;
        }
        throw new MomoException("Sorry, I haven't learned this instruction yet.");
    }

    private static void addTodo(String input) throws MomoException {
        if (!input.startsWith("todo ")) {
            throw new MomoException("Please provide a task description.");
        }
        String description = input.substring(5).trim();
        addTaskAndConfirm(new Todo(description));
    }

    private static void addDeadline(String input) throws MomoException {
        if (!input.startsWith("deadline ")) {
            throw new MomoException("Usage: deadline <description> /by <time>");
        }
        String rest = input.substring(9).trim();
        String[] parts = rest.split(" /by ", 2);
        if (parts.length < 2 || parts[0].trim().isEmpty() || parts[1].trim().isEmpty()) {
            throw new MomoException("Usage: deadline <description> /by <time>");
        }
        addTaskAndConfirm(new Deadline(parts[0].trim(), parts[1].trim()));
    }

    private static void addEvent(String input) throws MomoException {
        if (!input.startsWith("event ")) {
            throw new MomoException("Usage: event <description> /from <start> /to <end>");
        }
        String rest = input.substring(6).trim();
        String[] fromParts = rest.split(" /from ", 2);
        if (fromParts.length < 2 || fromParts[0].trim().isEmpty()) {
            throw new MomoException("Usage: event <description> /from <start> /to <end>");
        }
        String description = fromParts[0].trim();
        String[] toParts = fromParts[1].split(" /to ", 2);
        if (toParts.length < 2 || toParts[0].trim().isEmpty() || toParts[1].trim().isEmpty()) {
            throw new MomoException("Usage: event <description> /from <start> /to <end>");
        }
        addTaskAndConfirm(new Event(description, toParts[0].trim(), toParts[1].trim()));
    }

    private static void addTaskAndConfirm(Task task) {
        tasks.add(task);
        saveTasks();
        printLine();
        System.out.println("    Got it. I've added this task:");
        System.out.println("       " + task);
        System.out.println("    Now you have " + tasks.size() + " tasks in the list.");
        printLine();
    }

    private static void showList() {
        printLine();
        System.out.println("    Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println("    " + (i + 1) + "." + tasks.get(i));
        }
        printLine();
    }

    private static void markTask(String input) throws MomoException {
        int index = Integer.parseInt(input.split(" ")[1]) - 1;
        tasks.get(index).markAsDone();
        saveTasks();
        printLine();
        System.out.println("    Nice! I've marked this task as done:");
        System.out.println("       " + tasks.get(index));
        printLine();
    }

    private static void unmarkTask(String input) throws MomoException {
        int index = Integer.parseInt(input.split(" ")[1]) - 1;
        tasks.get(index).markAsNotDone();
        saveTasks();
        printLine();
        System.out.println("    OK, I've marked this task as not done yet:");
        System.out.println("       " + tasks.get(index));
        printLine();
    }

    private static void deleteTask(String input) throws MomoException {
        int index = Integer.parseInt(input.substring(7).trim()) - 1;
        Task removed = tasks.remove(index);
        saveTasks();
        printLine();
        System.out.println("    Noted. I've removed this task:");
        System.out.println("       " + removed);
        System.out.println("    Now you have " + tasks.size() + " tasks in the list.");
        printLine();
    }

    private static void saveTasks() {
        try {
            storage.save(tasks);
        } catch (IOException e) {
            System.out.println("Warning: failed to save tasks.");
        }
    }

    /**
     * Starts the Momo application.
     *
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        tasks.addAll(storage.load());
        printWelcome();
        readAndRespond();
    }
}