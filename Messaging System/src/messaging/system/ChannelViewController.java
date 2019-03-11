package messaging.system;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * This controls the main channel view, it is responsible for displaying the messages of each ChatRoom to
 * the user.
 */
public class ChannelViewController implements Initializable {

    /**
     * List of all the Message Components.
     */
    @FXML
    private ListView<MessageComponent> messageList = new ListView<MessageComponent>();
    /**
     * Contains a pointer to the data base.
     */
    private Data dataBase = null;

    /**
     * Customised list cell controller which enables generic components to be added in a list
     */
    private CustomListCellController<MessageComponent> messageController;

    /**
     * Contains a backup of all of the message components added to the messageController
     */
    private List<MessageComponent> messageBoxes = new ArrayList<MessageComponent>();

    /**
     * Initialises main controller
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        init();
    }

    /**
     * Sets data base.
     *
     * @param dataBase the data base
     */
    public void setDataBase(Data dataBase) {
        this.dataBase = dataBase;
    }

    /**
     * Initialises the message list controller
     */
    private void init(){
        messageController = new CustomListCellController<MessageComponent>(messageList);
    }

    /**
     * @param message Message to be checked against list
     *
     * If a message already exists, the the associated component with its controller is returned
     * @return Message
     */
    private MessageComponent getComponent(Message message){
        for(MessageComponent comp : messageBoxes) if(comp.getMessageID().equals(message.getMessageID())) return comp;
        return null;
    }

    /**
     * Either adds or updates an existing message according to the contents if message.
     *
     * @param message to be added or updated
     */
    public void updateMessage(Message message){
            try {
            MessageComponent messageComp;
            if((messageComp = getComponent(message)) != null) {
                 messageComp.getController().setBody(message.getBody());
            }
            else{
                boolean ownMessage = message.getSenderID().equals(Constants.getUserId());
                String displayName = (ownMessage)? "You:" : (dataBase !=null) ?(dataBase.getUserByID(message.getSenderID()).getDisplayName()+":") : "User Not found:";
                messageComp = new MessageComponent(displayName, message.getBody(), message.getMessageID(), ownMessage);
                messageBoxes.add(messageComp);
                //messageList.getChildren().add(messageComp.getNode());
                messageController.add(messageComp);
            }

            } catch (IOException e) {
                    System.out.println("Failed to obtain message controller");
            }

    }

}
