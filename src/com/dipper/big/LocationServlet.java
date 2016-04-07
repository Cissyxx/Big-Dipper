package com.dipper.big;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

import com.dipper.big.MapManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class LocationServlet
 */
@WebServlet("/LocationServlet")
public class LocationServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static String API_KEY = "AIzaSyALZvDqmZKte0ru1-fekJQE9ekCgovQcYw";
    private static MapManager mManager;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LocationServlet() {
        super();
    }
    
    public void init() throws ServletException
    {
        // initialize mManager
    	mManager = MapManager.getInstance(API_KEY);
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
    */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {

        String loc1, loc2, loc3, loc4, loc5;
        
        // get all locations
        loc1 = request.getParameter("loc1");
        loc2 = request.getParameter("loc2");
        loc3 = request.getParameter("loc3");
        loc4 = request.getParameter("loc4");
        loc5 = request.getParameter("loc5");
        List<String> destinations = new LinkedList<String>();
        
        // if the loc have valid values, put them in destination list
        if (loc1 != null && !loc1.isEmpty()) {
            destinations.add(loc1);
        }
        if (loc2 != null && !loc2.isEmpty()) {
            destinations.add(loc2);
        }
        if (loc3 != null && !loc3.isEmpty()) {
            destinations.add(loc3);
        }
        if (loc4 != null && !loc4.isEmpty()) {
            destinations.add(loc4);
        }
        if (loc5 != null && !loc5.isEmpty()) {
            destinations.add(loc5);
        }

        // write the response to PrintWriter
        PrintWriter out = response.getWriter();
        out.print("locations are: ");
        String locations = mManager.getOptimalPath(destinations).toString();
        System.out.println(locations);
        out.print(locations);
        //response.getWriter().append("Served at: ").append(request.getContextPath());
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        doGet(request, response);
    }

}
