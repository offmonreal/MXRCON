package mxrcon.Utilites;

import static java.lang.Math.floor;
import java.text.DecimalFormat;
import java.util.HashMap;
import javafx.util.Pair;

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

    public final static Pair<String, Integer> spilit(String source, String sperator)
    {
        if (source != null && !sperator.trim().isEmpty() && source.contains(sperator))
        {
            String[] tmp = source.split(sperator, 2);

            if (tmp.length == 2 && Inline.toInt(tmp[1]) > 0)
            {
                return new Pair<>(tmp[0], Inline.toInt(tmp[1]));
            }
        }

        return null;
    }
}
