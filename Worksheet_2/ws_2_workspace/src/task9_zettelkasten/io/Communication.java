package task9_zettelkasten.io;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for handling communication with the user
 */
public class Communication {
    private static final List<Message> GLOBAL_OUTPUT_BUFFER = new ArrayList<>();
    private static final List<IGlobalOutputBufferListener> GLOBAL_OUTPUT_BUFFER_LISTENERS = new ArrayList<>();

    /**
     * Add a listener to the global output buffer
     * @param _globalOutputBufferListener Listener
     */
    public static void registerGlobalOutputBufferListener(IGlobalOutputBufferListener _globalOutputBufferListener)
    {
        GLOBAL_OUTPUT_BUFFER_LISTENERS.add(_globalOutputBufferListener);
    }

    /**
     * Remove a listener from the global output buffer
     * @param _globalOutputBufferListener Listener
     */
    public static void unregisterGlobalOutputBufferListener(IGlobalOutputBufferListener _globalOutputBufferListener)
    {
        GLOBAL_OUTPUT_BUFFER_LISTENERS.remove(_globalOutputBufferListener);
    }

    /**
     * Write a new message to the global output buffer
     * @param _message Message to write (Object.toString() called automatically)
     * @param _severity Severity of the message
     */
    public static void writeToGlobalOutputBuffer(Object _message, Severity _severity)
    {
        Message message = new Message(_message.toString(), _severity);
        GLOBAL_OUTPUT_BUFFER.add(message);

        for (IGlobalOutputBufferListener globalOutputBufferListener : GLOBAL_OUTPUT_BUFFER_LISTENERS)
        {
            globalOutputBufferListener.onGlobalOutputBufferUpdate(message);
        }
    }

    /**
     * Write a new message to the global output buffer with default severity
     * @param _message Message to write (Object.toString() called automatically)
     */
    public static void writeToGlobalOutputBuffer(Object _message)
    {
        writeToGlobalOutputBuffer(_message, Severity.BASIC);
    }
}
