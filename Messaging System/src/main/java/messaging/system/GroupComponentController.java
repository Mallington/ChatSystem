package messaging.system;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Associated controller for "GroupComponent.fxml"
 * Used to display the status of a channel, displaying data such as
 * the name and amount of users in the channel
 */
public class GroupComponentController{

    /**
     * Display name of the channel
     */
    @FXML
    private Text groupName = new Text();

    /**
     * Amount of people in the channel
     */
    @FXML
    private Text channelPopulation = new Text();

    /**
     * Sets the channel name.
     *
     * @param name the name to be set
     */
    public void setGroupName(String name){
        groupName.setText(name);
    }

    /**
     * Set number of people.
     *
     * @param numberOfPeople the number of people
     */
    public void setNumberOfPeople(int numberOfPeople){
        channelPopulation.setText(numberOfPeople+"");
    }
}

