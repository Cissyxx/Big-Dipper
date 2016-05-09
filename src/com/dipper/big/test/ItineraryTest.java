package com.dipper.big.test;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.dipper.big.Itinerary;

public class ItineraryTest
{
    private Itinerary test;

    @Before
    public void set_up()
    {
        test = Mockito.mock(Itinerary.class);
    }

    @Test
    public void test_get()  {
        //  create mock
        final List<Integer> value = new LinkedList<Integer>();

        // define return value for method getUniqueId()
        Mockito.when(test.get(10)).thenReturn(value);

        // use mock in test
        Assert.assertEquals(test.get(10), value);
    }

    @Test
    public void test_getPath()
    {
        final Map<Integer, List<Integer>> path= new HashMap<Integer, List<Integer>>();

        Mockito.when(test.getItinerary()).thenReturn(path);

        Assert.assertEquals(test.getItinerary(), path);
    }

}
