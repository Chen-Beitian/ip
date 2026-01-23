import java.util.Scanner;
public class Momo {
    private static final Task[] tasks = new Task[100];
    private static int taskCount = 0;

    private static void printLine() {
        System.out.println("    ____________________________________________________________");
    }

    private static void welcome() {
        printLine();
        System.out.println("    Hello! I'm Momo");
        System.out.println("    What can I do for you?");
        printLine();
    }

    private static void readAndRespond() {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String input = scanner.nextLine();
            if (input.equals("bye")) {
                sayGoodbye();
                break;
            }
            if (input.equals("list")) {
                showList();
                continue;
            }
            if (input.startsWith("mark ")) {
                markTask(input);
                continue;
            }
            if (input.startsWith("unmark ")) {
                unmarkTask(input);
                continue;
            }
            addTask(input);
        }
    }

    // Determine the type of task to add
    private static void addTask(String input) {
        if (input.startsWith("todo ")) {
            addTodo(input);
            return;
        }
        if (input.startsWith("deadline ")) {
            addDeadline(input);
            return;
        }
        if (input.startsWith("event ")) {
            addEvent(input);
            return;
        }
    }

    private static void addTodo(String input) {
        String description = input.substring(5);
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
        tasks[taskCount] = task;
        taskCount++;
        printLine();
        System.out.println("    Got it. I've added this task:");
        System.out.println("       " + task);
        System.out.println("    Now you have " + taskCount + " tasks in the list.");
        printLine();
    }

    private static void showList() {
        printLine();
        System.out.println("    Here are the tasks in your list:");
        for (int i = 0; i < taskCount; i++) {
            System.out.println("    " + (i + 1) + "." + tasks[i]);
        }
        printLine();
    }

    private static void markTask(String input) {
        int index = Integer.parseInt(input.split(" ")[1]) - 1;
        tasks[index].markAsDone();
        printLine();
        System.out.println("    Nice! I've marked this task as done:");
        System.out.println("       " + tasks[index]);
        printLine();
    }

    private static void unmarkTask(String input) {
        int index = Integer.parseInt(input.split(" ")[1]) - 1;
        tasks[index].unmark();
        printLine();
        System.out.println("    OK, I've marked this task as not done yet:");
        System.out.println("       " + tasks[index]);
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