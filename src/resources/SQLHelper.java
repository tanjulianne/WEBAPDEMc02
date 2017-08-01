/*
SQLHelper.java [Helper Class]
Provides various static methods to easily reference SQL calls to the database.
NOTE: The database schema is named "webapde" with four tables, "users," "photos," "tags," and "shares."
      Please make sure your database is set accordingly. Port 3306 is used as default MySQL port number
Written by Jan Christian Blaise Cruz
 */

package resources;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class SQLHelper {

    // Queries the database and returns a result set
    public static ResultSet queryDatabase(String query) {
        ResultSet rs = null;
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/webapde?user=root&password=root");
            Statement stmt = (Statement) conn.createStatement();
            stmt.executeQuery(query);
            rs = stmt.getResultSet();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return rs;
    }

    // Updates a database with new rows.
    public static void updateDatabase(String query) {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/webapde?user=root&password=root");
            Statement stmt = (Statement) conn.createStatement();
            stmt.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Returns an ArrayList of Users from the database
    public static ArrayList<User> queryUserlist() {
        ArrayList<User> userList = new ArrayList<>();
        try {
            ResultSet rs = queryDatabase("SELECT username, password, description FROM users");
            while (rs.next()) {
                String dbDescription = rs.getString("description");
                String dbPassword = rs.getString("password");
                String dbUsername = rs.getString("username");

                userList.add(new User(dbUsername, dbPassword, dbDescription));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userList;
    }

    // Returns an ArrayList of photos from the database depending on the privacy set
    public static ArrayList<Photo> queryPhotolist(String privacy) {
        ArrayList<Photo> photoList = new ArrayList<>();
        try {
            // Pull the base photos
            ResultSet rs = queryDatabase("SELECT title, owner, privacy, description, file FROM photos WHERE privacy='" + privacy + "';");
            while (rs.next()) {
                String dbTitle = rs.getString("title");
                String dbOwner = rs.getString("owner");
                String dbDescription = rs.getString("description");
                String dbPrivacy = rs.getString("privacy");
                String dbFilePath = rs.getString("file");

                photoList.add(new Photo(dbPrivacy, dbTitle, dbDescription, dbOwner, new ArrayList<>(), dbFilePath));
            }

            // Add all tags associated
            ResultSet tagrs = queryDatabase("SELECT title, owner, tag FROM tags");
            while (tagrs.next()) {
                String tagTitle = tagrs.getString("title");
                String tagOwner = tagrs.getString("owner");
                String tagTag = tagrs.getString("tag");
                for (Photo p : photoList) {
                    if (p.getTitle().equals(tagTitle) && p.getOwnerUsername().equals(tagOwner))
                        p.getTags().add(tagTag);
                }
            }

            // Add all shares associated
            ResultSet shares = queryDatabase("SELECT title, owner, sharedTo FROM shares");
            while (shares.next()) {
                String shareTitle = shares.getString("title");
                String shareOwner = shares.getString("owner");
                String shareForwardee = shares.getString("sharedTo");
                for (Photo p : photoList) {
                    if (p.getTitle().equals(shareTitle) && p.getOwnerUsername().equals(shareOwner))
                        p.getSharedWith().add(shareForwardee);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return photoList;
    }

    // Fetches a single photo from the database
    // Slow function. Waits for two queryPhotoList functions to finish.
    public static Photo fetchPhoto(String title) {
        ArrayList<Photo> publics = queryPhotolist("Public");
        ArrayList<Photo> privates = queryPhotolist("Private");
        for (Photo p : publics)
            if (p.getTitle().equals(title))
                return p;
        for (Photo p : privates)
            if (p.getTitle().equals(title))
                return p;
        return null;
    }


}
