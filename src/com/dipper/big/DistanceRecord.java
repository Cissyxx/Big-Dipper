package com.dipper.big;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * user defined class to store different distance record
 */
public class DistanceRecord
{
    private final Set<Integer> mRecord;

    /**
     * default constructor
     */
    public DistanceRecord()
    {
        mRecord = new HashSet<Integer>();
    }

    /**
     * constructor with Set<Integer>
     * @param record set of integer
     */
    public DistanceRecord(Set<Integer> record)
    {
        mRecord = new HashSet<Integer>(record);
    }

    /**
     * add a value to the object
     * @param range value to be added
     */
    public void add(int range)
    {
        mRecord.add(range);
    }

    /**
     * remove a value from the object
     * @param range value to be removed
     */
    public void remove(int range)
    {
        mRecord.remove(range);
    }

    /**
     * determine whether the object contain the given value
     * @param range value to be determined
     * @return boolean
     */
    public boolean contains(int range)
    {
        return mRecord.contains(range);
    }


    /**
     * returns distance in set format
     * @return distance record in set format
     */
    public Set<Integer> getDistance()
    {
        return mRecord;
    }

    /**
     * returns an iterator of the values in set
     * @return iterator on specific object
     */
    public Iterator<Integer> iterator()
    {
        return mRecord.iterator();
    }
}
