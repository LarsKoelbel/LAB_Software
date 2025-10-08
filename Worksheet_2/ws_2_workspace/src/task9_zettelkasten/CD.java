package task9_zettelkasten;

/**
 * This class is used to represent a CD storage medium
 * @author lkoelbel
 * @matnr 21487
 */
public class CD extends Medium{
    private String lable = null;
    private String artist = null;

    public String getLable() {
        return lable;
    }

    public CD setLable(String _lable) {
        this.lable = _lable;
        return this;
    }

    public String getArtist() {
        return artist;
    }

    public CD setArtist(String _artist) {
        this.artist = _artist;
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
        sp.append("Lable: ").append(getLable()).append("\n");
        sp.append("Artist: ").append(getArtist());

        return sp.toString();
    }
}
