package messaging.system;

import java.io.IOException;

/**
 * This is a custom node displaying a user's display name and UID
 */
public class UserComponent extends Resource<UserComponentController> {
    /**
     * The specific user ID associated with the component
     */
    private String userID;


    /**
     * Instantiates a new User component.
     *
     * @param displayName the display name
     * @param UID         the uid
     */
    public UserComponent(String displayName, String UID) {
        super("/UserComponent.fxml");
        try {
            getController().setDisplayName(displayName);
            getController().setUID(UID);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e){

        }

    }

    /**
     * Gets channel id.
     *
     * @return the channel id
     */
    public String getUserID() {
        return userID;
    }

    /**
     * Sets channel id.
     *
     * @param userID the channel id
     */
    public void setUserID(String userID) {
        this.userID = userID;
    }
}
