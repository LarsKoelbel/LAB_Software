package task_2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

/**
 * @author larsk 21487
 */
public class Main{
    public static void main(String[] argv)
    {
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
                        System.out.println(line);
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
    }
}
