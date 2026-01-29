package momo;

import java.util.List;

/**
 * Main entry point of the Momo task management application.
 * Handles user input and dispatches commands.
 */
public class Momo {
    private final Storage storage;
    private final TaskList tasks;
    private final Ui ui;

    public Momo(String filePath) {
        this.ui = new Ui();
        this.storage = new Storage(filePath);
        List<Task> loaded = storage.load();
        this.tasks = new TaskList(loaded);
    }

    public void run() {
        ui.printWelcome();
        boolean isExit = false;
        while (!isExit) {
            try {
                String fullCommand = ui.readCommand();
                Command command = Parser.parse(fullCommand);
                command.execute(tasks, ui, storage);
                isExit = command.isExit();
            } catch (MomoException e) {
                ui.printError(e.getMessage());
            }
        }
    }

    /**
     * Starts the Momo application.
     *
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        new Momo("data/momo.txt").run();
    }
}