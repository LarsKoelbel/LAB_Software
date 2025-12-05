package com.example.task_4.library_project.Library.user_interface.cli_commands;

import com.example.task_4.library_project.Library.Library;
import com.example.task_4.library_project.Library.io.ProcessOutputBuffer;
import com.example.task_4.library_project.Library.io.Severity;
import com.example.task_4.library_project.Library.user_interface.CLI;
import com.example.task_4.library_project.Library.user_interface.ICLIEndpoint;

/**
* Disconnect the database server
* @author lkoeble 21487
*/
public class DisconnectDatabaseServer implements ICLIEndpoint {

    @Override
    public void call(String[] params, ProcessOutputBuffer _out, CLI _cli) {

        if (Library.server != null)
        {
            Library.server = null;
            _out.write("Disconnected from database-server", Severity.SUCCESS);
            _out.write("It is currently not supported to merge local changed into the server database. All changes made now will be lost, unless saved locally.", Severity.WARNING);
        }
        else
        {
            _out.write("No server connected", Severity.REMARK);
        }
    }

    @Override
    public String getProcessName() {
        return "server-disconnection";
    }

}
