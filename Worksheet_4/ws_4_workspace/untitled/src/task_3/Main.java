package task_3;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

/**
 * @author larsk 21487
 */
public class Main {
    public static void main(String[] argv)
    {
        String data = getRSSString();
        try
        {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();

            RSContentHandler handler = new RSContentHandler();
            parser.parse(new InputSource(new StringReader(data)), handler);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String getRSSString()
    {
        StringBuilder sb = new StringBuilder();
        try
        {
            URL url = new URL("https://rss.dw.com/xml/rss-de-all");
            try
            {
                URLConnection connection = url.openConnection();
                connection.setDoInput(true);
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8)))
                {
                    String line;
                    while ((line = reader.readLine()) != null)
                    {
                        //System.out.println(line);
                        sb.append(line).append("\n");
                    }
                }catch (IOException e)
                {
                    System.out.println("Failed to read data from server");
                }
            }catch (IOException e)
            {
                System.out.println("Failed to connect...");
            }
        }catch (MalformedURLException e)
        {
            System.out.println("Malformed URL");
        }
        return sb.toString();
    }
}
