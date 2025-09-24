package task2_garbage_collector;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SensorDataProcessor {
    private static final int NUM_SENSORS = 50000;
    private static final int DATA_POINTS_PER_RUN = 1000;

    public void processSensorData() {
        for (int i = 0; i < DATA_POINTS_PER_RUN; i++) {
            List<String> dataChunk = new ArrayList<>();
            for (int j = 0; j < NUM_SENSORS; j++) {
                dataChunk.add(new UUID(0L, j).toString());
            }
            // dataChunk wird in der nächsten Zeile verarbeitet
            analyzeAndStore(dataChunk);
        }
        // ... weiterer Code ...
    }

    /*
    Aufgabe 1: Beschreiben Sie den Lebenszyklus der Objekte, die in der Methode processSensorData() erstellt werden.

    Es wird eine neue Liste dataChunk erstellt. Dies gescheit für jeden Datenpunkt (Zeiteinheit). Das Listenobjekt existent immer
    genau für eine Zeiteinheit (einen durchlauf der for-Schleife). Beim beginn der neuen Zeiteinheit, wird eine neue Liste erstellt.
    Alle Ergebnisse def for-Schleife sind im weiteren code der Funtion nicht mehr verfügbar.
     */

    /*
    Aufgabe 2: Welche Objekte werden bei jedem Durchlauf der inneren for-Schleife erstellt? Wann werden diese Objekte für den Garbage Collector (GC) freigegeben?
    Beim Durchlaufen der inneren for-Schleife wird ein neues UUID object und ein neues String object erzeugt. Beim Erzeugen des String objects wird das UUID object freigegeben.
    Das string object wird erst am Ende (bein Rücksprung) der äußeren for-Schleife freigegeben, da eine referenz darauf in der dataChunk liste gespeichert wird.
     */

    /*
    Aufgabe 3: Wann werden die List<String>-Objekte (dataChunk) für den GC freigegeben?
    Die Liste wird bei jedem Rücksprung bzw. am ende der äußeren for-Schleife freigegeben.
     */

    /*
    Aufgabe 4: Erklären Sie, was System.gc() bewirkt. Ist dies in einer produktiven Anwendung empfehlenswert? Begründen Sie Ihre Antwort.

    - System.gc() teilt der Java-VM mit, den Garbage collector zu starten. Wichtig: Die VM kann den Aufruf ignorieren --> gc läuft nicht zwingend sofort.
    - In performance orientierten Systemen nicht sinnvoll aus folgenden Gründen:
        - gc Aufruf führt zu stop-the-world, d.h. alle threads des laufenden programmes werden angehalten um das Verändern von Referencen während des collection cycles zu verhindern
          STW verlangsamt das system und kann bei unnötigen collection cycles zu extremer Verringerung der Performance führen.
        - Java VM optimiert selbst: Die Java VM entschiedet selbst, wann dr gc idealerweise aufgerufen werden sollte.
        - Der Aufruf ist nur eine "Empfehlung". Es kann sich nicht auf das tatsächliche Laufen des gc verlassen werden.
     */

    private void analyzeAndStore(List<String> data) {
    // Diese Methode simuliert die Verarbeitung und Speicherung der Daten
    // In dieser Methode werden keine Referenzen auf die übergebenen Daten
    // gespeichert, die über den Methodenaufruf hinausgehen.
        System.out.println("Verarbeite Daten-Chunk mit " + data.size() + " Elementen.");
    }

    public static void main(String[] args) {
        SensorDataProcessor processor = new SensorDataProcessor();
        processor.processSensorData();

        // Was passiert mit den Objekten, die innerhalb von processSensorData erstellt wurden? 
        System.gc(); // Freiwilliger Aufruf des Garbage Collectors 
    }
} 