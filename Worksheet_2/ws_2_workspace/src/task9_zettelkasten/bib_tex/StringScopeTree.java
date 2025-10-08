package task9_zettelkasten.bib_tex;

import java.security.cert.TrustAnchor;
import java.util.ArrayList;
import java.util.List;

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

        String innerScopeString = getInScope(stringDeconstructor.toString());

        if (innerScopeString == null) throw new StringScopeParseException("The string " + stringDeconstructor.toString() + " is not a valid scope. Make sure all {} are placed correctly");

        this.root = new StringScope().setKey(key).setInnerStringScopes(generateSubscopes(innerScopeString));
    }

    /**
     * Function for creating subscopes from a string
     * @param _input Input string
     * @return List of sub scopes or null if the string doesn't have any subscopes
     */
    private List<StringScope> generateSubscopes(String _input)
    {
        List<StringScope> list = new ArrayList<>();

        String[] elements = splitInScope(_input, ',');
        for (String e : elements)
        {
            System.out.println(e);
            StringDeconstructor stringDeconstructor = new StringDeconstructor(e);
            String key = stringDeconstructor.getToNext('{', false);
            String content = getInScope(stringDeconstructor.toString());

            if (content != null && content.contains("{") && content.contains("}"))
            {
                list.add(new StringScope().setKey(key).setInnerStringScopes(generateSubscopes(content)));
            }else
            {
                list.add(new StringScope().setKey(key).setContent(content));
            }
        }

        if(list.isEmpty()) return null;
        return list;
    }

    /**
     * Split a string by a certain character, but only if the character is in the same scope
     * @param _input Input string
     * @param _c Character to split by
     * @return Array of split strings
     */
    private String[] splitInScope(String _input, char _c)
    {
        List<String> list = new ArrayList<>();

        StringBuilder builder = new StringBuilder();
        char c = (char) 0;
        int level = 0;
        for (int i = 0; i < _input.length(); i++)
        {
            if((c = _input.charAt(i)) == '{') level++;
            if(c == '}') level--;

            if (c == _c && level == 0)
            {
                if (!builder.isEmpty())
                {
                    list.add(builder.toString());
                    builder = new StringBuilder();
                }
            }else
            {
                builder.append(c);
            }
        }

        if(!builder.isEmpty()) list.add(builder.toString());

        return list.toArray(new String[0]);
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
