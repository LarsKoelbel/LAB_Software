package task_5_txt;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

/**
 * Main class taken from the task
 * @author lkoelbel-Mat-NR: 21487
 */
public class B5 {
    public static void main(String[] args) {
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter("./src/task_5_txt/test.txt"));
            out.write("Lorem ipsum dolor sit amet");
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}