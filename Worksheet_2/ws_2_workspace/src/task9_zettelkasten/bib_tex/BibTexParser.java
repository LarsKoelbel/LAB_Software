package task9_zettelkasten.bib_tex;

import task9_zettelkasten.Book;
import task9_zettelkasten.Medium;

/**
 * Class for parsing a media object from a bib tex string
 */
public class BibTexParser {

    /**
     * Parse a new media object from a bib tex string
     * @param _input BibTex string
     * @throws BibTexException if the string cant be parsed. Implements user readable interface for all exceptions.
     * @return The object
     */
    public static Medium parseFromBibTexString(String _input) throws BibTexException
    {
        BibTexStruct bibTexStruct = new BibTexStruct().parseFromString(_input);

        Medium medium = null;

        switch (bibTexStruct.getType()){
            case BibTexType.BOOK:
                medium = new Book().parseFromBibTexStruct(bibTexStruct);
        }
    }
}
