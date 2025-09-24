package task4_classes_objects;

/**
 * This class is used to represent a vehicle (Kraftfahrzeug)
 * @author lkoelbel
 * @matnr mat-nr: 21487
 */
public class Kraftfahrzeug {
    /**
     * Model of the vehicle z.B. Fiat
     */
    String model = "";

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

}
