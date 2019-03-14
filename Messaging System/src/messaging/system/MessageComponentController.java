package messaging.system;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Main controller for the MessageComponent.fxml
 * Main use is to control each message box
 */
public class MessageComponentController{

    /**
     * Denotes display name of the sender
     */
    @FXML
    private Text sender = new Text();
    /**
     * Text of the message
     */
    @FXML
    private Text message = new Text();


    /**
     * Set the sender.
     *
     * @param displayName the display name of the sender
     */
    public void setSender(String displayName){
        sender.setText(displayName);
    }

    /**
     * Set the body of the message.
     *
     * @param text the message body
     */
    public void setBody(String text){
        message.setText(text);
    }
}
