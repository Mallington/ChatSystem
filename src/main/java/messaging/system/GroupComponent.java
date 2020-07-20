package messaging.system;

import java.io.IOException;

/**
 * This is a custom node displaying the amount of users in a particular chat room
 * and the name of the channel
 */
public class GroupComponent extends Resource<GroupComponentController> {
    /**
     * The specific channel ID associated with the component
     */
    private String channelID;

    /**
     * Instantiates a new Group component.
     *
     * @param groupName       the channel name
     * @param groupPopulation the channel population
     */
    public GroupComponent(String groupName, int groupPopulation) {
        super("GroupComponent.fxml");
        try {
            getController().setGroupName(groupName);
            getController().setNumberOfPeople(groupPopulation);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Gets channel id.
     *
     * @return the channel id
     */
    public String getChannelID() {
        return channelID;
    }

    /**
     * Sets channel id.
     *
     * @param channelID the channel id
     */
    public void setChannelID(String channelID) {
        this.channelID = channelID;
    }
}
