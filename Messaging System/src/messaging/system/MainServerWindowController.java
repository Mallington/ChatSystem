package messaging.system;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.net.URL;
import java.util.ResourceBundle;

public class MainServerWindowController implements Initializable, ServerUserInterface {
    @FXML
    private Text port;

    @FXML
    private Text amountOfUsers;

    @FXML
    private TextFlow console;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            console.setPrefWidth(1500);
            console.setPrefHeight(1500);
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void displayPort(int portNumber) {
        Platform.runLater(()->{
            port.setText(portNumber+"");
        });
    }

    @Override
    public void displayUserPopulation(int population) {
        Platform.runLater(()->{
            amountOfUsers.setText(population+"");
        });
    }

    private Node genText(String message){
        Text t = new Text(message);
        t.setFill(Paint.valueOf("white"));
        return t;
    }
    @Override
    public void printConsole(String consoleMessage) {
        if(!consoleMessage.contains("\n")) consoleMessage += '\n';

        Node toAdd = genText(consoleMessage);
        Platform.runLater(() ->console.getChildren().addAll(toAdd));
    }

    @Override
    public void displayError(String title, String body) {
        Platform.runLater( ()->{
            console.getChildren().addAll(genText("\nOOPs:["+title+"]\n"+body+"\n"));
        });
    }
}
