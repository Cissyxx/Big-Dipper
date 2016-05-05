package com.dipper.big;

import java.util.HashMap;
import java.util.Map;

/**
 * user defined class to store corresponding distance for each location
 */
public class DistanceLength
{
    private final Map<Integer, Long> mDistance;

    /**
     * default constructor
     */
    public DistanceLength()
    {
        mDistance = new HashMap<Integer, Long>();
    }

    /**
     * constructor with Map<Integer, Long>
     * @param distance
     */
    public DistanceLength(Map<Integer, Long> distance)
    {
        mDistance = new HashMap<Integer, Long>(distance);
    }

    /**
     * returns the corresponding value by the given key
     * @param key to find the corresponding value
     * @return distance in Long format
     */
    public Long get(Integer key)
    {
        System.out.println("key is " + key);
        System.out.println("value is " + mDistance.get(key));
        return mDistance.get(key);
    }

    /**
     * add a pair of Integer and Long
     * @param key unique Integer
     * @param value paired with key
     */
    public void put(Integer key, Long value)
    {
        mDistance.put(key, value);
    }
}
