package com.dpworld.weather;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.format.PeriodFormat;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * @author PARK Sungung
 * @since 0.0.1
 */
public class Dummy {
    @Test
    public void test() {
        assertThat(Double.valueOf("291X".split("[A-Z]")[0]), is(291D));
        assertThat("123D".substring(0,"123D".length()-1), is("123"));
    }
    @Test
    public void testJodaTimePeriod() {
        DateTime now = new DateTime();
        DateTime old = now.minusMinutes(90);
        Period period = new Period(old, now);
        assertThat(PeriodFormat.getDefault().print(period), is("1 hour and 30 minutes"));
    }
    @Test
    public void testPrettyPeriod() {
        DateTime now = new DateTime();
        DateTime old = now.minusMinutes(90);
        assertThat(Utils.prettyPeriod(old, now), is("1h30m ago."));

    }
}
