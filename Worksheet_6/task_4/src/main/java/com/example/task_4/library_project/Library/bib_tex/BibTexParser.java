package com.example.task_4.library_project.Library.bib_tex;

import com.example.task_4.library_project.Library.Medium.*;
import com.example.task_4.library_project.Library.io.Communication;
import com.example.task_4.library_project.Library.io.ProcessOutputBuffer;
import com.example.task_4.library_project.Library.io.Severity;

/**
 * Class for parsing a media object from a bib tex string
 * @author lkoelbel 21487
 */
public class BibTexParser {

    /**
     * Parse a new media object from a bib tex string
     * @param _input BibTex string
     * @param _out Output buffer for the process
     * @throws BibTexException if the string cant be parsed. Implements user readable interface for all exceptions.
     * @return The object or null
     */
    public static Medium parseFromBibTexString(String _input, ProcessOutputBuffer _out) throws BibTexException
    {
        BibTexStruct bibTexStruct = new BibTexStruct().parseFromString(_input, _out);

        Medium medium = null;

        switch (bibTexStruct.getType()){
            case BibTexType.BOOK -> medium = new Book().parseFromBibTexStruct(bibTexStruct, _out);
            case BibTexType.CD -> medium = new CD().parseFromBibTexStruct(bibTexStruct, _out);
            case BibTexType.EL_MED -> medium = new ElectronicalMedium().parseFromBibTexStruct(bibTexStruct, _out);
            case BibTexType.JOURNAL -> medium = new Paper().parseFromBibTexStruct(bibTexStruct, _out);
            case BibTexType.EL_MED_WIKI -> medium = new ElectronicalMediumWikiBooks().parseFromBibTexStruct(bibTexStruct, _out);
            default -> _out.write("The type " + bibTexStruct.getType() + " in not (yet) supported", Severity.ERROR);
        }

        return medium;
    }
}
