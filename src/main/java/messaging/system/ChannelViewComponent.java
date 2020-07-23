package messaging.system;


/**
 * This class provides an easy to use interface extending the use of the Resource class
 * which loads the GUI and presents the controller. All the user needs to do is instantiate
 * the class.
 */
public class ChannelViewComponent extends Resource<ChannelViewController> {

    /**
     * Upon initialisation it stores the resource and creates a new FXML loader
     *
     */
    private ChatRoom chatRoom = null;

    /**
     * Instantiates a new Channel view component.
     *
     * @param room the Chat Room instance that will be graphically represented
     */
    public ChannelViewComponent(ChatRoom room) {
        super("/ChannelView.fxml");
        this.chatRoom = room;
    }

    /**
     * Gets channel id.
     *
     * @return the channel id
     */
    public String getChannelID() {
        return chatRoom.getRoomID();
    }

    /**
     * Sets the channel id.
     *
     * @param channelID the channel id
     */
    public void setChannelID(String channelID) {
        chatRoom.setRoomID(channelID);
    }

    /**
     * Gets chat room instance.
     *
     * @return the chat room
     */
    public ChatRoom getChatRoom() {
        return chatRoom;
    }

    /**
     * Sets chat room instance.
     *
     * @param chatRoom the chat room
     */
    public void setChatRoom(ChatRoom chatRoom) {
        this.chatRoom = chatRoom;
    }
}
