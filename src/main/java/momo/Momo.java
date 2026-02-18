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
    private boolean shouldExit = false;

    /**
     * Constructs the Momo application with the given file path.
     *
     * @param filePath The file path used for storing tasks.
     */
    public Momo(String filePath) {
        this.ui = new Ui();
        this.storage = new Storage(filePath);
        List<Task> loaded = storage.load();
        this.tasks = new TaskList(loaded);
    }

    /**
     * Runs the main application loop, reading and executing user commands.
     */
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

    public boolean isExit() {
        return shouldExit;
    }

    public String getWelcomeMessage() {
        ui.resetOutput();
        ui.printWelcome();
        return ui.consumeOutput();
    }

    public String getResponse(String input) {
        ui.resetOutput();
        try {
            Command command = Parser.parse(input);
            command.execute(tasks, ui, storage);
            shouldExit = command.isExit();
        } catch (MomoException e) {
            ui.printError(e.getMessage());
        }
        return ui.consumeOutput();
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
