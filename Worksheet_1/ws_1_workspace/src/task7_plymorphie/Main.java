package task7_plymorphie;

/**
 * Testing class for the Fahrzeug class
 */
public class Main {
    public static void main (String[] argv) {
        Fahrzeug golf= new Kraftfahrzeug ("Golf", 0.065, 1995);
        Motorrad gurke= new Motorrad(280, 1974);

        System.out.println(golf);
        System.out.println(gurke);
    }
}
