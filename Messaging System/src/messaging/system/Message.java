package messaging.system;

import java.io.Serializable;

/**
 * Message object to be sent as a payload via the Packet object
 */
public class Message implements Serializable {
    private String messageID = null;
    private String senderID = null;
    private String roomID = null;
    /**
     * The main text of the message
     */
    private String body = null;

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

    /**
     * Gets sender id.
     *
     * @return the sender id
     */
    public String getSenderID() {
        return senderID;
    }

    /**
     * Sets sender id.
     *
     * @param senderID the sender id
     */
    public void setSenderID(String senderID) {
        this.senderID = senderID;
    }

    /**
     * Gets room id.
     *
     * @return the room id
     */
    public String getRoomID() {
        return roomID;
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
     * Gets body.
     *
     * @return the body
     */
    public String getBody() {
        return body;
    }

    /**
     * Sets body.
     *
     * @param body the body
     */
    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String toString(){
        return "[Message ID: "+messageID+"]: "+senderID+" -> "+roomID;
    }
}
