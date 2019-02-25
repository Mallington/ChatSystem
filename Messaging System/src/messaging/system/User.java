package messaging.system;

import java.io.Serializable;

public class User implements Serializable {
    private String userID;
    private String displayName;

    public User(String userID, String displayName) {
        this.userID = userID;
        this.displayName = displayName;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }




}
