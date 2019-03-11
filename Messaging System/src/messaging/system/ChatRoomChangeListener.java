package messaging.system;

/**
 * Used to update changes, additions and deletions to the Data class, used to contain the messages and users
 */
public interface ChatRoomChangeListener {
     /**
      * Updates the changes to the messages
      *
      * @param oldMessage the old message
      * @param newMessage the new message
      */
     public void update(Message oldMessage, Message newMessage);

     /**
      * Updates the changes to the user itself
      *
      * @param oldUser the old user
      * @param newUser the new user
      */
     public void update(User oldUser, User newUser);
}
