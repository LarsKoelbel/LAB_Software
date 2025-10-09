package task9_zettelkasten;

import task9_zettelkasten.bib_tex.BibTexParameter;
import task9_zettelkasten.bib_tex.BibTexParameterType;
import task9_zettelkasten.bib_tex.BibTexStruct;
import task9_zettelkasten.bib_tex.BibTexType;
import task9_zettelkasten.io.Communication;
import task9_zettelkasten.io.Severity;

import java.lang.reflect.Field;
import java.lang.runtime.SwitchBootstraps;

/**
 * This class is used as a template for all storage mediums
 * @author lkoelbel
 * @matnr 21487
 */
abstract public class Medium {
    private String title = null;

    public String getTitle() {
        return title;
    }

    public Medium setTitle(String _title) {
        this.title = _title;
        return this;
    }

    /**
     * Methode is used to get a string representation of the storage medium
     * @return String representation
     */
    abstract public String generateRepresentation();

    /**
     * Parse all data from a bibtex struct
     * @param _bibTexStruct
     */
    public Medium parseFromBibTexStruct(BibTexStruct _bibTexStruct)
    {
        if (_bibTexStruct == null) throw new IllegalArgumentException("BibTex struct cant be null");

        for (BibTexParameter bibTexParameter : _bibTexStruct.getParameterList())
        {
            String name = bibTexParameter.getName();
            BibTexParameterType type = bibTexParameter.getType();

            for (Field parameter : this.getClass().getDeclaredFields())
            {
                if (parameter.getName().toLowerCase().equals(name))
                {
                    BibTexParameterType parameterType = null;
                    if (parameter.getType() == Double.class) parameterType = BibTexParameterType.NUMERIC_VALUE;
                    else if (parameter.getType() == String.class) parameterType = BibTexParameterType.STRING;
                    else{
                        Communication.writeToGlobalOutputBuffer("The parameter " + name + " is defined in the class " + this.getClass().getSimpleName()
                                + " as attribute of type " + parameter.getType() + " wich is not supported by the bibtex format. Use type String or double", Severity.WARNING);
                        continue;
                    }

                    try {
                        if (parameterType == BibTexParameterType.NUMERIC_VALUE) parameter.set(this, bibTexParameter.getFvalue());
                        else if (parameterType == BibTexParameterType.STRING) parameter.set(this, bibTexParameter.getSvalue());
                    }catch (IllegalAccessException e) {
                        Communication.writeToGlobalOutputBuffer("The parameter " + name + "of type " + type + " is not compatible with the implementation" +
                                "inside the " + this.getClass().getSimpleName() + " class", Severity.WARNING);
                    }

                }else {
                    Communication.writeToGlobalOutputBuffer("The parameter " + name + " of type " + type + " is not defined in the medium " + this.getClass().getSimpleName(),
                            Severity.WARNING);
                }
            }
        }

        return this;
    }

}
