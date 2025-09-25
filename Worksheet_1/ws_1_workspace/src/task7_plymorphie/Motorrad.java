package task7_plymorphie;

/**
 * This class represents a motorcycle
 */
public class Motorrad extends Fahrzeug{

    private double lengthInZoll = 0;

    // Default constructor
    /**
     * Default constructor
     */
    public Motorrad() {
    }

    /**
     * Constructor for setting the year of build
     * @param _yearOfBuild Year of build of the Motorrad
     */
    public Motorrad(int _yearOfBuild) {
        super(_yearOfBuild);
    }

    /**
     * Constructor for setting the size and the year of build
     * @param _yearOfBuild Year of build of the Motorrad
     * @param lengthInZoll Length in Zoll of the Motorrad
     */
    public Motorrad(int _yearOfBuild, double lengthInZoll) {
        super(_yearOfBuild);
        this.lengthInZoll = lengthInZoll;
    }

    /**
     * Constructor for instantiating a motorcycle with a specific length
     * @param _lengthInZoll Length of the motorcycle
     */
    public Motorrad(double _lengthInZoll)
    {
        lengthInZoll = _lengthInZoll;
    }

    // Getter and setter

    public double getLengthInZoll() {
        return lengthInZoll;
    }

    public void setLengthInZoll(double lengthInZoll) {
        this.lengthInZoll = lengthInZoll;
    }

    /**
     * Overwriting the drive methode to represent the vehicle driving
     */
    @Override
    void drive() {
        System.out.println("Antreten");
    }

    /**
     * Overwriting the to string methode
     * @return A string representation of all attributes of the class
     */
    @Override
    public String toString()
    {
        StringBuilder sp = new StringBuilder();
        sp.append("Vehicle Type: ").append("Motorrad").append("\n");
        sp.append("Year of build: ").append(yearOfBuild).append("\n");
        sp.append("Length in Zoll: ").append(lengthInZoll).append("\n");

        return sp.toString();
    }
}
