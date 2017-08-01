/*
Login.java [Servlet]
Handles login requests from the index.jsp frontend via a POST method.
NOTE: Sets the "currentUser" variable in session scope
Written by Jan Christian Blaise Cruz
 */

package servlets;

import resources.SQLHelper;
import resources.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

@WebServlet(name = "Login")
public class Login extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        // Get form parameters
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        boolean validUser = false;
        User currentUser = null;

        // Retrieves the userlist from the database
        if (request.getSession().getAttribute("userList") == null) {
            ArrayList<User> userList;
            try {
                userList = SQLHelper.queryUserlist();
                request.getSession().setAttribute("userList", userList);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Search for user
        for (User u : (ArrayList<User>) request.getSession().getAttribute("userList")) {
            if (u.getUsername().equals(username) && u.getPassword().equals(password)) {
                currentUser = u;
                validUser = true;
                Cookie cookie = new Cookie("username", username);
    			cookie.setMaxAge(cookie.getMaxAge() + 60*60*24*21);
    			response.addCookie(cookie);
            }
        }

        // Dispatch
        if (validUser) {
            request.getSession().setAttribute("currentUser", currentUser);
            request.getSession().setAttribute("requestor", "login");
            RequestDispatcher rs = request.getRequestDispatcher("PhotoGrid");
            rs.include(request, response);
        }
        else {
            out.println("<br><b><center><font color=\"red\">Password or Username is incorrect!</font></center></b>");
            RequestDispatcher rs = request.getRequestDispatcher("login.jsp");
            rs.include(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
