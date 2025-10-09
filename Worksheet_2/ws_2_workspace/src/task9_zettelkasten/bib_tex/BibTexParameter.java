package task9_zettelkasten.bib_tex;

/**
 * Class representing a bibtex parameter inside the bibtex struct
 */
public class BibTexParameter {
    private String name = null;
    private Double fvalue = null;
    private String svalue = null;
    private BibTexParameterType type = null;

    public String getName() {
        return name;
    }

    public BibTexParameter setName(String _name) {
        this.name = _name;
        return this;
    }

    public Double getFvalue() {
        return fvalue;
    }

    public BibTexParameter setFvalue(Double _fvalue) {
        this.fvalue = _fvalue;
        return this;
    }

    public String getSvalue() {
        return svalue;
    }

    public BibTexParameter setSvalue(String _svalue) {
        this.svalue = _svalue;
        return this;
    }

    public BibTexParameterType getType() {
        return type;
    }

    public BibTexParameter setType(BibTexParameterType _type) {
        this.type = _type;
        return this;
    }
}
