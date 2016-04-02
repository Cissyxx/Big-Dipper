package com.dipper.big.test;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;

import com.dipper.big.MainService;
import com.google.maps.GeoApiContext;
import com.google.maps.model.Distance;
import com.google.maps.model.DistanceMatrix;
import com.google.maps.model.DistanceMatrixElement;
import com.google.maps.model.DistanceMatrixRow;
import com.google.maps.model.Duration;


public class MainTest {

	private static DistanceMatrix mtx;
	private static final String[] DESTINATIONS = {
		"Seattle", "New York"	
	};
	
	private static final long[][] distanceData = {
			{0, 4598875},
			{4589777, 0}
	};
	
	private static final long[][] timeData = {
			{0, 150128},
			{150074, 0}
	};
	
	public static DistanceMatrix initializeMtx(String[] origin, String[] dest, 
			long[][] distanceData, long [][] timeData){
		DistanceMatrixRow[] rows = new DistanceMatrixRow[distanceData.length];
		System.out.println(rows.length);
		for(int i = 0; i < rows.length; i++){
			rows[i] = new DistanceMatrixRow();
			
			rows[i].elements = new DistanceMatrixElement[distanceData[i].length];
			for(int j = 0; j < rows[i].elements.length; j++){
				rows[i].elements[j] = new DistanceMatrixElement();
				rows[i].elements[j].distance = new Distance();
				rows[i].elements[j].distance.inMeters = distanceData[i][j];
				rows[i].elements[j].duration = new Duration();
				rows[i].elements[j].duration.inSeconds = timeData[i][j];
			}
		}
		
		return new DistanceMatrix(origin, dest, rows);
	}
	
	@BeforeClass
	public static void setupBeforeTests() throws Exception {
		mtx = initializeMtx(DESTINATIONS, DESTINATIONS, distanceData, timeData);
	}
	
	
	@Test
	public void test() {
		fail("Not yet implemented");
	}
	
	//Assume mtx != null
	@Test
	public void initTest(){
		MainService.printMatrix(mtx);
	}
	
	@Test
	public void generateCombinationTest(){
		printCombinations(5,1);
		printCombinations(5,2);
		printCombinations(5,3);
		printCombinations(5,4);
		printCombinations(5,5);
		
		printPickyCombinations(5,1,2);
		printPickyCombinations(5,1,1);
		printPickyCombinations(5,2,2);
		printPickyCombinations(5,2,1);
	}
	
	private void printCombinations(int n, int k){
		Set< Set<Integer> > mResult = MainService.generateCombinations(n, k);
		for(Set<Integer> s: mResult){
			System.out.print("( ");
			for(Integer i: s){
				System.out.print(i + " ");
			}
			System.out.print(") ");
		}
		System.out.println("");
	}
	
	private void printPickyCombinations(int n, int k, int index){
		Set< Set<Integer> > mResult = MainService.generatePickyCombinations(n, k, index);
		for(Set<Integer> s: mResult){
			System.out.print("( ");
			for(Integer i: s){
				System.out.print(i + " ");
			}
			System.out.print(") ");
		}
		System.out.println("");
	}
	
	//Assume mtx != null
	@Test
	public void optimalPath1Test(){
		// Replace the API key below with a valid API key.
		
		List<String> mResult = MainService.getOptimalPath(mtx);
		assertEquals("New York", mResult.get(0));
		assertEquals("Seattle", mResult.get(1));
	}

}
