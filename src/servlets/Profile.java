/*
Profile.java [Servlet]
Generates code for displaying a user's profile page.
NOTE: Profile works within a GET Method. This means all code that references
      Profile (and it's view jsp, profile.jsp) must be done using anchor
      tags and not forms that POST.

      The servlet takes one parameter (userprofile -- corresponds to the displayed
      user's username) via a GET method (e.g. localhost:8080/Profile?userprofile=foo)
Written by Jan Christian Blaise Cruz
 */

package servlets;

import resources.Photo;
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

@WebServlet(name = "Profile")
public class Profile extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        User profileOwner = null;
        ArrayList<User> userList = SQLHelper.queryUserlist();
        	// Fetch the data of the user from the database
            String userprofile = request.getParameter("userprofile");
            for (User u : userList)
                if (u.getUsername().equals(userprofile))
                    profileOwner = u;
            
        String profileOwnerName = profileOwner.getUsername();
        
     // Refresh all public and private photos
        try {
            ArrayList<Photo> publicPhotos = SQLHelper.queryPhotolist("public");
            request.getSession().setAttribute("publicPhotos", publicPhotos);
            ArrayList<Photo> privatePhotos = SQLHelper.queryPhotolist("private");
            request.getSession().setAttribute("privatePhotos", privatePhotos);
        } catch (Exception e) {
            e.printStackTrace();
            out.println("Trouble fetching all photos...");
        }
        
        
        out.println("<div class='giant-container'>");
        out.println("<div class='cover-container'>");
        out.println("<div class='cover-photo'>");
        out.println("<img id='cover' src='photos/cover.jpg'/>");
        out.println("<div class='profile-container'>");
        out.println("<div class='profile-details'>");
        out.println("<img id='profile-pic' src='photos/icon.jpg'/>");
        out.println("<div class='profile-text'>");
        out.println("<p class='profile-name'><span id='name'>@" + profileOwner.getUsername() + "</span></p>");
        out.println("<p class='profile-email'><span id='descrip'>" + profileOwner.getDescription() + "</span></p>");
        out.println("</div>");
        out.println("</div>");
        out.println("</div>");
        out.println("</div>");
        out.println("</div>");

        // Dispatch
        if (request.getSession().getAttribute("currentUser") == null) {   
            	

                // Fetch the photo with names equal to the requestor hidden parameter
                ArrayList<Photo> fetchedPhotos = new ArrayList<>();
                for (Photo p : ((ArrayList<Photo>) request.getSession().getAttribute("publicPhotos")))
                    if (p.getOwnerUsername().equals(profileOwnerName))
                        fetchedPhotos.add(p);

            	
            	out.println("<div class='container'>");
            	out.println("<div class='gallery-container'>");
            	out.println("<div class='album-gallery'>");
            	out.println("<div id='ag-container'>");
            	out.println("<div id='album-container' class='grid grid--type-c'>");
            	out.println("<div class ='grid__sizer'>"); out.println("</div>");        	
            	
            	// Print photo grid
                for (Photo p : fetchedPhotos) {            	
    				out.println("<div class='grid__item'>");
    				out.println("<a class='grid__link lb-action' href='PhotoFrame?title=" + p.getTitle() + "'>");
    				out.println("<img class='grid__img' src='" + p.getLocalPath() + "' />");
    				out.println("</a>");
    				out.println("</div>");
    				
                    /*out.println("<a href='PhotoFrame?title=" + p.getTitle() + "'>" +
                            "<img src='" + p.getLocalPath() + "' length='250px' width='250px' id='" + p.getTitle() + "' />" +
                            "</a>");
                            */
                }
                
                out.println("</div>");
                out.println("</div>");
                out.println("</div>");
              	
                out.println("<div class='more-photos'>");  
                out.println("Load more photos");  
                out.println("</div>");        
                      
                out.println("<hr id='footer-border'>"); 
                    
                out.println("<footer>");  
                out.println("<p>&copy; Blaise Cruz, Giselle Nodalo, Julianne Tan</p>");  
                out.println("</footer>");  
                  
                out.println("<script src='js/imagesloaded.pkgd.min.js'></script>");
                out.println("<script src='js/masonry.pkgd.min.js'></script>");
                out.println("<script src='js/anime.min.js'></script>");
                out.println("</div>");
                
    	         // Dispatch the request
    	        RequestDispatcher rs = request.getRequestDispatcher("profile_nologin.jsp");
    	        rs.include(request, response);

        } else {
                // Fetch the photo with names equal to the requestor hidden parameter
                ArrayList<Photo> fetchedPhotos = new ArrayList<>();
                for (Photo p : ((ArrayList<Photo>) request.getSession().getAttribute("publicPhotos")))
                    if (p.getOwnerUsername().equals(profileOwnerName))
                        fetchedPhotos.add(p);

                for (Photo p : ((ArrayList<Photo>) request.getSession().getAttribute("privatePhotos"))) {
                    if (p.getOwnerUsername().equals(((User) request.getSession().getAttribute("currentUser")).getUsername()))
                        fetchedPhotos.add(p);
                }
            	
            	out.println("<div class='container'>");
            	out.println("<div class='gallery-container'>");
            	out.println("<div class='album-gallery'>");
            	out.println("<div id='ag-container'>");
            	out.println("<div id='album-container' class='grid grid--type-c'>");
            	out.println("<div class ='grid__sizer'>"); out.println("</div>");        	
            	
            	// Print photo grid
                for (Photo p : fetchedPhotos) {            	
    				out.println("<div class='grid__item'>");
    				out.println("<a class='grid__link lb-action' href='PhotoFrame?title=" + p.getTitle() + "'>");
    				out.println("<img class='grid__img' src='" + p.getLocalPath() + "' />");
    				out.println("</a>");
    				out.println("</div>");
    				
                    /*out.println("<a href='PhotoFrame?title=" + p.getTitle() + "'>" +
                            "<img src='" + p.getLocalPath() + "' length='250px' width='250px' id='" + p.getTitle() + "' />" +
                            "</a>");
                            */
                }
                
                out.println("</div>");
                out.println("</div>");
                out.println("</div>");
              	
                out.println("<div class='more-photos'>");  
                out.println("Load more photos");  
                out.println("</div>");        
                      
                out.println("<hr id='footer-border'>"); 
                    
                out.println("<footer>");  
                out.println("<p>&copy; Blaise Cruz, Giselle Nodalo, Julianne Tan</p>");  
                out.println("</footer>");  
                  
                out.println("<script src='js/imagesloaded.pkgd.min.js'></script>");
                out.println("<script src='js/masonry.pkgd.min.js'></script>");
                out.println("<script src='js/anime.min.js'></script>");
                out.println("</div>");
                
    	         // Dispatch the request
                request.getSession().setAttribute("requestor", "");
    	        RequestDispatcher rs = request.getRequestDispatcher("profile.jsp");
    	        rs.include(request, response);
        } 
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        User profileOwner = null;
        ArrayList<User> userList = SQLHelper.queryUserlist();
        if (request.getParameter("requestor").equals("ownprofile")) {
            for (User u : userList)
                if (u.getUsername().equals(((User) request.getSession().getAttribute("currentUser")).getUsername()))
                    profileOwner = u;
        } else {
        	// Fetch the data of the user from the database
            String userprofile = request.getParameter("userprofile");
            for (User u : userList)
                if (u.getUsername().equals(userprofile))
                    profileOwner = u;
        }
        
        
        out.println("<div class='giant-container'>");
        out.println("<div class='cover-container'>");
        out.println("<div class='cover-photo'>");
        out.println("<img id='cover' src='photos/cover.jpg'/>");
        out.println("<div class='profile-container'>");
        out.println("<div class='profile-details'>");
        out.println("<img id='profile-pic' src='photos/icon.jpg'/>");
        out.println("<div class='profile-text'>");
        out.println("<p class='profile-name'><span id='name'>@" + profileOwner.getUsername() + "</span></p>");
        out.println("<p class='profile-email'><span id='descrip'>" + profileOwner.getDescription() + "</span></p>");
        out.println("</div>");
        out.println("</div>");
        out.println("</div>");
        out.println("</div>");
        out.println("</div>");

        // Dispatch
        if (request.getSession().getAttribute("currentUser") == null) {
        	request.getSession().setAttribute("requestor", "profilenologin");
            RequestDispatcher rs = request.getRequestDispatcher("PhotoGrid");
            rs.include(request, response);
        } else if (request.getParameter("requestor").equals("ownprofile")) {
        	request.getSession().setAttribute("requestor", "ownprofile");
            RequestDispatcher rs = request.getRequestDispatcher("PhotoGrid");
            rs.include(request, response);
        } else {
        	request.getSession().setAttribute("requestor", "profile");
            RequestDispatcher rs = request.getRequestDispatcher("PhotoGrid");
            rs.include(request, response);
        }
    }
}
