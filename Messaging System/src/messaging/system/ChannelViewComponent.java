package messaging.system;

public class ChannelViewComponent extends Resource<ChannelViewController> {

    /**
     * Upon initialisation it stores the resource and creates a new FXML loader
     *
     */
    private ChatRoom chatRoom = null;

    public ChannelViewComponent(ChatRoom room) {
        super("ChannelView.fxml");
        this.chatRoom = room;
    }

    public String getChannelID() {
        return chatRoom.getRoomID();
    }

    public void setChannelID(String channelID) {
        chatRoom.setRoomID(channelID);
    }

    public ChatRoom getChatRoom() {
        return chatRoom;
    }

    public void setChatRoom(ChatRoom chatRoom) {
        this.chatRoom = chatRoom;
    }
}
