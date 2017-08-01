/*
Share.java [Servlet]
Handles sharing requests from within a Photo Frame
NOTE: modifies the user database. Please make sure your connections are set and are valid.
      Share servlet uses redirects instead of includes/forwards due to it's client JSP
      (PhotoFrame servlet and it's view, photoframe.jsp) using GET methods instead of POST.

      Do not try to use POST methods or derivatives (includes and forwards) because the
      Photo Frame views and servlets will not be able to catch them!
Written by Jan Christian Blaise Cruz
 */

package servlets;

import resources.SQLHelper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "Share")
public class Share extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        // Get details
        String forwardee = request.getParameter("forwardee");
        String owner = request.getParameter("owner");
        String title = request.getParameter("title");
        String shareMessage = "";

        // Update the database
        try {
            String sql = "INSERT INTO shares (title, owner, sharedTo) VALUES ('" + title + "', '" + owner + "', '" + forwardee + "');";
            SQLHelper.updateDatabase(sql);
            shareMessage = "<p><font color='green'>Successfully shared to " + forwardee + "!</font></p>";
        } catch (Exception e) {
            e.printStackTrace();

        }

        // Redirect
        response.sendRedirect("PhotoFrame?title=" + title + "&shareMessage=" + shareMessage);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
