package task6_abstract_classes;

/**
 * This class represents a motorcycle
 */
public class Motorrad extends Fahrzeug{

    // Default constructor
    /**
     * Default constructor
     */
    public Motorrad() {
    }

    /**
     * Overwriting the drive methode to represent the vehicle driving
     */
    @Override
    void drive() {
        System.out.println("Antreten");
    }
}
