package messaging.system;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MainChatWindowController implements ClientUserInterface, Initializable {
    @FXML
    TextFlow consoleOutput = new TextFlow();
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        for(int i =0; i<50; i++)consoleOutput.getChildren().addAll(new Text("Console Output:\n"));
    }
    public void addChannel(){

    }

    public void addMember(){

    }

    @Override
    public void updateChatRoomList(List<ChatRoom> roomList) {

    }

    @Override
    public void update(Message oldMessage, Message newMessage) {

    }

    @Override
    public void update(User oldUser, User newUser) {

    }

    @Override
    public void printConsole(String consoleMessage) {

    }

    @Override
    public void displayError(String title, String body) {

    }
}
