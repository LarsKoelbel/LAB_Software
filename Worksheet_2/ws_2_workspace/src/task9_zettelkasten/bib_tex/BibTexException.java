package task9_zettelkasten.bib_tex;

/**
 * Exception for runtime errors inside the bibtex module
 */
public class BibTexException extends RuntimeException {
  public BibTexException(String message) {
    super(message);
  }
}
