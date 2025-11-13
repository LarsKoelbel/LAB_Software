package task_1;

import java.util.Date;

public class Person {

    private int id;
    private String name;
    private String vorname;
    private Date geburtsdatum;
    private String postleitzahl;
    private String ort;
    private String hobby;
    private String lieblingsgericht;
    private String lieblingsband;

    public Person() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public Date getGeburtsdatum() {
        return geburtsdatum;
    }

    public void setGeburtsdatum(Date geburtsdatum) {
        this.geburtsdatum = geburtsdatum;
    }

    public String getPostleitzahl() {
        return postleitzahl;
    }

    public void setPostleitzahl(String postleitzahl) {
        this.postleitzahl = postleitzahl;
    }

    public String getOrt() {
        return ort;
    }

    public void setOrt(String ort) {
        this.ort = ort;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    public String getLieblingsgericht() {
        return lieblingsgericht;
    }

    public void setLieblingsgericht(String lieblingsgericht) {
        this.lieblingsgericht = lieblingsgericht;
    }

    public String getLieblingsband() {
        return lieblingsband;
    }

    public void setLieblingsband(String lieblingsband) {
        this.lieblingsband = lieblingsband;
    }

    @Override
    public String toString() {
        return "[[" + this.id + "] ["
                + (this.name != null ? this.name : "Ungültig") + "] ["
                + (this.vorname != null ? this.vorname : "Ungültig") + "] ["
                + (this.ort != null ? this.ort : "Ungültig") + "] ["
                + (this.postleitzahl != null ? this.postleitzahl : "Ungültig") + "] ["
                + (this.geburtsdatum != null ? this.geburtsdatum : "Ungültig") + "] ["
                + (this.hobby != null ? this.hobby : "Ungültig") + "] ["
                + (this.lieblingsgericht != null ? this.lieblingsgericht : "Ungültig") + "] ["
                + (this.lieblingsband != null ? this.lieblingsband : "Ungültig") + "]]";
    }

    public String toXML()
    {
        StringBuilder sb = new StringBuilder("<person id=" + id + ">\n");

        sb.append("\t<name>").append(this.name != null ? this.name : "Ungültig").append("</name>\n");
        sb.append("\t<vorname>").append(this.vorname != null ? this.vorname : "Ungültig").append("</vorname>\n");
        sb.append("\t<ort>").append(this.ort != null ? this.ort : "Ungültig").append("</ort>\n");
        sb.append("\t<postleitzahl>").append(this.postleitzahl != null ? this.postleitzahl : "Ungültig").append("</postleitzahl>\n");
        sb.append("\t<geburtsdatum>").append(this.geburtsdatum != null ? this.geburtsdatum : "Ungültig").append("</geburtsdatum>\n");
        sb.append("\t<hobby>").append(this.hobby != null ? this.hobby : "Ungültig").append("</hobby>\n");
        sb.append("\t<lieblingsgericht>").append(this.lieblingsgericht != null ? this.lieblingsgericht : "Ungültig").append("</lieblingsgericht>\n");
        sb.append("\t<lieblingsband>").append(this.lieblingsband != null ? this.lieblingsband : "Ungültig").append("</lieblingsband>\n");

        sb.append("</person>");

        return sb.toString();

    }
}