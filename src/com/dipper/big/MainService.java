package com.dipper.big;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.maps.DistanceMatrixApi;
import com.google.maps.DistanceMatrixApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.DistanceMatrix;
import com.google.maps.model.DistanceMatrixElement;
import com.google.maps.model.DistanceMatrixRow;
import com.google.maps.model.GeocodingResult;

/* Current APIs' set:
 * -  Google Maps Directions
 * -  Google Maps Geocoding
 * 
 * Want more APIs? Requests being denied by Google? Shoot me a message on Messenger
 * - Stan C.
 */


public class MainService {
	private static String API_KEY = "AIzaSyALZvDqmZKte0ru1-fekJQE9ekCgovQcYw";
	private static String URL_DISTANCE_MATRIX = 
			"https://maps.googleapis.com/maps/api/distancematrix/json?"
			+ "origins=Vancouver+BC|Seattle"
			+ "&"
			+ "destinations=San+Francisco|Victoria+BC"
			+ "&"
			+ "key=AIzaSyALZvDqmZKte0ru1-fekJQE9ekCgovQcYw";
	// To conserve the api resources, instead of generating:
	// 		-5x5 matrix of distances (25 elements),
	// generate:
	//		-4 1x4 matrices of distances (16 elements)
	
	/* Google Maps API Setup */
	// https://developers.google.com/maps/web-services/client-library#maven
	
	/* Maven Eclipse Setup */
	// http://stackoverflow.com/questions/8620127/maven-in-eclipse-step-by-step-installation
	
	/* Adding Maven dependency */
	// http://stackoverflow.com/questions/9164893/how-do-i-add-a-maven-dependency-in-eclipse
	// To determine google maps version and configure pom.xml to it:
	// 		1. https://search.maven.org/#search%7Cga%7C1%7Cgoogle%20maps%20services
	//		2. Take the latest version, specify it in pom.xml
	
	public static DistanceMatrix getDistanceMatrix(GeoApiContext mContext, List<String> mDestinations){
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
	
	
	
	public static Set < Set<Integer> > generatePickyCombinations(int n, int k, int index){
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
	public static Set< Set<Integer> > generateCombinations(int n, int k){
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
	
	public static void printMatrix(DistanceMatrix mtx){
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
		//C(S, j) = min{C(S − {j}, i) + dij : i ∈ S, i ̸= j}
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
	public static List<String> getOptimalPath(DistanceMatrix mtx){
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
					
					//For each Element e in the set, calculate optimal path given that element e is the last destination
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
	
	public static void main(String[] args){
		// Replace the API key below with a valid API key.
		GeoApiContext context = new GeoApiContext().setApiKey(API_KEY);
		
		List<String> destinations = new LinkedList<String>();
//		destinations.add("Seattle");
//		destinations.add("New York");
		destinations.add("San Francisco");
		destinations.add("Richmond");
		destinations.add("Cleveland");
		destinations.add("Portland");
		
		
		//Grab distance mtx
		DistanceMatrix mtx = getDistanceMatrix(context, destinations);
		
		//Check if successful
		if(mtx != null){
			//printMatrix(mtx);
			
			//Search for optimal path
			List<String> mPath = getOptimalPath(mtx);
			System.out.println(mPath.toString());
			
		}
	}
}
