/*
Logout.java [Servlet]
Handles logout requests from the home.jsp frontend via a POST method.
NOTE: Sets the "currentUser" variable to null in session scope.
      Logout servlet redirects to index.jsp (It doesn't forward/include req/res!)
Written by Jan Christian Blaise Cruz
 */

package servlets;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "Logout")
public class Logout extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        request.getSession().setAttribute("currentUser", null);
        out.println("<b><font color=\"green\">You have successfully logged out.</font></b>");
        
        // kill session
     		request.getSession().invalidate();
     		
     		// kill cookie
     		Cookie[] cookies = request.getCookies();
     		if(cookies!=null){
     			for(int i = 0; i < cookies.length; i++){
     				Cookie currentCookie = cookies[i];
     				if(currentCookie.getName().equals("username")){
     					currentCookie.setMaxAge(0);
     					response.addCookie(currentCookie);
     				}
     			}
     		}

        response.sendRedirect("select.jsp");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}
