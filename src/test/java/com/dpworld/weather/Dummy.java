package com.dpworld.weather;

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
}
