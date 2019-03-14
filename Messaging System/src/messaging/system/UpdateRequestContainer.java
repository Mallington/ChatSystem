package messaging.system;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Used to request an update on specific UIDs relating to user and message objects
 */
public class UpdateRequestContainer  implements Serializable {
    /**
     * The Messages to fetch.
     */
    private List<String> messagesToFetch;
    /**
     * The Users to fetch.
     */
    private List<String> usersToFetch;

    /**
     * Instantiates a new Update request container.
     *
     * @param messagesToFetch the messages to fetch
     * @param usersToFetch    the users to fetch
     */
    public UpdateRequestContainer(List<String> messagesToFetch, List<String> usersToFetch) {
        this.messagesToFetch = messagesToFetch;
        this.usersToFetch = usersToFetch;
        }

    /**
     * Gets messages to fetch.
     *
     * @return the messages to fetch
     */
    public List<String> getMessagesToFetch() {
        return messagesToFetch;
        }

    /**
     * Gets users to fetch.
     *
     * @return the users to fetch
     */
    public List<String> getUsersToFetch() {
        return usersToFetch;
        }
        }
