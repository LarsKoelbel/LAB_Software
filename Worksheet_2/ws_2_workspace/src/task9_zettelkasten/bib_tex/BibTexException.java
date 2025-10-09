package task9_zettelkasten.bib_tex;

import task9_zettelkasten.IExceptionUserReadable;

/**
 * Exception for runtime errors inside the bibtex module
 */
public class BibTexException extends RuntimeException implements IExceptionUserReadable {
  private String userMessage = null;

  public BibTexException(String _message, String _userMessage) {
    super(_message);
    this.userMessage = _userMessage;
  }

  @Override
  public String getUserMessage() {
    return userMessage;
  }
}
