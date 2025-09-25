package task6_abstract_classes;

/**
 * Testing class for the Fahrzeug class
 */
public class Main {
    public static void main (String[] argv) {
        Kraftfahrzeug[] autoArr = new Kraftfahrzeug[2];
        //Der Focus verbraucht 6,5 Liter auf 100 km:
        autoArr[0] = new Kraftfahrzeug("Focus", 0.065);
        //Der Tesla verbraucht 0 Liter auf 100 km:
        autoArr[1] = new Kraftfahrzeug("Fiat", 0.00);

        int km = 400;
        System.out.printf("Verbrauch auf %d km:%n", km);
        for (int i = 0; i < autoArr.length; i++) {
            System.out.printf("%s: %.0f Liter %n",
                    autoArr[i].getModel(),
                    autoArr[i].usage(km));
        }

        Fahrzeug[] fahrzeugArr = new Fahrzeug[2];
        fahrzeugArr[0] = new Kraftfahrzeug ("Golf", 0.065);
        fahrzeugArr[1] = new Motorrad();
        for (int j = 0; j < 2; j++)
        {
            fahrzeugArr[j].drive();
        }
    }
}
