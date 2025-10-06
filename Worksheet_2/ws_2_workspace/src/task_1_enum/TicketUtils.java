package task_1_enum;

/**
 * Utility class for ticket actions
 * @author lkoelbel-Mat-NR: 21487
 */
public class TicketUtils {
    /**
     * Calculate the price of a ticket based on the type and the zone
     * @param _type Type of the ticket
     * @param _zone Zone of the ticket
     */
    public static double calculateTicketPrice(TicketType _type, Zone _zone)
    {
        return _type.getPriceFactor() * _zone.getBasePrice();
    }
}
