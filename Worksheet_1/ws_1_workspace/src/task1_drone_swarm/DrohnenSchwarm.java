/**
 * This file contains the DrohnenSchwarm class
 * @author lkoelbel
 * mat-nr: 21487
 */

package task1_drone_swarm;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * This class is used to store and manage data from the drone swarm
 */
public class DrohnenSchwarm {


    /**
     * The array stores the following data:
     * [0]: Drohnen-ID (ganzzahlig, z.B. 1.0, 2.0, ...)
     * [1]: Zeitstempel (in Sekunden)
     * [2]: X-Koordinate
     * [3]: Y-Koordinate
     * [4]: Z-Koordinate
     * [5]: Batteriestand (Prozentsatz, 0.0 - 100.0)
     * [6]: Status-Code (z.B. 1.0 für "in Mission", 2.0 für "Rückflug zur Basis", 3.0 für "Problem erkannt")
     */
    private double[][] droneData;

    /**
     * The array stores the swarm hotspot for each timestamp
     */
    private double[][] swarmHotspot;

    private int amountOfDrones = 0;
    private int recordedDuration = 0;

    private final Random rand = new Random();

    public DrohnenSchwarm(){}

    // Getters and steers

    public double[][] getDroneData() {
        return droneData;
    }

    public double[][] getSwarmHotspot() {
        return swarmHotspot;
    }

    // Collision detection
    /**
     * This function determines id there ahs been a collision between two drones at any point in the recorded data
     * @return A list of log messages for any collision
     */
    public ArrayList<String> findCollisions()
    {

        // Critical distance for detecting collisions
        final double CRITICAL_DISTANCE = 10;

        ArrayList<String> listOfCollisions = new ArrayList<>();

        if(amountOfDrones < 1 || recordedDuration < 1)
        {
            return listOfCollisions;
        }

        // Iterate thru the data with a loop
        for (int i = 0; i < recordedDuration; i++)
        {
            for (int d = 0; d < amountOfDrones; d++)
            {
                // Compare the distance of every drone with every other drone in the same timeframe
                int actualArrayPosition = i * amountOfDrones + d;
                int timeStampStartPosition = i * amountOfDrones;
                int timeStampEndPosition = timeStampStartPosition + amountOfDrones;

                // Get the data from the compare drone
                double xCompare = droneData[actualArrayPosition][2];
                double yCompare = droneData[actualArrayPosition][3];
                double zCompare = droneData[actualArrayPosition][4];
                double idCompare = droneData[actualArrayPosition][0];

                for(int c = timeStampStartPosition; c < timeStampEndPosition; c++)
                {
                    // Make sure its not the same drone
                    if (c == actualArrayPosition)
                    {
                        continue;
                    }

                    // Compare the drone distance
                    double x = droneData[c][2];
                    double y = droneData[c][2];
                    double z = droneData[c][2];

                    double dist = MathUtils.vectorDist(xCompare, yCompare, zCompare,x,y,z);

                    if(dist <= CRITICAL_DISTANCE)
                    {
                        // Add a logg entry for the collision
                        double id = droneData[c][0];
                        double timestamp = droneData[c][1];
                        String entry = String.format("Collision between droneID=%d and droneID=%d at timestamp %d with a distance of %.2fm",(int)idCompare, (int)id, (int)timestamp, dist);
                        listOfCollisions.add(entry);
                    }
                }
            }
        }

        return listOfCollisions;
    }

    // Battery supervision
    /**
     * This function finds drones with a low battery and returns there position
     * @param _treshold Threshold for low battery detection
     * @return List of drones in the following format: array(id,x,y,z)
     */
    public ArrayList<double[]> findLowBattery(double _treshold)
    {

        ArrayList<double[]> result = new ArrayList<>();

        if (droneData.length < 1) return result;

        // Check last capture
        for (int i = droneData.length - amountOfDrones; i < droneData.length; i++)
        {
            double batteryPercentage = droneData[i][5];
            if (batteryPercentage <= _treshold)
            {
                // Add drone to list
                double id = droneData[i][0];
                double x = droneData[i][2];
                double y = droneData[i][3];
                double z = droneData[i][4];
                result.add(new double[]{id,x,y,z});
            }
        }

        return result;
    }

    // Swarm hotspot
    /**
     * This function calculates the swarm hotspot for each timestamp
     */
    public void calculateSwarmHotspots()
    {

        if (droneData.length < 1)
        {
            return;
        }

        // Create storrage array
        swarmHotspot = new double[recordedDuration][3];

        for (int i = 0; i < recordedDuration; i++) {
            double xAverage = 0;
            double yAverage = 0;
            double zAverage = 0;

            for (int d = 0; d < amountOfDrones; d++) {
                // Get the average position of all drones in this timestamp
                int actualArrayPosition = i * amountOfDrones + d;
                xAverage += droneData[actualArrayPosition][2];
                yAverage += droneData[actualArrayPosition][3];
                zAverage += droneData[actualArrayPosition][4];
            }

            xAverage /= amountOfDrones;
            yAverage /= amountOfDrones;
            zAverage /= amountOfDrones;

            swarmHotspot[i][0] = xAverage;
            swarmHotspot[i][1] = yAverage;
            swarmHotspot[i][2] = zAverage;
        }
    }

    // Functions for generating test data

    /**
     * This function generates the next position for a drone
     * @param _position Last position of the drone
     * @param _maxSpeed the maximum speed of the drone
     */
    private double[] generateNewPosition(double[] _position, double _maxSpeed){

        if (_position.length < 3)
        {
            throw new IllegalArgumentException("The provided array did not contain x, y and z coordinates");
        }

        double[] newPosition = new double[3];
        newPosition[0] = MathUtils.clampDouble(0,1000,
                _position[0] + (_maxSpeed * MathUtils.remapRangeLinearDouble(0,1,-1,1, rand.nextDouble())));
        newPosition[1] = MathUtils.clampDouble(0,1000,
                _position[1] + (_maxSpeed * MathUtils.remapRangeLinearDouble(0,1,-1,1, rand.nextDouble())));
        newPosition[2] = MathUtils.clampDouble(0,1000,
                _position[2] + (_maxSpeed * MathUtils.remapRangeLinearDouble(0,1,-1,1, rand.nextDouble())));

        return newPosition;
    }

    /**
     * This function is used to generate a new battery percentage for a drone
     */
    private double generateNewBatteryPercentage(double _last, boolean _charging)
    {

        if (_charging)
        {
            return MathUtils.clampDouble(0,100, _last + 3);
        }else
        {
            return MathUtils.clampDouble(0,100, _last - (rand.nextDouble()));
        }
    }

    /**
     * This function creates simulation data for testing this class
     * @param _amountOfDrones The amount of drones to be included in the simulation
     * @param _simDuration The time duration to be simulated (in timestamps)
     */
    public void generiereSimulationsdaten(int _amountOfDrones, int _simDuration)
    {


        int droneDataEntryAmount = _amountOfDrones * _simDuration;

        this.droneData = new double[droneDataEntryAmount][7];

        // Save the amount of drones ant the duration for later use
        amountOfDrones = _amountOfDrones;
        recordedDuration = _simDuration;

        // Create the drone data for each timestamp
        for(int i = 0; i < _simDuration; i++)
        {
            // Create the drone data for each drone
            for(int d = 0; d < _amountOfDrones; d++)
            {
                int actualArrayPosition = i * _amountOfDrones + d;
                int lastEntryPosition = actualArrayPosition - _amountOfDrones;

                double droneID = d + 1;
                double timestamp = i;
                double xPos = 0;
                double yPos = 0;
                double zPos = 0;
                double batterPercentage = 0;
                double statusCode = 1;

                if (i > 0)
                {
                    // If the loop is not on the starting timestamp, we use the last position to create a new one
                    double[] newPos = generateNewPosition(new double[]{droneData[lastEntryPosition][2], droneData[lastEntryPosition][3], droneData[lastEntryPosition][4]}, 3.5);
                    xPos = newPos[0];
                    yPos = newPos[1];
                    zPos = newPos[2];

                    // If the loop is not on the starting timestamp, we use the battery percentage to create a new one
                    batterPercentage = generateNewBatteryPercentage(droneData[lastEntryPosition][5], false);
                }else
                {
                    // If the loop is not on the starting timestamp, we create a random starting position
                    xPos = MathUtils.clampDouble(0,1000,1000 * rand.nextDouble());
                    yPos = MathUtils.clampDouble(0,1000,1000 * rand.nextDouble());
                    zPos = MathUtils.clampDouble(0,1000,1000 * rand.nextDouble());

                    // If the loop is not on the starting timestamp, we create a random starting battery percentage
                    batterPercentage = rand.nextDouble() * 100;
                }

                // Place the data into the array
                droneData[actualArrayPosition][0] = droneID;
                droneData[actualArrayPosition][1] = timestamp;
                droneData[actualArrayPosition][2] = xPos;
                droneData[actualArrayPosition][3] = yPos;
                droneData[actualArrayPosition][4] = zPos;
                droneData[actualArrayPosition][5] = batterPercentage;
                droneData[actualArrayPosition][6] = statusCode;

            }
        }
    }

    /**
     * Generate a report containing collisions, low battery data, hotspots
     */
    public String generateReport()
    {

        if(droneData.length < 1)
        {
            return "No data collected yet.";
        }

        StringBuilder result = new StringBuilder();
        // Get all collisions
        result.append("########## Collisions ##########\n");
        List<String> collisions = findCollisions();
        for (String collision : collisions)
        {
            result.append("\t").append(collision).append("\n");
        }

        // Get all low battery drones
        result.append("########## Low battery data ##########\n");
        ArrayList<double[]> lowBatteryData = findLowBattery(20);
        for (double[] capture : lowBatteryData)
        {
            result.append("\t").append(String.format("Low battery drone id=%d at position x=%.2f y=%.2f z=%.2f\n",(int)capture[0], capture[1], capture[2], capture[3]));
        }

        // Get all hotspots
        result.append("########## Hotspots ##########\n");
        calculateSwarmHotspots();
        for (int i = 0; i < swarmHotspot.length; i++)
        {
            result.append(String.format("Swarm hotspot at timestamp=%d at position x=%.2f y=%.2f z=%.2f\n", i,
                    swarmHotspot[i][0], swarmHotspot[i][1], swarmHotspot[i][2]));
        }

        return result.toString();
    }
}
