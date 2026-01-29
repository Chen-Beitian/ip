package momo;

/**
 * Represents an exception specific to the Momo application.
 */
public class MomoException extends Exception {
    /**
     * Constructs a MomoException with the given message.
     *
     * @param message Error message.
     */
    public MomoException(String message) {
        super(message);
    }
}
