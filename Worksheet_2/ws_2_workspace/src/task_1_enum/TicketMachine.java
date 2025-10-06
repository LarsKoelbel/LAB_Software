package task_1_enum;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Main class of the ticket machine
 * @author lkoelbel-Mat-NR: 21487
 */
public class TicketMachine {
    /**
     * Print all tickets available in all zones and there price
     */
    public static void printAllTickets()
    {
        System.out.println("All available tickets:");
        for (TicketType t : TicketType.values())
        {
            for (Zone z : Zone.values())
            {
                System.out.println(t.toString() + " in " + z.toString() + ": " + TicketUtils.calculateTicketPrice(t,z) + "€");
            }
        }
    }

    /**
     * Print the over all cheapest ticket
     */
    public static void printCheapestTicket()
    {
        List<Ticket> ticketList = new ArrayList<>();
        for (TicketType t : TicketType.values())
        {
            for (Zone z : Zone.values())
            {
                ticketList.add(new Ticket(t,z, TicketUtils.calculateTicketPrice(t,z)));
            }
        }

        ticketList.sort(Comparator.comparingDouble(Ticket::getPrice));

        System.out.println("Cheapest ticket available: " + ticketList.getFirst());
    }
}
