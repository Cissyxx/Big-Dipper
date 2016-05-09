package com.dipper.big;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * user defined class on paths
 */
public class Itinerary
{
    private final Map<Integer, List<Integer>> mItinerary;

    /**
     * default constructor
     */
    public Itinerary()
    {
        mItinerary = new HashMap<Integer, List<Integer>>();
    }

    /**
     * constructor with a map
     * @param path map object
     */
    public Itinerary(Map<Integer, List<Integer>> path)
    {
        mItinerary = new HashMap<Integer, List<Integer>>(path);
    }

    /**
     * return the corresponding value by the given key
     * @param key integer to find the value
     * @return corresponding value in a map
     */
    public List<Integer> get(Integer key)
    {
        return mItinerary.get(key);
    }

    /**
     * add a pair of key and value to the object
     * @param key integer for searching
     * @param value corresponding value with key
     */
    public void put(Integer key, List<Integer> value)
    {
        mItinerary.put(key, value);
    }

    public Map<Integer, List<Integer>> getItinerary()
    {
        return mItinerary;
    }

    @Override
    public int hashCode() {
        return mItinerary.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Itinerary){
            final Itinerary dr = (Itinerary) obj;
            return mItinerary.equals(dr.getItinerary());
        }
        return false;
    }

}
