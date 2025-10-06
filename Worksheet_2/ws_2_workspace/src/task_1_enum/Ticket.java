package task_1_enum;

/**
 * Class representing a ticket
 * @author lkoelbel-Mat-NR: 21487
 */
public class Ticket {
    private TicketType type = null;
    private Zone zone = null;
    private double price = 0;

    /**
     * Create new ticket
     * @param _ticketType Type of ticket
     * @param _zone Zone of the ticket
     * @param _price Price of the ticket
     */
    public Ticket(TicketType _ticketType, Zone _zone, double _price)
    {
        type = _ticketType;
        zone = _zone;
        price = _price;
    }

    public TicketType getType() {
        return type;
    }

    public Zone getZone() {
        return zone;
    }

    public double getPrice() {
        return price;
    }

    /**
     * To string methode for the ticket class
     * @return String representation of a ticket
     */
    @Override
    public String toString()
    {
        return type.toString() + " in " + zone.toString() + "(" + price + "€)";
    }
}
