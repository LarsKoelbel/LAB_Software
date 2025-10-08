package task9_zettelkasten;

import task9_zettelkasten.bib_tex.StringDeconstructor;
import task9_zettelkasten.bib_tex.StringScopeTree;

/**
 * Main class for managing the library
 * @author lkoelbel
 * @matnr 21487
 */
public class Library {
    public static void main(String[] argv)
    {
        StringScopeTree tree = new StringScopeTree("@book{author = {-}, title = {Duden 01. Die deutsche Rechtschreibung}, publisher = {Bibliogr" +
                "aphisches Institut, Mannheim}, year = 2004, isbn = {3-411-04013-0}}");
    }

}
