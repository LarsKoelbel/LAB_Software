package task_1;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.nio.channels.ScatteringByteChannel;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        PersonenContentHandler handler = new PersonenContentHandler(); //implements
        SAXParserFactory factory = SAXParserFactory.newInstance();
        String filename = "src/task_1/Personen.xml";
        factory.setNamespaceAware(true);// auf Namespace achten
        try {
            SAXParser parser = factory.newSAXParser();
            parser.parse(filename, handler);

            // Print result
            handler.printAllPersons();

            while (true)
            {
                Person person = getPersonFromCommandLine();
                if (person != null) handler.addPerson(person);
                printXML(handler);
            }

        } catch(Exception e) {
            String errorMessage =
                    "Error parsing " + filename + ": " + e;
            System.err.println(errorMessage);
            e.printStackTrace();
        }
    }

    private static void printXML(PersonenContentHandler _handler)
    {
        System.out.println(_handler.getXMLString());
    }

    private static Person getPersonFromCommandLine()
    {
        Scanner scanner = new Scanner(System.in);
        Person person = new Person();
        String input = null;

        System.out.print("Name: ");
        input = scanner.nextLine();
        if (!input.isBlank()) person.setName(input);
        else person.setName(null);

        System.out.print("Vornahme: ");
        input = scanner.nextLine();
        if (!input.isBlank()) person.setVorname(input);
        else person.setVorname(null);

        System.out.print("Geburtsdatum: ");
        input = scanner.nextLine();
        if (!input.isBlank())
        {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
            try {person.setGeburtsdatum(simpleDateFormat.parse(input));}
            catch (ParseException e)
            {
                person.setGeburtsdatum(null);
            }
        }
        else person.setGeburtsdatum(null);

        System.out.print("Postleitzahl: ");
        input = scanner.nextLine();
        if (!input.isBlank()) person.setPostleitzahl(input);
        else person.setPostleitzahl(null);

        System.out.print("Ort: ");
        input = scanner.nextLine();
        if (!input.isBlank()) person.setOrt(input);
        else person.setOrt(null);

        System.out.print("Hobby: ");
        input = scanner.nextLine();
        if (!input.isBlank()) person.setHobby(input);
        else person.setHobby(null);

        System.out.print("Lieblingsgericht: ");
        input = scanner.nextLine();
        if (!input.isBlank()) person.setLieblingsgericht(input);
        else person.setLieblingsgericht(null);

        System.out.print("Lieblingsband: ");
        input = scanner.nextLine();
        if (!input.isBlank()) person.setLieblingsband(input);
        else person.setLieblingsband(null);

        return person;

    }
}