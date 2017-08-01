/*
Photo.java [Resource Class]
Represents a photo object when instantiated.
Note: all attributes have provided accessor and mutator functions.
Written by Jan Christian Blaise Cruz
 */

package resources;

import java.util.ArrayList;

public class Photo {
    private String privacy;                 // Privacy setting. Private or Public String.
    private String title;                   // Title of the image
    private String ownerUsername;           // Username of the owner
    private String description;             // Description of the photo
    private ArrayList<String> tags;         // List of tags (Strings)
    private ArrayList<String> sharedWith;   // List of usernames that photo is shared with. Empty if privacy is "Public"
    private String filename;                // Full path to the file, set from user root
    private String localPath;               // Local path of the file, set to data/filename.ext

    // Constructor
    public Photo(String privacy, String title, String description, String ownerUsername, ArrayList<String> tags, String filename) {
        this.setPrivacy(privacy);
        this.setTitle(title);
        this.setOwnerUsername(ownerUsername);
        this.setTags(tags);
        this.setFilename(filename);
        this.setSharedWith(new ArrayList<>());
        this.setDescription(description);
        this.setLocalPath(filename.split("/WebContent/")[1]);  // The localpath is set automatically
    }

    public String getPrivacy() {
        return privacy;
    }

    public void setPrivacy(String privacy) {
        this.privacy = privacy;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOwnerUsername() {
        return ownerUsername;
    }

    public void setOwnerUsername(String ownerUsername) {
        this.ownerUsername = ownerUsername;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }

    public ArrayList<String> getSharedWith() {
        return sharedWith;
    }

    public void setSharedWith(ArrayList<String> sharedWith) {
        this.sharedWith = sharedWith;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getLocalPath() {
        return localPath;
    }

    public void setLocalPath(String localPath) {
        this.localPath = localPath;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
