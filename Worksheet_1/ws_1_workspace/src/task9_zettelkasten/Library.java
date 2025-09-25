package task9_zettelkasten;

/**
 * Main class for managing the library
 * @author lkoelbel
 * @matnr 21487
 */
public class Library {
    public static void main(String[] argv)
    {
        Medium[] storage = new Medium[4];

        storage[0] = new Book()
                .setYearOfPublishing(2004)
                .setPublicher("Bibliographisches Institut, Mannheim")
                .setISBN("3411040130")
                .setWriter("-")
                .setTitle("Duden 01. Die deutsche Rechtschreibung");

        storage[1] = new CD()
                .setArtist("The Beatles ")
                .setLable("Apple (Bea (EMI))")
                .setTitle("1");

        storage[2] = new Paper()
                .setISSN(" 0038-7452")
                .setNumber(6)
                .setVolume(54)
                .setTitle("Der Spiegel");

        storage[3] = new ElectronicalMedium()
                .setURL("https://www.hochschule-stralsund.de/")
                .setTitle("Hochschule Stralsund");

        for (Medium m : storage)
        {
            System.out.println(m.generateRepresentation() + "\n");
        }
    }

}
