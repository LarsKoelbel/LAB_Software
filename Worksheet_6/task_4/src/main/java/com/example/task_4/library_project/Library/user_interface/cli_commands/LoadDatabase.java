package com.example.task_4.library_project.Library.user_interface.cli_commands;

import com.example.task_4.library_project.Library.Archive;
import com.example.task_4.library_project.Library.Library;
import com.example.task_4.library_project.Library.database.Server;
import com.example.task_4.library_project.Library.io.Communication;
import com.example.task_4.library_project.Library.io.IExceptionUserReadable;
import com.example.task_4.library_project.Library.io.ProcessOutputBuffer;
import com.example.task_4.library_project.Library.io.Severity;
import com.example.task_4.library_project.Library.user_interface.CLI;
import com.example.task_4.library_project.Library.user_interface.ICLIEndpoint;

/**
* Load collection data from the database
* @author lkoeble 21487
*/
public class LoadDatabase implements ICLIEndpoint {

    @Override
    public void call(String[] params, ProcessOutputBuffer _out, CLI _cli) {

        Server server = Library.server;
        Archive collection = Library.collection;

        if (server == null || !server.testAuth(Communication.NULL_BUFFER))
        {
            _out.write("Server not connected or server connection lost. Please use 'connect database-server' to (re)connect", Severity.ERROR);
            return;
        }

        try
        {
            // Check if the collection is empty
            if(collection.isEmpty())
            {
                Archive c = server.getCollectionFromDatabase(_out);
                if(c != null)
                {
                    if (c.isEmpty())
                    {
                        _out.write("No data on server", Severity.REMARK);
                    }else
                    {
                        collection.merge(c);
                        _out.write("Data download complete", Severity.SUCCESS);
                    }
                }
            }else
            {
                _out.write("Your current library is not empty. Loading from server in this state is not allowed out of risk to the database integrity. Merging local changes into the database is not jet supportet.\n" +
                        "The recommended procedure is to save your changed locally and clear using 'clear'", Severity.WARNING);
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
        return "cli-load-server";
    }

}
