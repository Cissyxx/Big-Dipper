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
    public Long getValue(Integer key)
    {
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

    public Map<Integer, Long> getDistance()
    {
        return mDistance;
    }

    @Override
    public int hashCode() {
        return mDistance.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof DistanceLength){
            final DistanceLength dr = (DistanceLength) obj;
            return mDistance.equals(dr.getDistance());
        }
        return false;
    }


}
