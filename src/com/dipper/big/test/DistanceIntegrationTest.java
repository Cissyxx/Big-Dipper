package com.dipper.big.test;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.dipper.big.Distance;

public class DistanceIntegrationTest
{
    private Distance test;

    @Before
    public void set_up()
    {
        test = new Distance();
    }

    @Test
    public void test_constructor()
    {
        final Map<Integer, Long> distance = new HashMap<Integer, Long>();
        distance.put(1, Long.MAX_VALUE);
        test = new Distance(distance);

        Assert.assertTrue(test.getValue(1).equals(Long.MAX_VALUE));
    }

    @Test
    public void test_getValue()
    {
        final Long value = Long.MAX_VALUE;
        test.put(1, value);
        Assert.assertTrue(test.getValue(1).equals(value));
    }

    @Test
    public void test_getDistance()
    {
        final Map<Integer, Long> distance = new HashMap<Integer, Long>();
        distance.put(1, (long) 10);

        test.put(1, (long) 10);
        Assert.assertEquals(test.getDistance(), distance);
    }

}
