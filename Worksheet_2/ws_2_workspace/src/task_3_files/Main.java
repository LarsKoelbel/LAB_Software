package task_3_files;

import java.io.File;
import java.io.FileInputStream;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Main class for file reading
 * @author lkoelbel-Mat-NR: 21487
 */
public class Main {
    /**
     * Main methode
     * @param argv Arguments form the command line
     */
    public static void main(String[] argv)
    {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter file path: ");
        String path = scanner.nextLine().replace("\"","");
        if (path == null || path.isBlank()){
            System.out.println("ERROR: Path cant be empty");
        }
        else
        {
            File file = new File(path);
            if(!file.exists())
            {
                System.out.println("ERROR: " + path + " is not a file.");
                return;
            }

            try (FileInputStream fis = new FileInputStream(path))
            {
                int b = 0;
                int i = 0;
                while ((b = fis.read()) != -1)
                {
                    System.out.println("Character at position " + i + ": " + (char) b);
                    i++;
                }
            }catch (Exception e)
            {
                System.out.println("ERROR: Reading the file failed, due to exception: " + Arrays.toString(e.getStackTrace()));
            }
        }
    }
}
