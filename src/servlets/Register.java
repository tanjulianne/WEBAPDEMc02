/*
Register.java [Servlet]
Handles registration requests from the index.jsp frontend via a POST method.
NOTE: modifies the user database. Please make sure your connections are set and are valid.
Written by Jan Christian Blaise Cruz
 */

package servlets;

import resources.SQLHelper;
import resources.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

@WebServlet(name = "Register")
public class Register extends HttpServlet {
	
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        // get the new parameters
        String newUsername = request.getParameter("username");
        String newPassword = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmpassword");
        String newDescription = request.getParameter("description");

        // Set the userList if none yet
        if (request.getSession().getAttribute("userList") == null) {
            ArrayList<User> userList;
            try {
                userList = SQLHelper.queryUserlist();
                request.getSession().setAttribute("userList", userList);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Check userlist if such person exists
        ArrayList<User> currentUsers = (ArrayList<User>) request.getSession().getAttribute("userList");
        boolean valid = true;
        for (User u : currentUsers) {
            if (u.getPassword().equals(newPassword) && u.getUsername().equals(newUsername))
                valid = false;
        }
        

        // Add to userlist if there exists no user and the passwords matched
        if (valid && newPassword.equals(confirmPassword)) {
            try {
                String sql = "INSERT INTO users (username, password, description) VALUES('" + newUsername + "', '" + newPassword + "', '" +newDescription + "');";
                SQLHelper.updateDatabase(sql);

                // Update the userlist in the session
                User temp = new User(newUsername, newPassword, newDescription);
                ArrayList<User> newUserlist = (ArrayList<User>) request.getSession().getAttribute("userList");
                newUserlist.add(temp);
                request.getSession().setAttribute("userList", newUserlist);

                // Return results
                out.println("<br><b><center><font color=\"green\">You have successfully registered! Login now.</font></center></b>");
                RequestDispatcher rs = request.getRequestDispatcher("select.jsp");
                rs.include(request, response);

            } catch (Exception e) {
                e.printStackTrace();

                // Return results
                out.println("<br><b><center><font color=\"red\">Unsuccessful registration!</font></center></b>");
                RequestDispatcher rs = request.getRequestDispatcher("register.jsp");
                rs.include(request, response);
            }
        } else {
        	// Return results
            out.println("<br><b><center><font color=\"red\">Error!</font></center></b>");
            RequestDispatcher rs = request.getRequestDispatcher("register.jsp");
            rs.include(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
