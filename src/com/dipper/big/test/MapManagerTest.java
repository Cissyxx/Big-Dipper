package com.dipper.big.test;

import org.junit.Assert;
import org.junit.Test;

import com.dipper.big.LocationException;
import com.dipper.big.MapManager;
import com.dipper.big.Path;

public class MapManagerTest
{
    private static String API_KEY = "AIzaSyALZvDqmZKte0ru1-fekJQE9ekCgovQcYw";
    private static MapManager mManager;

    @Test
    public void test_getAllOptimalPath() throws LocationException{
        //Initialization
        mManager = MapManager.getInstance(API_KEY);

        final Path destinations = new Path();
        destinations.add("central park");
        destinations.add("high line");
        destinations.add("times square");

        final String expectedOutput = "[Central Park, New York, NY 10024, USA, Theater District, New York, NY, USA, The High Line, New York, NY 10011, USA]";

        //Grab optimal path
        Assert.assertEquals(mManager.getAllOptimalPath(destinations, false).getPath().toString(), expectedOutput);
    }

}
