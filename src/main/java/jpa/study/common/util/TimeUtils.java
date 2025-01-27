package jpa.study.common.util;

import java.util.concurrent.TimeUnit;

public class TimeUtils {

    public static Long convertTimeUnit(int duration, TimeUnit from, TimeUnit to) {
        return switch (to) {
            case NANOSECONDS -> from.toNanos(duration);
            case MICROSECONDS -> from.toMicros(duration);
            case MILLISECONDS -> from.toMillis(duration);
            case SECONDS -> from.toSeconds(duration);
            case MINUTES -> from.toMinutes(duration);
            case HOURS -> from.toHours(duration);
            case DAYS -> from.toDays(duration);
        };
    }
}
