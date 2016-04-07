package com.dipper.big;

import java.io.BufferedReader;
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
	public static String API_KEY = "AIzaSyALZvDqmZKte0ru1-fekJQE9ekCgovQcYw";
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LocationServlet() {
        super();
        // TODO Auto-generated constructor stub
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
		PrintWriter out = response.getWriter();
		out.print("locations are: ");
		out.print(loc1 + " ");
		out.print(loc2 + " ");
		out.print(loc3 + " ");
		out.print(loc4 + " ");
		out.print(loc5 + " ");
		
		MapManager mManager = MapManager.getInstance(API_KEY);
		List<String> dest = new LinkedList<String>();
		if(loc1 != null) dest.add(loc1);
		if(loc2 != null) dest.add(loc2);
		if(loc3 != null) dest.add(loc3);
		if(loc4 != null) dest.add(loc4);
		if(loc5 != null) dest.add(loc5);
		
		out.print(mManager.getOptimalPath(dest).toString());
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
