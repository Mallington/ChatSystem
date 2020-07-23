package messaging.system;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.layout.HBox;

import java.io.IOException;

/**
 * High level interface for the MessageComponent.fxml
 * Allows a message to be displayed in a customised graphic
 */
public class MessageComponent extends Resource<MessageComponentController> {

    /**
     * States whether or not it is thew users own message
     * This boolean dictates whether or not a message should be left or right aligned
     */
    private boolean ownMessage = false;
    /**
     * ID of the message associated with the component
     */
    private String messageID;

    /**
     * Instantiates a new Message component.
     *
     * @param displayName the display name
     * @param messageText the message text
     * @param messageID   the message id
     * @param ownMessage  whether the component should be left or right aligned
     */
    public MessageComponent(String displayName, String messageText, String messageID, boolean ownMessage) {
        super("/MessageComponent.fxml");

        this.messageID = messageID;
        this.ownMessage = ownMessage;

        try {
            super.getController().setSender(displayName);
            super.getController().setBody(messageText);
        }
        catch(Exception e){
            System.out.println("Failed to load peopleOnline controller");
        }
    }

    /**
     * This overrides th getNode method to add the ability to align the message boxes
     * @return the node
     * @throws IOException
     */
    @Override
    public Parent getNode() throws IOException {
        HBox h = new HBox();
        if(ownMessage)  h.setAlignment(Pos.BASELINE_RIGHT);
        else  h.setAlignment(Pos.BASELINE_LEFT);
        h.getChildren().add(super.getNode());

        return h;
    }

    /**
     * Gets message id.
     *
     * @return the message id
     */
    public String getMessageID() {
        return messageID;
    }

    /**
     * Sets message id.
     *
     * @param messageID the message id
     */
    public void setMessageID(String messageID) {
        this.messageID = messageID;
    }
}
