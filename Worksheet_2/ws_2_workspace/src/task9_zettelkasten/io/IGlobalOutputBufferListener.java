package task9_zettelkasten.io;

/**
 * Methods for all classes listening to the global output buffer
 */
public interface IGlobalOutputBufferListener {
    /**
     * Is called when the global output buffer updates
     * @param _lastMessage Last message in the buffer
     */
    public void onGlobalOutputBufferUpdate(Message _lastMessage);
}
