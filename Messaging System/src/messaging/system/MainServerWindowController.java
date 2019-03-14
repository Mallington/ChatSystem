package messaging.system;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class MainServerWindowController implements Initializable, ServerUserInterface {
    @FXML
    private Text port;

    @FXML
    private Text amountOfUsers;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @Override
    public void displayPort(int portNumber) {
        Platform.runLater(()->{
            port.setText(portNumber+"");
        });
    }

    @Override
    public void displaysUserPopulation(int population) {
        Platform.runLater(()->{
            amountOfUsers.setText(population+"");
        });
    }

    @Override
    public void printConsole(String consoleMessage) {

    }

    @Override
    public void displayError(String title, String body) {

    }
}
