package task7_plymorphie;

/**
 * This class is used to represent a vehicle (Kraftfahrzeug)
 * @author lkoelbel
 * @matnr mat-nr: 21487
 */
public class Kraftfahrzeug extends Fahrzeug{
    /**
     * Model of the vehicle z.B. Fiat
     */
    String model = null;

    /**
     * Fuel usage in l/ km
     */

    double fuelUsage = 0;

    //Constructors

    /**
     * Constructor to set the model and fuel usage
     * @param _modell Model of the vehicle
     * @param _usage Fuel usage in l/ km
     */
    public Kraftfahrzeug(String _modell, double _usage)
    {
        if (_modell == null) throw new IllegalArgumentException("Model can not be null");
        model = _modell;
        fuelUsage = _usage;
    }

    /**
     * Constructor for setting the year of build, model and fuel usage
     * @param model Model of the Vehicle
     * @param fuelUsage Fuel usage in l/ km
*      @param _yearOfBuild Year of build of the vehicle
     */
    public Kraftfahrzeug(String model, double fuelUsage, int _yearOfBuild) {
        super(_yearOfBuild);
        this.model = model;
        this.fuelUsage = fuelUsage;
    }

    // Getter and setter

    public String getModel() {
        return model;
    }

    /**
     * Calculate the fuel used for a certain distance, in l
     * @param _distance Distance traveled by the vehicle
     * @return Fuel used in l
     */
    public double usage(double _distance)
    {
        return fuelUsage * _distance;
    }


    @Override
    public void drive()
    {
        System.out.println("Gas geben");
    }

    /**
     * Overwriting the to string methode
     * @return A string representation of all attributes of the class
     */
    @Override
    public String toString()
    {
        StringBuilder sp = new StringBuilder();
        sp.append("Vehicle Type: ").append("Kraftfahrzeug").append("\n");
        sp.append("Year of build: ").append(yearOfBuild).append("\n");
        sp.append("Model ").append(model).append("\n");
        sp.append("Fuel usage in l/ km: ").append(fuelUsage).append("\n");

        return sp.toString();
    }

}
