package task9_zettelkasten.bib_tex;

/**
 * Exception to raise in case of an error inside the String scope module
 */
public class StringScopeParseException extends RuntimeException implements ExceptionUserReadable{
    public StringScopeParseException(String message) {
        super(message);
    }

    @Override
    public String getUserMessage() {
        return getMessage();
    }

    @Override
    public void printUserMessage() {
        System.out.println(getMessage());
    }
}
