package messaging.system;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Sent in a response packet, providing updates to users an messages
 */
public class UpdateResponceContainer implements Serializable {
    /**
     * Updated Users
     */
    private List<User> users;
    /**
     * Updated Messages
     */
    private List<Message> messages;


    /**
     * Instantiates a new Update response container.
     *
     * @param users    the users
     * @param messages the messages
     */
    public UpdateResponceContainer(List<User> users, List<Message> messages) {
        this.users = users;
        this.messages = messages;
    }

    /**
     * Gets the updated users objects.
     *
     * @return the users
     */
    public List<User> getUsers() {
        return users;
    }

    /**
     * Gets the updated message objects.
     *
     * @return the messages
     */
    public List<Message> getMessages() {
        return messages;
    }


}
