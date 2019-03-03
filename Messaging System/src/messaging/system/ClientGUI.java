package messaging.system;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ClientGUI extends Application{

    private MainChatWindowController userInterface = null;

    @Override
    public void start(Stage primaryStage) {
        Resource<MainChatWindowController> r = new Resource<MainChatWindowController>("MainChatWindow.fxml");
        try {
            primaryStage.setMinWidth(900);
            primaryStage.setMinHeight(400);

            primaryStage.setScene(new Scene(r.getNode()));
            userInterface = r.loadController();

            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public MainChatWindowController open(){
        launch(null);
        return userInterface;
    }
}
