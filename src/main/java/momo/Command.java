package momo;

/**
 * Represents an executable user command.
 */
public abstract class Command {
    /**
     * Executes this command.
     *
     * @param tasks The task list.
     * @param ui The user interface.
     * @param storage The storage component for saving tasks.
     * @throws MomoException If an error occurs during command execution.
     */
    public abstract void execute(TaskList tasks, Ui ui, Storage storage) throws MomoException;

    /**
     * Returns whether this command should cause the program to exit.
     *
     * @return true if this command exits the application, false otherwise.
     */
    public boolean isExit() {
        return false;
    }
}