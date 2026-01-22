import java.util.Scanner;
public class Momo {
    private static final String[] tasks = new String[100];
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
        tasks[taskCount] = input;
        taskCount++;
        printLine();
        System.out.println("    added: " + input);
        printLine();
    }

    private static void showList() {
        printLine();
        for (int i = 0; i < taskCount; i++) {
            System.out.println("    " + (i + 1) + ". " + tasks[i]);
        }
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