package com.example.task_4.library_project.Library.user_interface;

import com.example.task_4.library_project.Library.io.ProcessOutputBuffer;

public class NullCLI extends CLI{
    @Override
    public void registerEndpoint(ICLIEndpoint _endpoint, String _path) {

    }

    @Override
    public String ask(String _question) {
        return "Null cli has disabled this function";
    }

    @Override
    public void start() {

    }

    @Override
    public void registerStartUpCall(ICLIEndpoint _call) {

    }

    @Override
    public void flushOutputBuffer(ProcessOutputBuffer _out) {

    }

    @Override
    public void start(boolean _noExit) {

    }
}
