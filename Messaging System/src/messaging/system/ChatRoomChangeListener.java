package messaging.system;

public interface ChatRoomChangeListener {
     public void update(Message oldMessage, Message newMessage);
     public void update(User oldUser, User newUser);
}
