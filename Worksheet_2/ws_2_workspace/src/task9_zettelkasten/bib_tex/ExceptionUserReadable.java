package task9_zettelkasten.bib_tex;

/**
 * Interface for defining methods to be used to communicate an exception to the user
 */
public interface ExceptionUserReadable {
    /**
     * Get a user readable message from the exception
     * @return User readable message for the exception
     */
    String getUserMessage();

    /**
     * Print the user readable message to the console
     * Note: This function should work with the output from StringScope() for consistency
     */
    void printUserMessage();
}
