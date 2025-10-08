package task9_zettelkasten.bib_tex;

import java.util.List;

/**
 * Class representing a string scope with a key and a value
 */
public class StringScope {
    private String key = null;
    private List<StringScope> innerStringScopes = null;
    private String content = null;

    public String getKey() {
        return key;
    }

    public StringScope setKey(String _key) {
        this.key = _key;
        return this;
    }

    public List<StringScope> getInnerStringScopes() {
        return innerStringScopes;
    }

    public StringScope setInnerStringScopes(List<StringScope> _innerStringScopes) {
        this.innerStringScopes = _innerStringScopes;
        return this;
    }

    public String getContent() {
        return content;
    }

    public StringScope setContent(String _content) {
        this.content = _content;
        return this;
    }
}
