package task_1_enum;

import java.util.Locale;

/**
 * Enum for the available zones
 * @author lkoelbel-Mat-NR: 21487
 */
public enum Zone implements IPrintable{
    INNER_CITY(2.0d),
    OUTER_CITY(3.0d),
    REGIONAL(5.0d);

    private double basePrice = 0;

    /**
     * Cinstructor for new Zone with base price
     * @param _basePrice Base price for the zone
     */
    Zone(double _basePrice)
    {
        basePrice = _basePrice;
    }

    public double getBasePrice()
    {
        return basePrice;
    }

    /**
     * Print all details to the command line
     */
    @Override
    public void printDetails() {
        System.out.println(String.format(Locale.ENGLISH, "%s | %.2f", this.toString(), basePrice));
    }
}
