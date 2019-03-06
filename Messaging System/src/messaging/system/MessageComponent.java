package messaging.system;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.layout.HBox;

import java.io.IOException;

public class MessageComponent extends Resource<MessageComponentController> {
    /**
     * Upon initialisation it stores the resource and creates a new FXML loader
     */

    private boolean ownMessage = false;
    private String messageID;

    public MessageComponent(String displayName, String messageText, String messageID, boolean ownMessage) {
        super("MessageComponent.fxml");

        this.messageID = messageID;
        this.ownMessage = ownMessage;

        try {
            super.getController().setSender(displayName);
            super.getController().setBody(messageText);
        }
        catch(Exception e){
            System.out.println("Failed to load message controller");
        }
    }

    @Override
    public Parent getNode() throws IOException {
        HBox h = new HBox();
        if(ownMessage)  h.setAlignment(Pos.BASELINE_RIGHT);
        else  h.setAlignment(Pos.BASELINE_LEFT);
        h.getChildren().add(super.getNode());

        return h;
    }

    public String getMessageID() {
        return messageID;
    }

    public void setMessageID(String messageID) {
        this.messageID = messageID;
    }
}
