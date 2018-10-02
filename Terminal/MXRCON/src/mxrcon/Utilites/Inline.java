package mxrcon.Utilites;

import static java.lang.Math.floor;
import java.text.DecimalFormat;

/**
 *
 * @author maxim
 */
public class Inline
{

    final String roundDouble(double value, double base)
    {
        DecimalFormat df = new DecimalFormat("#.00");
        return df.format(value);
    }

    //Возможно нужен Math.ceil
    final double roundDoubleTo(double value, double base)
    {
        return floor((value + base / 2) / base) * base;
    }

    public final static int toInt(String value)
    {
        try
        {
            return Integer.parseInt(value);

        } catch (Exception ex)
        {
            return -1;
        }
    }
}

