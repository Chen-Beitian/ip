import java.util.Scanner;
import java.util.ArrayList;
public class Momo {
    private static final ArrayList<Task> tasks = new ArrayList<>();

    private static void printLine() {
        System.out.println("    ____________________________________________________________");
    }

    private static void welcome() {
        printLine();
        System.out.println("    Hello! I'm Momo");
        System.out.println("    What can I do for you?");
        printLine();
    }

    private static void showError(String message) {
        printLine();
        System.out.println("    " + message);
        printLine();
    }

    private static void readAndRespond() {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String input = scanner.nextLine();
            try {
                handleInput(input);
            } catch (MomoException e) {
                showError(e.getMessage());
            }
        }
    }

    private static void handleInput(String input) throws MomoException {
        if (input.equals("bye")) {
            sayGoodbye();
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

    // Determine the type of task to add
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
        String description = "";
        if (input.startsWith("todo ")) {
            description = input.substring(5).trim();
        } else if (input.equals("todo")) {
            description = "";
        }
        if (description.isEmpty()) {
            throw new MomoException("Please provide a task description.");
        }
        addTaskAndConfirm(new Todo(description));
    }

    private static void addDeadline(String input) {
        String rest = input.substring(9);
        String[] parts = rest.split(" /by ", 2);
        addTaskAndConfirm(new Deadline(parts[0], parts[1]));
    }

    private static void addEvent(String input) {
        String rest = input.substring(6); // 去掉 "event "
        String[] fromParts = rest.split(" /from ", 2);
        String description = fromParts[0];
        String[] toParts = fromParts[1].split(" /to ", 2);
        addTaskAndConfirm(new Event(description, toParts[0], toParts[1]));
    }

    // add task to list and print confirmation message
    private static void addTaskAndConfirm(Task task) {
        tasks.add(task);
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

    private static void markTask(String input) {
        int index = Integer.parseInt(input.split(" ")[1]) - 1;
        tasks.get(index).markAsDone();
        printLine();
        System.out.println("    Nice! I've marked this task as done:");
        System.out.println("       " + tasks.get(index));
        printLine();
    }

    private static void unmarkTask(String input) {
        int index = Integer.parseInt(input.split(" ")[1]) - 1;
        tasks.get(index).unmark();
        printLine();
        System.out.println("    OK, I've marked this task as not done yet:");
        System.out.println("       " + tasks.get(index));
        printLine();
    }

    private static void deleteTask(String input) throws MomoException {
        int index = Integer.parseInt(input.substring(7).trim()) - 1;
        Task removed = tasks.remove(index);
        printLine();
        System.out.println("    Noted. I've removed this task:");
        System.out.println("       " + removed);
        System.out.println("    Now you have " + tasks.size() + " tasks in the list.");
        printLine();
    }

    private static void sayGoodbye() {
        printLine();
        System.out.println("    Bye. Hope to see you again soon!");
        printLine();
    }

    public static void main(String[] args) {
        welcome();
        readAndRespond();
    }
}