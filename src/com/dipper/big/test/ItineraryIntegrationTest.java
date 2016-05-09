package com.dipper.big.test;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.dipper.big.Itinerary;


public class ItineraryIntegrationTest
{
    private Itinerary test;

    @Before
    public void set_up()
    {
        test = new Itinerary();
    }

    @Test
    public void test_constructor()
    {
        final Map<Integer, List<Integer>> itinerary = new HashMap<Integer, List<Integer>>();
        final List<Integer> value = new LinkedList<Integer>();
        value.add(10);
        itinerary.put(1, value);

        test = new Itinerary(itinerary);

        Assert.assertEquals(test.getItinerary(), itinerary);
    }

    @Test
    public void test_get()
    {
        final List<Integer> value = new LinkedList<Integer>();
        value.add(10);
        test.put(1, value);

        Assert.assertEquals(test.get(1), value);
    }

}