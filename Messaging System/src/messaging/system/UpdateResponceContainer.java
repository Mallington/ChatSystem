package messaging.system;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UpdateResponceContainer implements Serializable {
    List<User> users;
    List<Message> messages;


    public UpdateResponceContainer(List<User> users, List<Message> messages) {
        this.users = users;
        this.messages = messages;
    }

    public List<User> getUsers() {
        return users;
    }

    public List<Message> getMessages() {
        return messages;
    }


}
