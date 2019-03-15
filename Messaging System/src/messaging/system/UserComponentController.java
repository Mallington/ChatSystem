package messaging.system;

import javafx.fxml.FXML;
import javafx.scene.text.Text;

/**
 * Associated controller for "UserComponent.fxml"
 * Used to display the presence of a user in the right sidebar
 */
public class UserComponentController{

    /**
     * Display name of the user
     */
    @FXML
    private Text displayName = new Text();

    /**
     * The UID of the user
     */
    @FXML
    private Text UID = new Text();

    /**
     * Sets the channel name.
     *
     * @param name the name to be set
     */
    public void setDisplayName(String name){
        displayName.setText(name);
    }

    public void setUID(String UIDText){
        UID.setText(UIDText);
    }
}

