package task_2_boxing;

/**
 * Demo class for boxing and unboxing
 * @author lkoelbel-Mat-NR: 21487
 */
public class Main {
    public static void main(String[] argv)
    {
        /*
        Werttypen sind variablen die ihren Wert direkt enthalten. Dazu zählen (in Java) alle elementaren datentypen.
        Die Werte werden direkt aus dem Stack gespeichert.
        */
        int a = 1748;
        double b = 45.645d;
        System.out.println(a);
        System.out.println(b);
        /*
        Verweistypen speichern ihren eigentlichen Wert im heap. Die Variable im Stack enthält nur die Speicheradresse.
        Alle Klassenverweise sind Verweistypen.
         */
        class Container{
            public int value = 10;
        }
        Container c = new Container();
        System.out.println(c.value);
        System.out.println(c);
        /*
        Boxing ist das "Verpacken" eines Werttypen in einen Verweistypen. Dabei wird ebenfalls der eigentliche Wert im
        Heap abgelegt und der Stack enthält nur eine Referenz in Form der Speicheradresse. Alle elementaren Datentypen haben
        dazugehörige Klassen um Boxing zo betreiben. Wenn das Boxing vom Compiler durchgeführt wird und nicht vom nutzer,
        bezeichnet mann es als autoboxing.
         */
        int n = 5;                          // Wertetyp
        Integer m = new Integer(5);   // Boxing (deprecated in newer java versions)
        Integer o = 5;                      // Verweistype (autoboxing)
    }
}
