package task_8_dws;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Class representing a cvs entry
 * @author lkoelbel 21487
 */
public class CVSEntry {
    private LocalDate date;
    private double first;
    private double high;
    private double low;
    private double last;
    private double peices;
    private double volume;
    public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");


    /**
     * Constructor for representing a cvs entry
     * @param _date Date of the entry
     * @param _first First value
     * @param _high High value
     * @param _low Low value
     * @param _last Last value
     * @param _pieces IDK - some business stuff
     * @param _volume IDK - some business stuff
     */
    public CVSEntry(LocalDate _date, double _first, double _high, double _low, double _last, double _pieces, double _volume)
    {
        this.date = _date;
        this.first = _first;
        this.high = _high; // Unused
        this.low = _low; // Unused
        this.last = _last;
        this.peices = _pieces; // Unused
        this.volume = _volume; // Unused
    }

    /**
     * Parse cvs entry from string
     * @param _input String
     * @return CVS file
     */
    public static CVSEntry parseFromString(String _input) throws ParseException {
        //String format: Datum;Erster;Hoch;Tief;Schlusskurs;Stuecke;Volumen
        String[] values = _input.strip().split(";", -1);
        if (values.length < 7) throw new ParseException("Entry must be at least 7 elements long: " + _input,0);
        String valueBuffer = null;
        try{
            valueBuffer = values[0];
            LocalDate date = LocalDate.parse(valueBuffer, formatter);
            valueBuffer = values[1].replace(",",".");
            double first = Double.parseDouble(valueBuffer);
            valueBuffer = values[4].replace(",",".");
            double last = Double.parseDouble(valueBuffer);

            return new CVSEntry(date, first, 0, 0, last, 0, 0);
        } catch (DateTimeParseException e){
            System.err.println("The date is not in the correct format");
            return null;
        }catch (NumberFormatException e){
            System.err.println("A number was not in the correct format: " + valueBuffer + " in the string " + _input);
            return null;
        }
    }

    public LocalDate getDate() {
        return date;
    }

    public double getFirst() {
        return first;
    }

    public double getHigh() {
        return high;
    }

    public double getLow() {
        return low;
    }

    public double getLast() {
        return last;
    }

    public double getPeices() {
        return peices;
    }

    public double getVolume() {
        return volume;
    }
}
