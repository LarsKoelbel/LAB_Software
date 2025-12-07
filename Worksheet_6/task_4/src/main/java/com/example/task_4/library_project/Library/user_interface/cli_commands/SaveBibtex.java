package com.example.task_4.library_project.Library.user_interface.cli_commands;

import com.example.task_4.library_project.Library.Library;
import com.example.task_4.library_project.Library.io.IExceptionUserReadable;
import com.example.task_4.library_project.Library.io.ProcessOutputBuffer;
import com.example.task_4.library_project.Library.io.Severity;
import com.example.task_4.library_project.Library.persistency.BibTexPersistency;
import com.example.task_4.library_project.Library.user_interface.CLI;
import com.example.task_4.library_project.Library.user_interface.ICLIEndpoint;

/**
* Save a collection as bibtex representation
* @author lkoeble 21487
*/
public class SaveBibtex implements ICLIEndpoint {

    public static final String DEFAULT_BIBTEX_FILE = "src/main/java/com/example/task_4/library_project/data/objects.lib.bibtex";

    @Override
    public void call(String[] params, ProcessOutputBuffer _out, CLI _cli) {
        // Check path
        if (params.length <= 0)
        {
            _out.write("No path provided", Severity.ERROR);
            return;
        }

        if (params[0].equalsIgnoreCase("default")) params[0] = DEFAULT_BIBTEX_FILE;

        BibTexPersistency bibTexPersistency = new BibTexPersistency();

        try
        {
            bibTexPersistency.save(Library.collection, params[0]);
        }catch (Exception e)
        {
            if (e instanceof IExceptionUserReadable)
            {
                _out.write(((IExceptionUserReadable) e).getUserMessage(), Severity.ERROR);
            }
            else
            {
                _out.write("Fatal error while saving: " + e.getMessage(), Severity.FATAL);
            }
        }

        _out.write("Saved under: " + params[0], Severity.SUCCESS);
    }

    @Override
    public String getProcessName() {
        return "cli-save-bib-tex";
    }
}
