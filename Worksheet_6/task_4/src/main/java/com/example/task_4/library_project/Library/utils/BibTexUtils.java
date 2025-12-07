package com.example.task_4.library_project.Library.utils;

/**
* Utility class for the bibtex format
* @author lkoeble 21487
*/
public class BibTexUtils {

    /**
     * Clean a string of all characters that would break the bibtex format (possible loss of data)
     * Replaces all unsupported characters with <BiTex::unsChar>
     * @param _string The string to clean
     * @return The bibtex save string
     */
    public static String makeBibtexSave(String _string)
    {
        String placholder = "<BiTex::unsChar>";
        _string = _string.strip();
        return _string.replace("\n", placholder)
                .replace("=", placholder);
    }
}
