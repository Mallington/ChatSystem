package messaging.system;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ChannelViewController implements Initializable {
    @FXML
    private ListView<MessageComponent> messageList = new ListView<MessageComponent>();

    private Data dataBase = null;

    private CustomListCellController<MessageComponent> messageController;
    private List<MessageComponent> messageBoxes = new ArrayList<MessageComponent>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        init();
    }

    public void setDataBase(Data dataBase) {
        this.dataBase = dataBase;
    }
    private void init(){
        messageController = new CustomListCellController<MessageComponent>(messageList);
    }

    private MessageComponent getComponent(Message message){
        for(MessageComponent comp : messageBoxes) if(comp.getMessageID().equals(message.getMessageID())) return comp;
        return null;
    }

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
