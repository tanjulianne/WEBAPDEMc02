/*
PhotoFrame.java [Servlet]
Generates dynamic code for displaying a single photo in its frame
NOTE: PhotoFrame works within a GET Method. This means all code that references
      PhotoFrame (and it's view jsp, photoframe.jsp) must be done using anchor
      tags and not forms that POST.

      The servlet takes one parameter (title -- corresponds to the displayed
      photo's title) via a GET method (e.g. localhost:8080/PhotoFrame?title=foo)
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

@WebServlet(name = "PhotoFrame")
public class PhotoFrame extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        // Query the image details
        String title = request.getParameter("title");
        Photo currentPhoto = SQLHelper.fetchPhoto(title);
        
        
        
        
        
        
                
        out.println("<div class='container'>");
        out.println("<div class='album-gallery'>");
        out.println("<div id='photo-container'>");
        out.println("<div id='photo-space'>");
        out.println("<center class='center'><img id='img' src='" + currentPhoto.getLocalPath() + "' id='" + currentPhoto.getTitle() + "' style = 'width:400px; height: auto;'></center>");
        out.println("</div>");
        
        out.println("<h4><center><i><span id='title'>" + title + "</span></i></center></h4>");
        out.println("<div><h5>Uploaded by: <a href='Profile?userprofile=" + currentPhoto.getOwnerUsername() + "'>@" + currentPhoto.getOwnerUsername() +"</span></a></h5></div>");
        out.println("<div><h5>Description: <span id='album'>" + currentPhoto.getDescription() + "</span></h5></div>");
  

        
        out.println("<div id='tagged'>");
        out.println("<h5 class='inline-tag'>Tags: " );
        for (String tag : currentPhoto.getTags()) {
            out.print("<a href='search.jsp' id ='tag-link'> <span id='tag'>#" + tag + "</span></a>,");
        }
        out.println("</h5>");
        if (request.getSession().getAttribute("currentUser") != null) {
        	if (((User) request.getSession().getAttribute("currentUser")).getUsername().equals(currentPhoto.getOwnerUsername())) {
                out.println("<form action='Tag' method='post'>" +
                        "Add a tag: <input type='text' name='tag' /><br />" +
                        "<input type='hidden' name='title' value='" + currentPhoto.getTitle() + "' />" +
                        "<input type='hidden' name='owner' value='" + currentPhoto.getOwnerUsername() + "' />" +
                        "<input type='submit' value='Add Tag' /><br />" +
                        "</form>");
            }
        }
            
        out.println("</div>");
        
        
        if (request.getSession().getAttribute("currentUser") != null) {
        	if (currentPhoto.getPrivacy().equals("Private")) {
        	    out.println("<div id='shared'>");
        	    out.println("<h5 class='inline-shared'>Shared with: " );
        	    for (String shares : currentPhoto.getSharedWith()) {
        	        out.print("<a href='Profile?userprofile=" + shares + "' <span id='share'>@" + shares + "</span></a>,");
        	    }
        	    out.println("</h5>");
                    if (((User) request.getSession().getAttribute("currentUser")).getUsername().equals(currentPhoto.getOwnerUsername())) {
                        out.println("<form action='Share' method='post'>" +
                                "Share to username: <input type='text' name='forwardee' /><br />" +
                                "<input type='hidden' name='title' value='" + currentPhoto.getTitle() + "' />" +
                                "<input type='hidden' name='owner' value='" + currentPhoto.getOwnerUsername() + "' />" +
                                "<input type='submit' value='Share' /><br />" +
                                "</form>");
                    }
                    out.println("</div>");
                }
        }
        
	    
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

        // Dispatch
        if (request.getSession().getAttribute("currentUser") == null) {
        	RequestDispatcher rs = request.getRequestDispatcher("photo_view_nologin.jsp");
            rs.include(request, response);
        } else {
        	RequestDispatcher rs = request.getRequestDispatcher("photo_view.jsp");
            rs.include(request, response);
        }
    }
}
