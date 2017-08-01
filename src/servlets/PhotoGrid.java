/*
PhotoGrid.java [Servlet]
Generates dynamic code for displaying groups of photos in a view frame
NOTE: The servlet generates three different cases of dynamic code, depending if
      the request came from an anonymous unregistered user (generates all public
      photos), from within a user profile (generates all photos of the profile's
      owner that is visible and accessible to the current user), or from within
      the logged in home page, which in case also takes into account tags.

      The requestor relies heavily on hidden inputs. Do not execute the servlet
      outside of it's proper POST method roots.
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

@WebServlet(name = "PhotoGrid")
public class PhotoGrid extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

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
        
        
        
        // If there is no logged in user, return all public photos
        if (request.getSession().getAttribute("currentUser") == null) {
                	
                	out.println("<div class='container'>");
                	out.println("<div class='album-gallery'>");
                	out.println("<div id='ag-container'>");
                	out.println("<div id='album-container' class='grid grid--type-c'>");
                	out.println("<div class ='grid__sizer'>"); out.println("</div>");        	
                	
                	// Print photo grid
                    for (Photo p : ((ArrayList<Photo>) request.getSession().getAttribute("publicPhotos"))) {            	
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
                    RequestDispatcher rs = request.getRequestDispatcher("public_nologin.jsp");
                    rs.include(request, response);
           }
        
        if (request.getSession().getAttribute("requestor") != null) {
        	System.out.println(request.getSession().getAttribute("requestor"));
        	// If photogrid was called from login
            if (request.getSession().getAttribute("requestor").equals("login")) {
            	
            	out.println("<div class='container'>");
            	out.println("<div class='album-gallery'>");
            	out.println("<div id='ag-container'>");
            	out.println("<div id='album-container' class='grid grid--type-c'>");
            	out.println("<div class ='grid__sizer'>"); out.println("</div>");        	
            	
            	// Print photo grid
                for (Photo p : ((ArrayList<Photo>) request.getSession().getAttribute("publicPhotos"))) {            	
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
                RequestDispatcher rs = request.getRequestDispatcher("public.jsp");
                rs.include(request, response);
            }
            
         // Else if photogrid was called from own profile
            else if (request.getSession().getAttribute("requestor").equals("ownprofile")) {
            	
                String profileOwner = ((User) request.getSession().getAttribute("currentUser")).getUsername();

                // Fetch the photo with names equal to the requestor hidden parameter
                ArrayList<Photo> fetchedPhotos = new ArrayList<>();
                for (Photo p : ((ArrayList<Photo>) request.getSession().getAttribute("publicPhotos")))
                    if (p.getOwnerUsername().equals(profileOwner))
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
                out.println("</div>");
                
             // Dispatch the request
                request.getSession().setAttribute("requestor", "");
                RequestDispatcher rs = request.getRequestDispatcher("profile.jsp");
                rs.include(request, response);
            }
            
            

            // Else if photogrid was called from the user profile
            else if (request.getSession().getAttribute("requestor").equals("profile")) {
            	
            	String profileOwner = request.getParameter("profileOwner");

                // Fetch the photo with names equal to the requestor hidden parameter
                ArrayList<Photo> fetchedPhotos = new ArrayList<>();
                for (Photo p : ((ArrayList<Photo>) request.getSession().getAttribute("publicPhotos")))
                    if (p.getOwnerUsername().equals(profileOwner))
                        fetchedPhotos.add(p);

                for (Photo p : ((ArrayList<Photo>) request.getSession().getAttribute("privatePhotos"))) {
                    if (p.getOwnerUsername().equals(((User) request.getSession().getAttribute("currentUser")).getUsername()))
                        fetchedPhotos.add(p);
                    else {
                        if (p.getOwnerUsername().equals(profileOwner)) {
                            for (String shares : p.getSharedWith())
                                if (shares.equals(((User) request.getSession().getAttribute("currentUser")).getUsername()))
                                    fetchedPhotos.add(p);
                        }
                    }
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
                out.println("</div>");
                
             // Dispatch the request
                request.getSession().setAttribute("requestor", "");
                RequestDispatcher rs = request.getRequestDispatcher("profile.jsp");
                rs.include(request, response);

            }
        }
        
        if (request.getParameter("requestor") != null ) {
        	// Else if photogrid was called to see Shared
            if (request.getParameter("requestor").equals("shared")) {

                // Fetch the photo with names equal to the requestor hidden parameter
                ArrayList<Photo> fetchedPhotos = new ArrayList<>();

                for (Photo p : ((ArrayList<Photo>) request.getSession().getAttribute("privatePhotos"))) {
                    for (String shares : p.getSharedWith())
                    	if (shares.equals(((User) request.getSession().getAttribute("currentUser")).getUsername()))
                    		fetchedPhotos.add(p);
                }
            	
            	out.println("<div class='container'>");
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
    	        RequestDispatcher rs = request.getRequestDispatcher("shared.jsp");
    	        rs.include(request, response);
            }
            
            // Else if photogrid was called from login
            if (request.getParameter("requestor").equals("login")) {
            	
            	out.println("<div class='container'>");
            	out.println("<div class='album-gallery'>");
            	out.println("<div id='ag-container'>");
            	out.println("<div id='album-container' class='grid grid--type-c'>");
            	out.println("<div class ='grid__sizer'>"); out.println("</div>");        	
            	
            	// Print photo grid
                for (Photo p : ((ArrayList<Photo>) request.getSession().getAttribute("publicPhotos"))) {            	
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
                RequestDispatcher rs = request.getRequestDispatcher("public.jsp");
                rs.include(request, response);
            }

            
            // Else if called to search
            if (request.getParameter("requestor").equals("search")) {

                // Check if the user specified tags
                String tag = request.getParameter("tags");
                ArrayList<Photo> fetchedPhotos = new ArrayList<>();

                // If tags exist, use them. Otherwise, don't.
                if (tag.length() == 0) {
                    // Get only photos regardless of tags
                    for (Photo p : ((ArrayList<Photo>) request.getSession().getAttribute("publicPhotos")))
                        fetchedPhotos.add(p);

                    if (request.getSession().getAttribute("currentUser") != null) {
                    	// Fetch if you're the owner or you're on the shared list
                        for (Photo p : ((ArrayList<Photo>) request.getSession().getAttribute("privatePhotos"))) {
                            if (p.getOwnerUsername().equals(((User) request.getSession().getAttribute("currentUser")).getUsername()))
                                fetchedPhotos.add(p);
                            for (String shares : p.getSharedWith())
                                if (shares.equals(((User) request.getSession().getAttribute("currentUser")).getUsername()))
                                    fetchedPhotos.add(p);
                        }
                    }

                } else {
                    // Fetch all public photos that match
                    for (Photo p : ((ArrayList<Photo>) request.getSession().getAttribute("publicPhotos")))
                        for (String userTag : p.getTags())
                            if (userTag.equals(tag))
                                fetchedPhotos.add(p);

                    
                    if (request.getSession().getAttribute("currentUser") != null) { 
                    	// Fetch if you're the owner or in the shared list and photo matches tags
                        ArrayList<Photo> intermediary = new ArrayList<>();
                        for (Photo p : ((ArrayList<Photo>) request.getSession().getAttribute("privatePhotos"))) {
                            if (p.getOwnerUsername().equals(((User) request.getSession().getAttribute("currentUser")).getUsername()))
                                intermediary.add(p);

                            for (String shares : p.getSharedWith())
                                if (shares.equals(((User) request.getSession().getAttribute("currentUser")).getUsername()))
                                    intermediary.add(p);
                        }
                        for (Photo p : intermediary)
                            for (String userTag : p.getTags())
                                if (userTag.equals(tag))
                                    fetchedPhotos.add(p);
                    }
                }
                
                out.println("<div class='container'>");
            	out.println("<div class='album-gallery'>");
            	out.println("<p><i>Filtered by tag: #" + tag + "</i></p>");
            	out.println("<div id='ag-container'>");
            	out.println("<div id='album-container' class='grid grid--type-c'>");
            	out.println("<div class ='grid__sizer'>"); out.println("</div>");        	
            	
            	// Print photo grid
                for (Photo p : ((ArrayList<Photo>) request.getSession().getAttribute("publicPhotos"))) {            	
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
                
                
                if (request.getSession().getAttribute("currentUser") == null) {
                	// Dispatch the request
                    RequestDispatcher rs = request.getRequestDispatcher("search_nologin.jsp");
                    rs.include(request, response);
                } else {
                	RequestDispatcher rs = request.getRequestDispatcher("search.jsp");
                    rs.include(request, response);
                }
            }
        }
        
     	
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
