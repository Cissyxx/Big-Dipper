package com.dipper.big;

import java.util.LinkedList;
import java.util.List;


/**
 * User defined class specifically for storing travel order
 */
public class DistanceResult
{

    private final List<String> mResult;

    /**
     * Default Constructor
     */
    public DistanceResult()
    {
        mResult = new LinkedList<String>();
    }

    /**
     * Constructor with a List<String>
     * @param result list of string
     */
    public DistanceResult(List<String> result)
    {
        mResult = new LinkedList<String>(result);
    }

    /**
     * Add a place to the object
     * @param place name of a place
     */
    public void add(String place)
    {
        mResult.add(place);
    }

    /**
     * Get the total number of the destinations
     * @return number of destinations
     */
    public int size()
    {
        return mResult.size();
    }

    /**
     * return the specific destination at given index value
     * @param index index of specific destination
     * @return destination value at given index
     */
    public String get(int index)
    {
        return mResult.get(index);
    }

    /**
     * return a specific sub list of original list between the given index values
     * @param fromIndex beginning index
     * @param toIndex ending index
     * @return sub-list of the original list
     */
    public List<String> subList(int fromIndex, int toIndex)
    {
        return mResult.subList(fromIndex, toIndex);
    }

    /**
     * Returns an array containing all of the elements in this list in proper sequence
     * @param strings
     * @return string array
     */
    public String[] toArray(String[] strings) {
        return mResult.toArray(strings);
    }

    /**
     * return the String list
     * @return String list that contains all destinations in proper order
     */
    public List<String> getResult()
    {
        return mResult;
    }
}
