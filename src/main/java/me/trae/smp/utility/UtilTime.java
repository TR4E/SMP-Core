package me.trae.smp.utility;

public final class UtilTime {

    public static double convert(final double time, final int trim, TimeUnit unit) {
        if (unit == TimeUnit.BEST) {
            unit = (time < 60000L ? TimeUnit.SECONDS : (time < 3600000L ? TimeUnit.MINUTES : (time < 86400000L ? TimeUnit.HOURS : (time < 31536000000L ? TimeUnit.DAYS : TimeUnit.YEARS))));
        }
        return (unit.equals(TimeUnit.SECONDS) ? UtilMath.trim(trim, time / 1000.0D) : (unit.equals(TimeUnit.MINUTES) ? UtilMath.trim(trim, time / 60000.0D) : (unit.equals(TimeUnit.HOURS) ? UtilMath.trim(trim, time / 3600000.0D) : (unit.equals(TimeUnit.DAYS) ? UtilMath.trim(trim, time / 86400000.0D) : (unit.equals(TimeUnit.YEARS) ? UtilMath.trim(trim, time / 31536000000.0D) : UtilMath.trim(trim, time))))));
    }

    public static String getTime(final long time, final TimeUnit unit, final int trim) {
        return convert(time, trim, unit) + " " + (unit.equals(TimeUnit.BEST) ? UtilFormat.cleanString((time < 60000L ? TimeUnit.SECONDS : (time < 3600000L ? TimeUnit.MINUTES : (time < 86400000L ? TimeUnit.HOURS : (time < 31536000000.0D ? TimeUnit.DAYS : TimeUnit.YEARS)))).name()) : UtilFormat.cleanString(unit.name()));
    }

    public static boolean elapsed(final long from, final long required) {
        return (System.currentTimeMillis() - from >= required);
    }

    public enum TimeUnit {
        SECONDS, MINUTES, HOURS, DAYS, YEARS, BEST
    }
}