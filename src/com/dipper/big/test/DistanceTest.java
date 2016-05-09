package com.dipper.big.test;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.dipper.big.Distance;

public class DistanceTest
{
    private Distance test;

    @Before
    public void set_up()
    {
        test = Mockito.mock(Distance.class);
    }

    @Test
    public void test_getValue()  {
        //  create mock
        final Long value = (long) 43;

        // define return value for method getUniqueId()
        Mockito.when(test.getValue(10)).thenReturn(value);

        // use mock in test
        Assert.assertEquals(test.getValue(10), value);
    }

    @Test
    public void test_getDistance()
    {
        final Map<Integer, Long> distance = new HashMap<Integer, Long>();

        Mockito.when(test.getDistance()).thenReturn(distance);

        Assert.assertEquals(test.getDistance(), distance);
    }

}
