package task_1;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class Program {

    public static void main(String[] args)
    {
        Adresse adresse = new Adresse();
        adresse.setStrasse("Ringstr. 1");
        adresse.setOrt("Musterstadt");

        Person hugo = new Person();
        hugo.setName("Hugo Schmidt");
        hugo.setAdresse(adresse);

        Person erika = new Person();
        erika.setName("Erika Schmidt");
        erika.setAdresse(adresse);

        List<Person> list = new ArrayList<>();

        list.add(hugo);
        list.add(erika);

        // Serialize object
        String path = "./src/task_1/obj.ser";

        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(path))){
            outputStream.writeObject(list);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Deserialize the file

        List<Person> newList = new ArrayList<>();
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(path))){
           newList = (ArrayList<Person>) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        System.out.println(newList);

        // Comparing the address since Address has no equals methode for comparing content
        // so this is fine
        System.out.println(newList.get(0).getAdresse() == newList.get(1).getAdresse());

    }

}
