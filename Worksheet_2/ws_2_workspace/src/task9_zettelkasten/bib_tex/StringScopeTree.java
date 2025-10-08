package task9_zettelkasten.bib_tex;

/**
 * Class representing an environment scope of string scopes as a tree
 */
public class StringScopeTree {

    private StringScope root = null;

    /**
     * Constructor for a new String scope tree from a string
     * @param _input Input string structure
     */
    public StringScopeTree(String _input) throws StringScopeParseException
    {
        if (_input == null) throw new IllegalArgumentException();
        StringDeconstructor stringDeconstructor = new StringDeconstructor(_input);

        // Get the first key
        String key = stringDeconstructor.getToNext('{', false);

        if (key == null) throw new StringScopeParseException("The string " + _input + " is not in the correct format and cant be parsed into a scope.");


    }

    /**
     * Get the string content from inside a scope (String inside the next set of {})
     * @param _input String input
     * @return Content inside the brackets as raw string or null if the format is incorrect
     */
    private static String getInScope(String _input)
    {
        if (!_input.startsWith("{")) return null;

        StringBuilder content = new StringBuilder();

        char c = (char) 0;
        int level = 0;

        for (int i = 1; i<_input.length(); i++)
        {
            if ((c = _input.charAt(i)) == '}')
            {
                if (level == 0) return content.toString();
                else level --;
            }
            else if (c == '{') level ++;

            content.append(c);
        }

        return null;
    }

}
