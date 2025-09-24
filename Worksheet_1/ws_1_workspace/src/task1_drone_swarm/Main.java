package task1_drone_swarm;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Test function for the droneSwarm class
 */
public class Main {

    public static void main(String[] argv)
    {


        DrohnenSchwarm drohnenSchwarm = new DrohnenSchwarm();
        drohnenSchwarm.generiereSimulationsdaten(50,1000);
        long start = System.currentTimeMillis();
        ArrayList<String> collisionLog = drohnenSchwarm.findCollisions();
        long end = System.currentTimeMillis();
        System.out.println("Execution time for collision detection: " + (end - start) + " ms");
        String report = drohnenSchwarm.generateReport();
        System.out.println(report);
    }
}
