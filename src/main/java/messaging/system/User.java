package messaging.system;

import java.io.Serializable;

/**
 * Contains information associated with each user
 */
public class User implements Serializable {
    private String userID;
    private String displayName;

    /**
     * Instantiates a new User.
     *
     * @param userID      the user id
     * @param displayName the display name
     */
    public User(String userID, String displayName) {
        this.userID = userID;
        this.displayName = displayName;
    }

    /**
     * Gets user id.
     *
     * @return the user id
     */
    public String getUserID() {
        return userID;
    }

    /**
     * Sets user id.
     *
     * @param userID the user id
     */
    public void setUserID(String userID) {
        this.userID = userID;
    }

    /**
     * Gets display name.
     *
     * @return the display name
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Sets display name.
     *
     * @param displayName the display name
     */
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }




}
