package task9_zettelkasten;

/**
 * This class is used to represent a paper (newspaper) storage medium
 * @author lkoelbel
 * @matnr 21487
 */
public class Paper extends Medium{
    private String ISSN = null;
    private int volume = 0;
    private int number = 0;

    public String getISSN() {
        return ISSN;
    }

    public Paper setISSN(String _ISSN) {
        this.ISSN = _ISSN;
        return this;
    }

    public int getVolume() {
        return volume;
    }

    public Paper setVolume(int _volume) {
        this.volume = _volume;
        return this;
    }

    public int getNumber() {
        return number;
    }

    public Paper setNumber(int _number) {
        this.number = _number;
        return this;
    }

    /**
     * Methode is used to get a string representation of the storage medium
     * @return String representation
     */
    @Override
    public String generateRepresentation()
    {
        StringBuilder sp = new StringBuilder();
        sp.append("Title: ").append(getTitle()).append("\n");
        sp.append("ISSN: ").append(getISSN()).append("\n");
        sp.append("Volume: ").append(getVolume()).append("\n");
        sp.append("Number: ").append(getNumber());

        return sp.toString();
    }
}
