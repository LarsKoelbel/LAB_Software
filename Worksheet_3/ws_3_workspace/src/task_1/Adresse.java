package task_1;

import java.io.Serializable;

public class Adresse implements Serializable {
    private String strasse;
    private String ort;
    public String getStrasse() { return this.strasse; }
    public void setStrasse(String strasse) { this.strasse = strasse; }
    public String getOrt() { return ort; }
    public void setOrt(String ort) { this.ort = ort; }
    public String toString()
    {
        return new StringBuilder().append(strasse).append(", ")
                .append(ort).toString();
    }
}
