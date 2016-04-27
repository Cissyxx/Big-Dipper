package com.dipper.big;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

/**
 * Servlet implementation class LocationServlet
 */
@WebServlet(urlPatterns={"/LocationServlet"})
public class LocationServlet extends HttpServlet {
    private static final long serialVersionUID = 102831973239L;
    private static String API_KEY = "AIzaSyALZvDqmZKte0ru1-fekJQE9ekCgovQcYw";
    private static MapManager mManager;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public LocationServlet() {
        super();
    }

    @Override
    public void init()
    {
        // initialize mManager
        mManager = MapManager.getInstance(API_KEY);
    }
    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String currentLoc, loc1, loc2, loc3, loc4, loc5;
        currentLoc = request.getParameter("currentloc");
        System.out.println(currentLoc);
        loc1 = request.getParameter("loc1");
        loc2 = request.getParameter("loc2");
        loc3 = request.getParameter("loc3");
        loc4 = request.getParameter("loc4");
        loc5 = request.getParameter("loc5");

        final MapManager mManager = MapManager.getInstance(API_KEY);
        final List<String> dest = new LinkedList<String>();
        if(loc1 != null && !loc1.isEmpty()) {
            dest.add(loc1);
        }
        if(loc2 != null && !loc2.isEmpty()) {
            dest.add(loc2);
        }
        if(loc3 != null && !loc3.isEmpty()) {
            dest.add(loc3);
        }
        if(loc4 != null && !loc4.isEmpty()) {
            dest.add(loc4);
        }
        if(loc5 != null && !loc5.isEmpty()) {
            dest.add(loc5);
        }


        // write the result to response
        PrintWriter out;
        String output;
        try {
            out = response.getWriter();
            try {
                output = new Gson().toJson(mManager.getOptimalPath(dest));
                out.write(output);
            } catch (final LocationException e) {
                // TODO Auto-generated catch block
                out.write(e.getMessage());
            }
        } catch (final IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final boolean ajax = "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));

        if (ajax) {
            // Handle ajax (JSON or XML) response.
            final PrintWriter out = response.getWriter();
            System.out.println("Handled with AJAX");
        } else {
            // Handle regular (JSP) response.
        }
        doGet(request, response);
    }

}
