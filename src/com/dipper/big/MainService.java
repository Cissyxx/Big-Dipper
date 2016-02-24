package com.dipper.big;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;

public class MainService {
	private static String API_KEY = "AIzaSyALZvDqmZKte0ru1-fekJQE9ekCgovQcYw";
	/* Google Maps API Setup */
	// https://developers.google.com/maps/web-services/client-library#maven
	
	/* Maven Eclipse Setup */
	// http://stackoverflow.com/questions/8620127/maven-in-eclipse-step-by-step-installation
	
	/* Adding Maven dependency */
	// http://stackoverflow.com/questions/9164893/how-do-i-add-a-maven-dependency-in-eclipse
	// To determine google maps version and configure pom.xml to it:
	// 		1. https://search.maven.org/#search%7Cga%7C1%7Cgoogle%20maps%20services
	//		2. Take the latest version, specify it in pom.xml
	
	public static void main(String[] args){
		// Replace the API key below with a valid API key.
		GeoApiContext context = new GeoApiContext().setApiKey(API_KEY);
		GeocodingResult[] results = new GeocodingResult[0];
		try {
			results = GeocodingApi.geocode(context,
			    "1600 Amphitheatre Parkway Mountain View, CA 94043").await();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(results[0].formattedAddress);
	}
	
}
