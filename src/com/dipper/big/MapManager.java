package com.dipper.big;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.maps.DirectionsApi;
import com.google.maps.DirectionsApiRequest;
import com.google.maps.DistanceMatrixApi;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.DirectionsLeg;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.DirectionsRoute;
import com.google.maps.model.DirectionsStep;
import com.google.maps.model.DistanceMatrix;
import com.google.maps.model.LatLng;

/**
 * This is a singleton class used in the entire system
 * It holds the main algorithm to find the optimal path
 */
public class MapManager {

    private static MapManager mManager = new MapManager();
    private final DistanceResult mDestinations = new DistanceResult();
    private final GeoApiContext mContext = new GeoApiContext();

    private static final String FAIL_DISTANCE_MATRIX = "Fail to create distance matrix";
    private static final String FAIL_TO_GET_DISTANCE = "Fail to get distance";
    private static final String NO_PATH = "Fail to find a path from the destinations";

    private MapManager(){}

    /**
     * Get single instance of MapManager
     * @param API_KEY specific API KEY that STAX registered for Google API
     * @return updated MapManager
     */
    public static MapManager getInstance(final String API_KEY)
    {
        mManager.mContext.setApiKey(API_KEY);
        return mManager;
    }

    /**
     * Get distance matrix
     * @param mContext provided helper class from Google API
     * @param mDestinations list of destination names
     * @return distance matrix if not no exception caught
     */
    private static DistanceMatrix getDistanceMatrix(GeoApiContext mContext, DistanceResult mDestinations) throws LocationException {
        try {
            //Grab distance matrix
            return DistanceMatrixApi.getDistanceMatrix(mContext,
                    (mDestinations.toArray(new String[mDestinations.size()])),
                    (mDestinations.toArray(new String[mDestinations.size()]))).await();
        } catch (final Exception e) {
            throw new LocationException(FAIL_DISTANCE_MATRIX, e);
        }
    }

    /**
     * Generate path combinations
     * @param n location from 0 - n
     * @param k number of items
     * @param index reference to location position
     * @return set of destination orders
     */
    private static Set<DistanceRecord> generatePickyCombinations(int n, int k, int index){
        final Set<DistanceRecord> mResult = generateCombinations(n, k);
        final Iterator<DistanceRecord> i = mResult.iterator();
        while(i.hasNext()){
            final DistanceRecord temp = i.next();
            if(temp.contains(index) == false) {
                i.remove();
            }
        }
        return mResult;
    }

    /**
     * Generate different combinations
     * @param n location from 0 - n
     * @param k number of items
     * @return set of destination orders
     */
    private static Set<DistanceRecord> generateCombinations(int n, int k){
        final Set<DistanceRecord> mResult = new HashSet<DistanceRecord>();
        combinationHelper(0, n, k, new DistanceRecord(), mResult);
        return mResult;
    }

    /**
     * helper function to generate combinations
     * @param min minimum distance
     * @param max maximum distance
     * @param n location from 0 - n
     * @param mRecord remaining locations from previous calculation
     * @param mResult locations in travel order
     */
    private static void combinationHelper(int min, int max, int n, DistanceRecord mRecord, Set<DistanceRecord> mResult){
        final int minRange = min + 1;
        if(n == 0){
            mResult.add(new DistanceRecord(mRecord));
        } else {
            for(Integer i = minRange; i <= max - (n-1); i++){
                mRecord.add(i);
                combinationHelper(i,max,n-1,mRecord,mResult);
                mRecord.remove(i);
            }
        }
    }

    /**
     * Get the minimum distance
     * @param mtx distance matrix obtained from previous functions
     * @param mDistance stores path length for specific starting location
     * @param mPath stores path length for specific starting location and its corresponding path
     * @param mSubset locations in travel order
     * @param node starting location
     * @return
     * @throws LocationException when something unexpected happens
     */
    private static long getMinimalDistance(DistanceMatrix mtx, Map<DistanceRecord, DistanceLength> mDistance,
            Map<DistanceRecord, DistancePath> mPath, DistanceRecord mSubset, Integer node) throws LocationException{
        try
        {
            long mResult = Long.MAX_VALUE;
            int mResultCity = -1;

            //Make a new subset without node
            final DistanceRecord tempSubset = new DistanceRecord(mSubset);
            tempSubset.remove(node);

            //Find minimal distance
            final Iterator<Integer> i = tempSubset.iterator();
            while(i.hasNext()){
                final int mCity = i.next();

                //Hacky way to deal with Overflow
                final long d = Math.abs(mDistance.get(tempSubset).getValue(mCity) + mtx.rows[mCity-1].elements[node-1].distance.inMeters);

                //Check which value is smaller
                if(mResult > d){
                    mResult = d;
                    mResultCity = mCity;
                }
            }

            //Add to mPath and return result
            final List<Integer> mNewPath = new LinkedList<Integer>(mPath.get(tempSubset).get(mResultCity));
            mNewPath.add(node);
            mPath.get(mSubset).put(node,mNewPath);

            return mResult;
        } catch (final NullPointerException e)
        {
            throw new LocationException(NO_PATH, e);
        }
    }

    /**
     * Get the optimal path, which consists of each destination exactly once
     * @param mtx distance matrix provided by Google API
     * @return an ordered list of destinations corresponding to the optimal path,
     * @throws LocationException when something unexpected happens
     */
    private static DistanceResult getOptimalPath(DistanceMatrix mtx, boolean isStartLocSet) throws LocationException{
        final DistanceResult mResult = new DistanceResult();
        final int mSize = mtx.destinationAddresses.length;

        //Store distance values
        Map<DistanceRecord, DistanceLength> mDistance;
        Map<DistanceRecord, DistancePath> mPath;
        long bestDistance = Long.MAX_VALUE;
        List<Integer> bestPath = new LinkedList<Integer>();

        //Track whether start location is set
        int originLength = 0;
        if(isStartLocSet) {
            originLength = 1;
        } else {
            originLength = mtx.originAddresses.length;
        }

        //For each origin - Note, this must be 1-based
        for(int i = 1; i <= originLength; i++){
            mDistance = new HashMap<DistanceRecord, DistanceLength>();
            mPath = new HashMap<>();

            //Initialize the value of a pair of a set of origin and the origin itself to be 0
            final DistanceRecord tempDistance = new DistanceRecord();
            tempDistance.add(i);
            mDistance.put(tempDistance, new DistanceLength());
            mDistance.get(tempDistance).put(i, (long) 0);
            mPath.put(tempDistance, new DistancePath());
            mPath.get(tempDistance).put(i, new LinkedList<Integer>());

            //Add origin as first city
            mPath.get(tempDistance).get(i).add(i);

            //Solve subproblems of size 2 to n
            for(int j = 2; j <= mSize; j++){
                //For all possible subsets (indexes in subset are 1-based)
                final Set<DistanceRecord> mSubsets = generatePickyCombinations(mSize,j,i);
                final Iterator<DistanceRecord> itr = mSubsets.iterator();
                while(itr.hasNext()){

                    final DistanceRecord mSet = itr.next();

                    //Set value to infinite to prevent start and end at home
                    mDistance.put(mSet, new DistanceLength());
                    mDistance.get(mSet).put(i, Long.MAX_VALUE);

                    //Add new list for this set
                    mPath.put(mSet, new DistancePath());

                    //For each Element node in the set, calculate optimal path given that element node is the last destination
                    final Iterator<Integer> itr2 = mSet.iterator();
                    while(itr2.hasNext()){
                        //Skip origin though
                        final Integer node = itr2.next();
                        if(node == i) {
                            continue;
                        }

                        //Calculate optimal path given that element node is the last destination
                        mDistance.get(mSet).put(node, getMinimalDistance(mtx, mDistance, mPath, mSet, node));
                    }
                }
            }

            //Done for one origin. If best results, record its path and distance
            final Set<DistanceRecord> temp = generateCombinations(mSize,mSize);
            for(int j = 1; j <= mSize; j++){
                final Iterator<DistanceRecord> itr = temp.iterator();
                while(itr.hasNext()){
                    final DistanceRecord mSet = itr.next();
                    final DistanceLength dl = mDistance.get(mSet);
                    final long d = dl.getValue(j);
                    final long f = 1;

                    if(bestDistance > d){
                        bestDistance = d;
                        bestPath = mPath.get(mSet).get(j);
                    }
                }
            }

        }

        //Convert indices to strings
        for(int i = 0; i < bestPath.size(); i++){
            mResult.add(mtx.destinationAddresses[bestPath.get(i)-1]);
        }

        return mResult;
    }

    /**
     * Get the best path
     * @param mDestinations list of destinations
     * @param isStartLocSet if true, takes first destination as start location
     * @return list of ordered destinations
     * @throws LocationException when something unexpected happens
     */
    public DistanceResult getAllOptimalPath(DistanceResult mDestinations, boolean isStartLocSet) throws LocationException{

        try {
            //Grab Distance Matrix

            final DistanceMatrix mtx = getDistanceMatrix(mContext, mDestinations);


            //Check if successful
            if(mtx != null){
                //Search for optimal path
                final DistanceResult mPath = getOptimalPath(mtx, isStartLocSet);
                return mPath;
            } else {
                //Report error
                return null;
            }
        } catch (final NullPointerException e)
        {
            throw new LocationException(FAIL_DISTANCE_MATRIX, e);
        }
    }

    /**
     * Return the ordered list of directions given a path
     * @param mPath ordered list of destinations
     * @return ordered list of directions
     * @throws LocationException when something unexpected happens
     */
    public DistanceResult getDirections(DistanceResult mPath) throws LocationException{
        final DirectionsApiRequest mRequest = DirectionsApi.getDirections(mContext, mPath.get(0), mPath.get(mPath.size()-1));
        try {
            final DirectionsResult mResult = mRequest.waypoints(mPath.subList(1, mPath.size()).toArray(new String[mPath.size()-2])).await();
            final DistanceResult resultDirections = new DistanceResult();

            for(final DirectionsRoute r : mResult.routes){
                for(final DirectionsLeg l: r.legs){
                    for(final DirectionsStep s: l.steps){
                        resultDirections.add(s.htmlInstructions);
                    }
                }
            }
            return resultDirections;
        } catch (final Exception e) {
            throw new LocationException(FAIL_TO_GET_DISTANCE, e);
        }
    }

    public String getLocation(LatLng coords){
        try {
            return GeocodingApi.reverseGeocode(mContext,coords).await()[0].formattedAddress;
        } catch (final Exception e) {
            // TODO Auto-generated catch block
            return null;
        }
    }
}
