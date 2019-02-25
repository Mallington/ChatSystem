package messaging.system;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UpdateRequestContainer  implements Serializable {
    List<String> messagesToFetch;
    List<String> usersToFetch;

    public UpdateRequestContainer(List<String> messagesToFetch, List<String> usersToFetch) {
        this.messagesToFetch = messagesToFetch;
        this.usersToFetch = usersToFetch;
    }

    public List<String> getMessagesToFetch() {
        return messagesToFetch;
    }

    public List<String> getUsersToFetch() {
        return usersToFetch;
    }
}
