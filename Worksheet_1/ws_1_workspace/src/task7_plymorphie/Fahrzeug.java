package task7_plymorphie;

/**
 * Abstract class as template for all vehicles
 * @author lkoelbel
 * @matnr mat-nr: 21487
 */
abstract public class Fahrzeug {

    int yearOfBuild = 0;

    // Constructors

    /**
     * Constructor for instantiating a new Fahrzeug with a year ob build
     * @param _yearOfBuild Year of build of the vehicle
     */
    public Fahrzeug (int _yearOfBuild)
    {
        yearOfBuild = _yearOfBuild;
    }

    /**
     * Default constructor
     */
    public Fahrzeug ()
    {}

    /**
     * Methode for making the vehicle drive
     */
    abstract void drive();

    // Getter and setter


    public int getYearOfBuild() {
        return yearOfBuild;
    }

    public void setYearOfBuild(int yearOfBuild) {
        this.yearOfBuild = yearOfBuild;
    }


    /**
     * Overwriting the to string methode
     * @return A string representation of all attributes of the class
     */
    @Override
    public String toString()
    {
        StringBuilder sp = new StringBuilder();
        sp.append("Vehicle Type: ").append("Fahrzeug (Allgemein)").append("\n");
        sp.append("Year of build: ").append(yearOfBuild).append("\n");

        return sp.toString();
    }
}
