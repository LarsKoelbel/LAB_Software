package com.example.task_4;

import com.example.task_4.library_project.Library.Medium.Medium;
import com.example.task_4.library_project.Library.io.ProcessOutputBuffer;
import com.example.task_4.library_project.Library.xml.WikiBookParser;

/**
* Handler class for getting wikibooks
* @author lkoeble 21487
*/
public class WikiBooks {
    /**
     * Get the url of a wikibook by name
     * @param _name Name of the book
     * @return The url or null if the name is invalid
     */
    public static String getURL(String _name)
    {
        if (_name == null || _name.isBlank()) return null;
        _name = _name.replace(" ", "_");
        return "https://de.wikibooks.org/wiki/" + _name;
    }

    /**
     * Get the medium of a wikibooks book
     * @param _name Name of the book
     * @return The medium or null if the book does not exist
     */
    public static Medium getMedium(String _name, ProcessOutputBuffer _out)
    {
        WikiBookParser parser = new WikiBookParser();
        return parser.getMedium(_name, _out);
    }
}
