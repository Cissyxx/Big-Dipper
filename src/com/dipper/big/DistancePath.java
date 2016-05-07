package com.dipper.big;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * user defined class on paths
 */
public class DistancePath
{
    private final Map<Integer, List<Integer>> mPath;

    /**
     * default constructor
     */
    public DistancePath()
    {
        mPath = new HashMap<Integer, List<Integer>>();
    }

    /**
     * constructor with a map
     * @param path map object
     */
    public DistancePath(Map<Integer, List<Integer>> path)
    {
        mPath = new HashMap<Integer, List<Integer>>(path);
    }

    /**
     * return the corresponding value by the given key
     * @param key integer to find the value
     * @return corresponding value in a map
     */
    public List<Integer> get(Integer key)
    {
        return mPath.get(key);
    }

    /**
     * add a pair of key and value to the object
     * @param key integer for searching
     * @param value corresponding value with key
     */
    public void put(Integer key, List<Integer> value)
    {
        mPath.put(key, value);
    }

    public Map<Integer, List<Integer>> getPath()
    {
        return mPath;
    }

    @Override
    public int hashCode() {
        return mPath.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof DistancePath){
            final DistancePath dr = (DistancePath) obj;
            return mPath.equals(dr.getPath());
        }
        return false;
    }

}
