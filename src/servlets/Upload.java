/*
Upload.java [Servlet]
Handles file uploading from within the home.jsp view. Saves the files in a local data
storage and copies their details to database storage.
NOTE: Modifies the user database. Please make sure your connections are set and are valid.
      DO NOT MODIFY ORDER OF IMPORTS. They have servlets to apache commons libraries have
      scope issues if imported out of order.

      BEFORE USING MAKE SURE THE fileDirectory STRING IS SET ACCORDING TO YOUR LOCAL
      SYSTEM FILEPATH. Set it to the data folder within the web-contents folder of this
      project. THIS IS VERY IMPORTANT.

      The servlet saves the files in two places. One in local storage, and one in the
      folders where tomcat places it's artifacts. When displaying images, the artifacts
      location is important. When saving, the local storage is important. Both are needed
      to make the servlet function. Please do not change them unless you know exactly how
      the File objects to multipart files work.

      PLEASE WAIT AFTER UPLOADING. There is a natural buffer time between uploading and
      seeing results flash on the screen. This is due to the database connection being faster
      than the filesystem directives.
Written by Jan Christian Blaise Cruz
 */

package servlets;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import resources.Photo;
import resources.SQLHelper;
import resources.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@WebServlet(name = "Upload")
@MultipartConfig
public class Upload extends HttpServlet {

    private static final String DATA_DIRECTORY = "/data";
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        // Get the variables for the photo
        String description = request.getParameter("description");
        String title = request.getParameter("title");
        String privacy = request.getParameter("privacy");
        String tagString = request.getParameter("tags");
        String[] tags = tagString.split("\\s*,\\s*");
        String owner = ((User) request.getSession().getAttribute("currentUser")).getUsername();
        Part filePart = request.getPart("file");

        // Create file streams
        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString(); // MSIE fix.
        String extension = fileName.split("\\.")[1];
        if (extension.equals("png") || extension.equals("jpg") || extension.equals("tiff")) {
        	InputStream fileContent = filePart.getInputStream();

            // Copy to tomcat artifacts directory (data copy of the local folder)
            DiskFileItemFactory factory = new DiskFileItemFactory();
            factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
            String uploadFolder = getServletContext().getRealPath("") + File.separator + DATA_DIRECTORY;
            ServletFileUpload upload = new ServletFileUpload(factory);

            // Parse the request to copy it to local storage
            try {
                // Parse the request through an iterator
                List items = upload.parseRequest(request);
                Iterator iter = items.iterator();
                while (iter.hasNext()) {
                    FileItem item = (FileItem) iter.next();

                    // If the file isn't a form field, treat it as a multipart file
                    if (!item.isFormField()) {
                        String nfileName = new File(item.getName()).getName();
                        String nfilePath = uploadFolder + File.separator + nfileName;
                        File uploadedFile = new File(nfilePath);
                        System.out.println(nfilePath);

                        // saves the file to upload directory
                        item.write(uploadedFile);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            // NOTE: CHANGE THE fileDirectory STRING TO YOUR SYSTEM'S RELATIVE PATH.
            //       fileDirectory MUST POINT TO THE DATA FOLDER WITHIN THE WEB-CONTENT FOLDER OF THIS PROJECT
            //       ADJUST THE STRING ACCORDING TO HOW YOUR OS WRITES PATHS
            // Declare path, create the file, copy.
            try {
                String fileDirectory = "C:/Users/acer/workspace/WEBAPDE_MC02/WebContent/data";
                String filePath = fileDirectory + "/" + fileName;
                File uploads = new File(fileDirectory);  // File directory object to point path
                File file = new File(uploads, fileName); // File object to copy to
                Files.copy(fileContent, file.toPath());  // Copy the actual object to the destination data folder

                // Update the database after copying the actual file (converted to byte stream)
                String sql = "INSERT INTO photos (title, owner, privacy, description, file) VALUES ('" + title + "', '" + owner + "', '" + privacy + "', '" + description + "', '" + filePath + "');";
                SQLHelper.updateDatabase(sql);
                for (String tag : tags) {
                    String sql2 = "INSERT INTO tags (title, owner, tag) VALUES ('" + title + "', '" + owner + "', '" + tag + "');";
                    SQLHelper.updateDatabase(sql2);
                }

                // Outputs
                out.println("<br><center><b><font color=\"green\">File was uploaded successfully!</font></b></center>");

                // Update the public photolist
                try {
                    ArrayList<Photo> publicPhotos = SQLHelper.queryPhotolist("public");
                    request.getSession().setAttribute("publicPhotos", publicPhotos);
                } catch (Exception e) {
                    e.printStackTrace();
                    out.println("Trouble fetching public photos...");
                }

                // Update the private photolist
                try {
                    ArrayList<Photo> privatePhotos = SQLHelper.queryPhotolist("private");
                    request.getSession().setAttribute("privatePhotos", privatePhotos);
                } catch (Exception e) {
                    e.printStackTrace();
                    out.println("Trouble fetching private photos...");
                }

            } catch (Exception e) {
                out.println("<br><center><b><font color=\"red\">File was not uploaded successfully!</font></b></center>");
                e.printStackTrace();
            }
        } else {
        	out.println("<br><center><b><font color=\"red\">Wrong file type!</font></b></center>");
        }
        

        // Redirects
        RequestDispatcher rs = request.getRequestDispatcher("upload.html");
        rs.include(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
