package task_8_dws;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

public class DWS {

    /**
     * Main class for the dws application
     * @author lkoelbel 21487
     */
    public static void main(String[] args) {
        getResultsOfYear(2019);
    }

    /**
     * Get the investment result for a specific year
     * @param _year Year
     */
    public static void getResultsOfYear(int _year)
    {
        // Get the start and end of data
        LocalDate startOfYear = LocalDate.parse("01.01." + _year, CVSEntry.formatter);
        LocalDate dateEndOfYear = LocalDate.parse("31.12." + _year, CVSEntry.formatter);

        try {
            BufferedReader in = new BufferedReader(new FileReader("./src/task_8_dws/DWS-TOP-DIVIDENDE.csv"));

            // die ersten sechs Zeilen überlesen
            int cnt = 0;
            while (in.readLine() != null && cnt < 7) {
                in.readLine();
                cnt++;
            }

            // Read all csv entry's
            List<CVSEntry> entries = new ArrayList<>();
            String line;
            while ((line = in.readLine()) != null)
            {
                CVSEntry cvsEntry = CVSEntry.parseFromString(line);
                if (cvsEntry == null) continue;
                entries.add(CVSEntry.parseFromString(line));
            }

            // Sort the list based on the date
            entries.sort(Comparator.comparing(CVSEntry::getDate));

            // Find the first entry
            CVSEntry first = null;
            CVSEntry last = null;
            CVSEntry current = entries.getFirst();
            if (dateEndOfYear.isBefore(entries.getFirst().getDate()))
            {
                System.err.println("The end date cant be bevor the start of the data");
                return;
            }
            int i = 1;
            while (current.getDate().isBefore(startOfYear))
            {
                current = entries.get(i);
                i++;
            }
            first = current;
            while (current.getDate().isBefore(dateEndOfYear))
            {
                current = entries.get(i);
                i++;
            }
            last = entries.get(i - 2);

            double differance = last.getLast() - first.getFirst();

            // Print the differance
            String result = new StringBuilder()
                    .append("First entry for the year on ").append(first.getDate()).append(" with value of ").append(first.getFirst()).append("\n")
                    .append("Last entry for the year on ").append(last.getDate()).append(" with a value of ").append(last.getLast()).append("\n")
                    .append("This is a differance of ").append(differance).append(" ")
                    .append(differance > 0 ? "bull!" : "bear!").toString();

            System.out.println(result);



        } catch (FileNotFoundException e) {
            System.err.println("Data file could not be found: " + e);
        } catch (IOException e) {
            System.err.println("Error loading the data: " + e);
        } catch (ParseException e) {
            System.err.println("The csv data could not be parsed due to the following error: " + e);
        } catch (IndexOutOfBoundsException e){
            System.err.println("End date out of bounds of the provided data");
        }
    }
}
