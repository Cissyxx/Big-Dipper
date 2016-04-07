package com.dipper.big;

import java.util.Arrays;
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
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.DistanceMatrix;
import com.google.maps.model.DistanceMatrixElement;
import com.google.maps.model.DistanceMatrixRow;

//Singleton class
public class MapManager {
	
	private static MapManager mManager = new MapManager();
	private List<String> mDestinations = new LinkedList<String>();
	private GeoApiContext mContext = new GeoApiContext();
	
	private MapManager(){}
	
	//Get single instance of MapManager
	public static MapManager getInstance(final String API_KEY){
		mManager.mContext.setApiKey(API_KEY);
		return mManager;
	}
	
	private static DistanceMatrix getDistanceMatrix(GeoApiContext mContext, List<String> mDestinations){
		try {
			//Grab distance matrix
			return DistanceMatrixApi.getDistanceMatrix(mContext, 
					(mDestinations.toArray(new String[mDestinations.size()])),
					(mDestinations.toArray(new String[mDestinations.size()]))).await();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private static Set< Set<Integer> > generatePickyCombinations(int n, int k, int index){
		Set<Set<Integer> > mResult = generateCombinations(n, k);
		Iterator<Set<Integer>> i = mResult.iterator();
		while(i.hasNext()){
			Set<Integer> temp = i.next();
			if(temp.contains(index) == false)
				i.remove();
		}
		return mResult;
	}
	
	//n: 0 - n
	//k: # of items
	private static Set< Set<Integer> > generateCombinations(int n, int k){
		Set<Set<Integer> > mResult = new HashSet< Set<Integer> >(); 
		combinationHelper(0, n, k, new HashSet<Integer>(), mResult);
		return mResult;
	}
	
	//Recursive function
	private static void combinationHelper(int min, int max, int n, Set<Integer> mRecord, Set< Set<Integer> > mResult){
		int minRange = min + 1;
		if(n == 0){
			mResult.add(new HashSet<Integer>(mRecord));
		} else {
			for(int i = minRange; i <= max - (n-1); i++){
				mRecord.add(i);
				combinationHelper(i,max,n-1,mRecord,mResult);
				mRecord.remove(i);
			}
		}
	}
	
	private static void printMatrix(DistanceMatrix mtx){
		//Check if successful
		if(mtx != null){
			//Grab list of destinations
			List<String> destinations = new LinkedList<>(Arrays.asList(mtx.originAddresses));
			
			System.out.println("Origins:");
			for(String s: destinations){
				System.out.println(s);
			}
			
			System.out.println("Destinations:");
			for(String s: mtx.destinationAddresses){
				System.out.println(s);
			}
			
			for(DistanceMatrixRow d: mtx.rows){
				for(DistanceMatrixElement e : d.elements)
					System.out.print("{" + e.distance.inMeters + " meteres | " 
							+ e.duration.inSeconds + " seconds}");
				System.out.println("");
			}
		}
	}
	
	private static long getMinimalDistance(DistanceMatrix mtx, Map< Set<Integer>, Map<Integer, Long> > mDistance,
			Map< Set<Integer>, Map<Integer, List<Integer>> > mPath, Set<Integer> mSubset, Integer node){
		long mResult = Long.MAX_VALUE;
		int mResultCity = -1;
		
		//Make a new subset without node
		Set<Integer> tempSubset = new HashSet<Integer>(mSubset);
		//System.out.println("Before: " + tempSubset);
		tempSubset.remove(node);
		//System.out.println("Remaining: " + tempSubset);
		
		//Find minimal distance 
		Iterator<Integer> i = tempSubset.iterator();
		while(i.hasNext()){
			int mCity = i.next();
			
			//Hacky way to deal with Overflow
			long d = Math.abs(mDistance.get(tempSubset).get(mCity) + mtx.rows[mCity-1].elements[node-1].distance.inMeters);
			
			//Check which value is smaller
			if(mResult > d){
				mResult = d;
				mResultCity = mCity;
			}
		}
		
		//Add to mPath and return result
		//System.out.println("TempSubset: " + tempSubset.toString() + " " + mResultCity);
		List<Integer> mNewPath = new LinkedList<Integer>(mPath.get(tempSubset).get(mResultCity));
		//System.out.println("Path: " + mNewPath.toString());
		mNewPath.add(node);
		//System.out.println("Subset: " + mSubset.toString() + " " + node);
		mPath.get(mSubset).put(node,mNewPath);
		
		return mResult;
	}
	
	//Given a DistanceMatrix mtx, return an ordered list of destinations corresponding to the 
	//optimal path, where the optimal path consists of each destination exactly once.
	private static List<String> getOptimalPath(DistanceMatrix mtx){
		List<String> mResult = new LinkedList<>();
		int mSize = mtx.destinationAddresses.length;
		
		//Store distance values
		Map< Set<Integer>, Map<Integer, Long> > mDistance;
		Map< Set<Integer>, Map<Integer, List<Integer>> > mPath;
		long bestDistance = Long.MAX_VALUE;
		List<Integer> bestPath = null;
		
		//For each origin - Note, this must be 1-based
		for(int i = 1; i <= mtx.originAddresses.length; i++){
			mDistance = new HashMap<>();
			mPath = new HashMap<>();
			
			//Initialize the value of a pair of a set of origin and the origin itself to be 0
			Set<Integer> t = new HashSet<Integer>();
			t.add(i);
			mDistance.put(t, new HashMap<Integer, Long>());
			mDistance.get(t).put(i, (long) 0);
			mPath.put(t, new HashMap<Integer, List<Integer>>());
			mPath.get(t).put(i, new LinkedList<Integer>());
			
			//Add origin as first city
			mPath.get(t).get(i).add(i);
			
			//Solve subproblems of size 2 to n
			for(int j = 2; j <= mSize; j++){
				//For all possible subsets (indexes in subset are 1-based)
				Set< Set<Integer> > mSubsets = generatePickyCombinations(mSize,j,i);
				Iterator<Set<Integer> > itr = mSubsets.iterator();
				while(itr.hasNext()){
					Set<Integer> mSet = itr.next();
					
					//Set value to infinite to prevent start and end at home
					mDistance.put(mSet, new HashMap<Integer, Long>());
					mDistance.get(mSet).put(i, Long.MAX_VALUE);
					
					//Add new list for this set
					mPath.put(mSet, new HashMap<Integer, List<Integer>>());
//					mPath.get(mSet).put(i, new LinkedList<Integer>());
					
					//For each Element node in the set, calculate optimal path given that element node is the last destination
					Iterator<Integer> itr2 = mSet.iterator();
					while(itr2.hasNext()){
						//Skip origin though
						Integer node = itr2.next();
						if(node == i) continue;
						
						//Calculate optimal path given that element node is the last destination
						mDistance.get(mSet).put(node, getMinimalDistance(mtx, mDistance, mPath, mSet, node));
					}
				}
			}
			
			//Done for one origin. If best results, record its path and distance
			Set< Set<Integer>> temp = generateCombinations(mSize,mSize);
			for(int j = 1; j <= mSize; j++){
				Iterator<Set<Integer>> itr = temp.iterator();
				while(itr.hasNext()){
					Set<Integer> mSet = itr.next();
					Long d = mDistance.get(mSet).get(j);
					if(bestDistance > d){
						bestDistance = d;
						bestPath = mPath.get(mSet).get(j);
					}
				}
			}

//			System.out.println(mDistance.toString());
//			System.out.println("Overall Distance: " + bestDistance);
//			System.out.println("Overall Path: " + bestPath);
		}
		
		//Convert indices to strings
		for(int i = 0; i < bestPath.size(); i++){
			mResult.add(mtx.destinationAddresses[bestPath.get(i)-1]);
		}
		
		return mResult;
	}
	
	//Get Best Path given list of destinations
	public List<String> getOptimalPath(List<String> mDestinations){
		
		//Grab Distance Matrix
		DistanceMatrix mtx = getDistanceMatrix(mContext, mDestinations);
		
		//Check if successful
		if(mtx != null){
			//Search for optimal path
			List<String> mPath = getOptimalPath(mtx);
			
			
			DirectionsApiRequest mRequest = DirectionsApi.getDirections(mContext, mPath.get(0), mPath.get(mPath.size()-1));
			try {
				DirectionsResult mResult = mRequest.waypoints(mPath.subList(1, mPath.size()).toArray(new String[mPath.size()-2])).await();
				
				/* Do something with results */
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return mPath;
		} else {
			//Report error
			return null;
		}
	}
}
