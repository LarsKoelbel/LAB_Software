package task_6_encodings;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;

/**
 * Main class for this task
 * @author lkoelbel-Mat-NR: 21487
 */
public class Main {
    /**
     * Main methode for the task
     * @param argv Console arguments
     */
    public static void main(String[] argv)
    {
        String path = "./src/task_6_encodings/message.txt";
        String message = "Die Welt kostet 17 €\r\n";
        writeToFile(message, "./src/task_6_encodings/message.txt", StandardCharsets.UTF_8);
        printByteBufferToConsole(readBytesFromFile(path), NumberSystem.DECIMAL);
    }

    /**
     * Write a message (String) to a file
     * @param _message String to write
     * @param _path Path for the file
     * @param _standardCharset Encoding to be used for writing
     * @return True if file was written
     */
    public static boolean writeToFile(String _message, String _path, Charset _standardCharset)
    {
        if (_message == null) return false;
        if (_path == null) return false;

        try (BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(_path),_standardCharset))){
            out.write(_message);
            out.flush();
            out.close();
            return true;
        } catch (IOException _){
            return false;
        }
    }

    /**
     * Read all bytes from a file
     * @param _path Path of the file
     * @return Array of bytes
     */
    public static byte[] readBytesFromFile(String _path){
        if (_path == null) return null;

        try{
           Path path = Paths.get(_path);
           return Files.readAllBytes(path);
        }catch (IOException _){
            return null;
        }
    }

    /**
     * Printing a buffer to the console (with index)
     * @param _buffer Buffer to be printed
     * @param _numberSystem Number system to use for the output
     */
    public static void printByteBufferToConsole(byte[] _buffer, NumberSystem _numberSystem){
        if (_buffer == null) print("Buffer is null");
        else if (_numberSystem == null) print("No number system specified");
        else {
            for (int i = 0; i < _buffer.length; i++){
                byte value = _buffer[i];
                if (_numberSystem == NumberSystem.DECIMAL) print(String.format(Locale.ENGLISH,"%d: %d",i, value & 0xFF));
                else if (_numberSystem == NumberSystem.HEXADECIMAL) print(String.format(Locale.ENGLISH,"0x%X: 0x%02X", i, value & 0xFF));
                else print(String.format(Locale.ENGLISH,"Unsupported number system %s", _numberSystem));
            }
        }

    }

    /**
     * Methode for printing to the console (Print line)
     * @param _obj Object to print
     */
    public static void print(Object _obj){
        System.out.println(_obj);
    }
}
