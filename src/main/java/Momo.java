import java.util.Scanner;
public class Momo {

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
                printLine();
                System.out.println("    Bye. Hope to see you again soon!");
                printLine();
                break;
            }
            echo(input);
        }
    }

    private static void echo(String input) {
        printLine();
        System.out.println("    " + input);
        printLine();
    }

    public static void main(String[] args) {
        welcome();
        readAndRespond();
    }
}