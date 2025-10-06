package task_1_enum;

import java.util.Locale;

/**
 * Types of tickets that are available form the machine
 * @author lkoelbel-Mat-NR: 21487
 */
public enum TicketType implements IPrintable{
    SINGLE(1,"Single ride ticket", "Ticket for a single ride"),
    DAYPASS(2.5d,"All day ticket", "Ticket for unlimited rides within the next 24h (from time of purchase)"),
    WEEKLY(10,"All week ticket (7 days)", "Ticket for unlimited rides within the next 7 days (from time of purchase)");

    private double priceFactor = 0;
    private String lable = null;
    private String description = null;

    /**
     * Creating a new ticket Type
     * @param _priceFactor Price of the ticket
     * @param _lable Label/ Name of the ticket
     * @param _description Description of the ticket for the user
     */
    TicketType(double _priceFactor, String _lable, String _description)
    {
        this.lable = _lable;
        this.priceFactor = _priceFactor;
        this.description = _description;
    }

    public double getPriceFactor(){
        return priceFactor;
    }

    /**
     * Print all details to the console
     */
    @Override
    public void printDetails()
    {
        System.out.println(this.toString() + "| price factor: " + priceFactor);
    }

    /**
     * To string methode
     * @return String representation of the ticket type
     */
     @Override
    public String toString()
    {
        return String.format(Locale.ENGLISH, "%s | %s", lable, description);
    }
}
