package task9_zettelkasten;

/**
 * Interface for making an exception supply a user readable error message
 */
public interface IExceptionUserReadable {
    /**
     * Get a user readable error message
     * @return User readable error message
     */
    public String getUserMessage();
}
