package messaging.system;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ChatRoom implements Serializable {
    private String roomID = null;
    private List<String> userIDS = new ArrayList<String>();
    private List<String> messageIDS = new ArrayList<String>();

    public ChatRoom(List<String> userI, List<String> messageIDS) {
        this.userIDS = userI;
        this.messageIDS = messageIDS;
    }

    public String getRoomID() {
        return roomID;
    }

    public void setRoomID(String roomID) {
        this.roomID = roomID;
    }

    public List<String> getUserIDS() {
        return userIDS;
    }

    public void setUserIDS(List<String> userIDS) {
        this.userIDS = userIDS;
    }

    public List<String> getMessageIDS() {
        return messageIDS;
    }

    public void setMessageIDS(List<String> messageIDS) {
        this.messageIDS = messageIDS;
    }
}
