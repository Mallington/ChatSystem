package messaging.system;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class MessageComponentController  implements Initializable {

    @FXML
    Text sender = new Text();

    @FXML
    Text message = new Text();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    public void setSender(String displayName){
        sender.setText(displayName);
    }

    public void setBody(String text){
        message.setText(text);
    }
}
