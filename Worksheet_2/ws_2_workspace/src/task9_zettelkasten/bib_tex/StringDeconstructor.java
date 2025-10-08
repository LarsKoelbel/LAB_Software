package task9_zettelkasten.bib_tex;

/**
 * Deconstructeo for taking apart a string
 */
public class StringDeconstructor {

    private String content = null;

    /**
     * Create a new deconstructor
     * @param _input Input string content
     */
    public StringDeconstructor(String _input)
    {
        if (_input == null) throw new IllegalArgumentException();
        this.content = _input;
    }

    /**
     * Get the string snippet up to the next occurrence of the specified character
     * @param _c Next character
     * @param _inclusive True: Include last character in output | False: else
     * @return String snippet or null
     */
    public String getToNext(char _c, boolean _inclusive){
        StringBuilder result = new StringBuilder();
        StringBuilder buffer = new StringBuilder(this.content);
        while (!buffer.isEmpty())
        {
            char b;
            if((b = buffer.charAt(0)) != _c){
                result.append(b);
                buffer.deleteCharAt(0);
            }else{
                if (_inclusive){
                    result.append(b);
                    buffer.deleteCharAt(0);
                }
                this.content = buffer.toString();
                return result.toString();
            }
        }

        return null;
    }
}
