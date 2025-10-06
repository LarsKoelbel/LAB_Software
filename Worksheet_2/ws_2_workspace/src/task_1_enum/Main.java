package task_1_enum;

/**
 * Test class for the task
 * @author lkoelbel-Mat-NR: 21487
 */
public class Main {
    public static void main(String[] argv)
    {
        // Ticket types
        for (TicketType t : TicketType.values())
        {
            System.out.println(t);
        }

        TicketMachine.printAllTickets();

        TicketMachine.printCheapestTicket();

    }
}
