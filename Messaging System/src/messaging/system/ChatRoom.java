package messaging.system;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Contains the IDs of the messages, and users associated with this room along with other
 * information.
 */
public class ChatRoom implements Serializable {
    /**
     * Unique ID for this room
     */
    private String roomID = null;

    /**
     *Name of room
     */
    private String name = "";
    /**
     *Contains the IDs of the users in this room
     */
    private List<String> userIDS = new ArrayList<String>();
    /**
     * Contains the IDs of the messages in this room
     */
    private List<String> messageIDS = new ArrayList<String>();

    /**
     * Instantiates a new Chat room.
     *
     * @param userIDS the user IDs
     * @param messageIDS the message IDs
     */
    public ChatRoom(List<String> userIDS, List<String> messageIDS) {
        this.userIDS = userIDS;
        this.messageIDS = messageIDS;
    }

    /**
     * Instantiates a new Chat room.
     *
     * @param roomID the room id
     */
    public ChatRoom(String roomID){
        this.userIDS = new ArrayList<String>();
        this.messageIDS = new ArrayList<String>();
        this.roomID = roomID;
    }

    /**
     * Gets the room id.
     *
     * @return the room id
     */
    public String getRoomID() {
        return roomID;
    }

    /**
     * Gets the name of the room.
     *
     * @return the name of the room
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the display name of the room
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets room id.
     *
     * @param roomID the room id
     */
    public void setRoomID(String roomID) {
        this.roomID = roomID;
    }

    /**
     * Gets user ids.
     *
     * @return the user ids
     */
    public List<String> getUserIDS() {
        return userIDS;
    }

    /**
     * Sets user ids.
     *
     * @param userIDS the user ids
     */
    public void setUserIDS(List<String> userIDS) {
        this.userIDS = userIDS;
    }

    /**
     * Gets message ids.
     *
     * @return the message ids
     */
    public List<String> getMessageIDS() {
        return messageIDS;
    }

    /**
     * Sets message ids.
     *
     * @param messageIDS the message ids
     */
    public void setMessageIDS(List<String> messageIDS) {
        this.messageIDS = messageIDS;
    }
}
