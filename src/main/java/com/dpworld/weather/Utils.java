package com.dpworld.weather;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;
import org.springframework.util.Assert;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author PARK Sungung
 * @since 0.0.1
 */
public class Utils {

    private final static PeriodFormatter RELATIVE_TIME_FORMATTER = new PeriodFormatterBuilder()
            .appendDays().appendSuffix("d")
            .appendHours().appendSuffix("h")
            .appendMinutes().appendSuffix("m")
            .printZeroNever()
            .toFormatter();

    private final static DateFormat SHORT_TIME_FORMATTER = new SimpleDateFormat("HHmm");

    public static String prettyPeriod(Date start) {
        return prettyPeriod(new DateTime(start));
    }
    public static String prettyPeriod(DateTime start) {
        return prettyPeriod(start, new DateTime());
    }
    public static String prettyPeriod(DateTime start, DateTime end) {
        Assert.notNull(start); Assert.notNull(end);
        Period period = new Period(start, end);
        String pretty = RELATIVE_TIME_FORMATTER.print(period);
        if (StringUtils.isEmpty(pretty)) {
            return "now";
        } else {
            return RELATIVE_TIME_FORMATTER.print(period).concat(" ago");
        }

    }

    public static String shortenTime(Date timestamp) {
        return SHORT_TIME_FORMATTER.format(timestamp);
    }
}
