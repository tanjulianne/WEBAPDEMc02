/*
User.java [Resource Class]
Represents a user object when instantiated.
Note: all attributes have provided accessor and mutator functions.
Written by Jan Christian Blaise Cruz
 */

package resources;

public class User {
    // Fields
    private String username;
    private String password;
    private String description;

    // Constructors
    public User(String username, String password, String description) {
        this.setUsername(username);
        this.setPassword(password);
        this.setDescription(description);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
