package me.trae.smp.utility;

import org.bukkit.util.Vector;

import java.text.DecimalFormat;
import java.util.concurrent.ThreadLocalRandom;

public final class UtilMath {

    public static String format(final double input, final String decimalFormat) {
        return new DecimalFormat(decimalFormat).format(input);
    }

    public static double trim(final int degree, final double d) {
        final StringBuilder format = new StringBuilder("#.#");
        for (int i = 1; i < degree; i++) {
            format.append("#");
        }
        final DecimalFormat df = new DecimalFormat(format.toString());
        return Double.parseDouble(df.format(d).replaceAll(",", "."));
    }

    public static int randomInt(final int i) {
        return ThreadLocalRandom.current().nextInt(i);
    }

    public static int randomInt(final int i1, final int i2) {
        return ThreadLocalRandom.current().nextInt(i1, i2);
    }

    public static double offset(final Vector a, final Vector b) {
        return a.subtract(b).length();
    }
}