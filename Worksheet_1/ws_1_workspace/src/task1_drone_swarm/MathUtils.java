/**
 * This file contains a number of math util functions
 * @author lkoelbel
 * mat-nr: 21487
 */

package task1_drone_swarm;

/**
 * This function is used to linearly remap a double value to a new range
 */
public class MathUtils {
    public static double remapRangeLinearDouble(double _valueMin, double _valueMax, double _remapMin, double _remapMax, double _value)
    {

        // Using linear interpolation

        double m = (_remapMax - _remapMin) / (_valueMax - _valueMin);
        return m * _value + _remapMin;
    }

    /**
     * This function is used to clam a double value within a range
     */
    public static double clampDouble(double _min, double _max, double _value)
    {


        if (_value >= _max) return _max;
        if (_value <= _min) return _min;
        return _value;
    }

    /**
     * This function is used to calculate the Euclidean distance between two vectors
     */

    public static double vectorDist(double _x1, double _y1, double _z1, double _x2, double _y2, double _z2)
    {

        return Math.sqrt(Math.pow((_x2 - _x1),2) + Math.pow((_y2 - _y1),2) + Math.pow((_z2 - _z1),2));
    }
}
