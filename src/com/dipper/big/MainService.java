package com.dipper.big;

/* Current APIs' set:
 * -  Google Maps Directions
 * -  Google Maps Geocoding
 *
 * Want more APIs? Requests being denied by Google? Shoot me a message on Messenger
 * - Stan C.
 */

/**
 * Holds path search mechanism to run at only back-end
 */
public class MainService {
    // Replace the API key below with a valid API key.
    private static String API_KEY = "AIzaSyALZvDqmZKte0ru1-fekJQE9ekCgovQcYw";
    private static MapManager mManager;

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
    //      1. https://search.maven.org/#search%7Cga%7C1%7Cgoogle%20maps%20services
    //      2. Take the latest version, specify it in pom.xml

    /**
     * Add specific locations to perform getOptimalPath in MapManager
     * @throws LocationException
     */
    public static void main(String[] args) throws LocationException{
        //Initialization
        mManager = MapManager.getInstance(API_KEY);

        final DistanceResult destinations = new DistanceResult();
        destinations.add("central park");
        destinations.add("high line");
        destinations.add("times square");

        //Grab optimal path
        System.out.println(mManager.getAllOptimalPath(destinations, false).toString());
    }
}
