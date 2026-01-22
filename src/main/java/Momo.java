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
            if (isBye(input)) {
                sayGoodbye();
                break;
            }
            if (isList(input)) {
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

    private static boolean isBye(String input) {
        return input.equals("bye");
    }

    private static boolean isList(String input) {
        return input.equals("list");
    }

    private static void addTask(String input) {
        tasks[taskCount] = new Task(input);
        taskCount++;
        printLine();
        System.out.println("    added: " + input);
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