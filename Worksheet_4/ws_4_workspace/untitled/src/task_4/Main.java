package task_4;

import javax.management.modelmbean.XMLParseException;
import java.sql.PreparedStatement;

/**
* Maiin class for the xml parsing task
* @author lkoeble 21487
*/
public class Main {
    public static void main(String[] argv)
    {
        String xml = "<team>" +
                "<person><vorname>Peter</vorname><nachname>Quill</nachname><alias>Starlord</al" +
                "ias></person>" +
                "<person><vorname>Rocket</vorname><nachname>Racoon</nachname><alias>--" +
                "</alias></person></team>";

        String xml2 = "<team><person><nachname>Spengler</nachname><titel>Dr.</alias>" +
                "<vorname>Egon</vorname></person>" +
                "<mensch><vorname>Peter</vorname><nachname>Venkman</nachname><title>-" +
                "</title></person></team>";

        XMLParser parser = new XMLParser(xml);
        XMLParser parser2 = new XMLParser(xml2);

        try {
            XMLNode root = parser.parse();
            System.out.println(root.repr(0));

            for (XMLNode node : root)
            {
                if (node.getqName().equals("person"))
                {
                    System.out.println(parsePerson(node));
                }
            }

            root = parser2.parse();
            System.out.println(root.repr(0));

            for (XMLNode node : root)
            {
                if (node.getqName().equals("person"))
                {
                    System.out.println(parsePerson(node));
                }
            }
        } catch (XMLParseException e) {
            throw new RuntimeException(e);
        }
    }

    private static Person parsePerson(XMLNode _root) throws XMLParseException {
        Person p = new Person();
        for (XMLNode node : _root)
        {
            switch (node.getqName())
            {
                case "vorname" -> p.setName(node.getContent());
                case "nachname" -> p.setLastName(node.getContent());
                case "alias" -> p.setAlias(node.getContent());
            }
        }
        if (p.isComplete()) return p;
        throw new XMLParseException("Cant parse person. Missing at lest one tag");
    }
}
