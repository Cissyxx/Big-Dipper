package com.dipper.big;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

import com.dipper.big.MapManager;
import com.google.gson.Gson;

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
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String loc1, loc2, loc3, loc4, loc5;
		loc1 = request.getParameter("loc1");
		loc2 = request.getParameter("loc2");
		loc3 = request.getParameter("loc3");
		loc4 = request.getParameter("loc4");
		loc5 = request.getParameter("loc5");
//		out.println("Locations are: ");
//		out.println(String.format(" - %s", loc1));
//		out.println(String.format(" - %s", loc2));
//		out.println(String.format(" - %s", loc3));
//		out.println(String.format(" - %s", loc4));
//		out.println(String.format(" - %s", loc5));
		
		MapManager mManager = MapManager.getInstance(API_KEY);
		List<String> dest = new LinkedList<String>();
		if(loc1 != null && !loc1.isEmpty()) dest.add(loc1);
		if(loc2 != null && !loc2.isEmpty()) dest.add(loc2);
		if(loc3 != null && !loc3.isEmpty()) dest.add(loc3);
		if(loc4 != null && !loc4.isEmpty()) dest.add(loc4);
		if(loc5 != null && !loc5.isEmpty()) dest.add(loc5);
		
		

	    //response.setContentType("application/json");
	    //response.setCharacterEncoding("UTF-8");
	    PrintWriter out = response.getWriter();
	    String output = new Gson().toJson(mManager.getOptimalPath(dest));
//	    List<String> result = mManager.getOptimalPath(dest);
//	    String output = result.toString();
	    System.out.println(output);
	    out.write(output);
		//response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		boolean ajax = "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));

	    if (ajax) {
	        // Handle ajax (JSON or XML) response.
	    	PrintWriter out = response.getWriter();
	    	System.out.println("Handled with AJAX");
	    } else {
	        // Handle regular (JSP) response.
	    }
		doGet(request, response);
	}
	
	

}
