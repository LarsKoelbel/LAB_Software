package com.example.task_4.library_project.Library.user_interface.cli_commands;

import com.example.task_4.library_project.Library.Archive;
import com.example.task_4.library_project.Library.Library;
import com.example.task_4.library_project.Library.io.IExceptionUserReadable;
import com.example.task_4.library_project.Library.io.ProcessOutputBuffer;
import com.example.task_4.library_project.Library.io.Severity;
import com.example.task_4.library_project.Library.persistency.BibTexPersistency;
import com.example.task_4.library_project.Library.user_interface.CLI;
import com.example.task_4.library_project.Library.user_interface.ICLIEndpoint;

/**
* Load collection data from a bibtex representation
* @author lkoeble 21487
*/
public class LoadBibtex implements ICLIEndpoint {

    @Override
    public void call(String[] params, ProcessOutputBuffer _out, CLI _cli) {
        if (Library.server != null)
        {
            _out.write("The current session is or was connected to a server. Database integrity does not allow to load local data in unclear" +
                    " operational state. To load local data, please officially disconnect from the server using 'disconnect' command", Severity.WARNING);
            return;
        }

        // Check path
        if (params.length <= 0)
        {
            _out.write("No path provided", Severity.ERROR);
            return;
        }

        if (params[0].equalsIgnoreCase("default")) params[0] = SaveBibtex.DEFAULT_BIBTEX_FILE;

        try
        {

            // Check if the collection is empty
            if(Library.collection.isEmpty())
            {
                BibTexPersistency b = new BibTexPersistency();
                Archive c = b.load(params[0]);
                Library.collection.merge(c);

                _out.write("Done", Severity.SUCCESS);
                return;
            }else
            {
                if(_cli.ask(
                        "Your current library is not empty. Loading from a file now might lead to duplicate IDs. Are you sure you want to continue? [y/n]"
                ).strip().equalsIgnoreCase("y"))
                {
                    BibTexPersistency b = new BibTexPersistency();
                    Archive c = b.load(params[0]);
                    Library.collection.merge(c);

                    _out.write("Done", Severity.SUCCESS);
                }else
                {
                    _out.write("Loading canceled :: Your archive is not empty!", Severity.ERROR);
                }
            }

        }catch (Exception e)
        {
            if (e instanceof IExceptionUserReadable)
            {
                _out.write(((IExceptionUserReadable) e).getUserMessage(), Severity.ERROR);
            }
            else
            {
                _out.write("Fatal error while loading: " + e.getMessage(), Severity.FATAL);
            }
        }

    }

    @Override
    public String getProcessName() {
        return "cli-load-bibtex";
    }

}
