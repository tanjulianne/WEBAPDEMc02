package servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import resources.SQLHelper;
import resources.User;

/**
 * Servlet implementation class RelogServlet
 */
@WebServlet("/RelogServlet")
public class RelogServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RelogServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		
		// get cookies
				Cookie[] cookies = request.getCookies();
				String username = null;
				// check is username cookie exists
				if(cookies!=null){
					for(int i = 0; i < cookies.length; i++){
						Cookie currentCookie = cookies[i];
						if(currentCookie.getName().equals("username")){
							username = currentCookie.getValue();
							currentCookie.setMaxAge(currentCookie.getMaxAge() + 60*60*24*21);
							currentCookie.setHttpOnly(true);
							response.addCookie(currentCookie);
						}
					}
				}
			
				// if exists
				if(username!=null){
					// use cookie value and set it as attr to session
					request.getSession().setAttribute("un", username);
					
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
			            if (u.getUsername().equals(username)) {
			                currentUser = u;
			            }
			        }
					
			        request.getSession().setAttribute("currentUser", currentUser);
			        request.getSession().setAttribute("requestor", "login");
					// go to success.jsp
					request.getRequestDispatcher("public.jsp")
							.forward(request, response);
				} else{
				// else
					// user had not visited website, or logged out
					// go to index.html
					response.sendRedirect("select.jsp");
				}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
