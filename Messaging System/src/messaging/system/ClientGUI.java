package messaging.system;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ClientGUI extends Application{

    private static boolean initSemaphore =false;

    private static MainChatWindowController userInterface = null;

    private static boolean  userInterfaceSemaphore = true;

    @Override
    public void start(Stage primaryStage) {
        Resource<MainChatWindowController> r = new Resource<MainChatWindowController>("MainChatWindow.fxml");
        try {
            primaryStage.setMinWidth(900);
            primaryStage.setMinHeight(400);

            primaryStage.setScene(new Scene(r.getNode()));
            userInterfaceSemaphore = true;
            setUserInterface(r.getController());
            getUserInterface().channelName.setText("Loading...");

            userInterfaceSemaphore = false;

            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private synchronized MainChatWindowController getUserInterface() {
        return ClientGUI.userInterface;
    }

    private static synchronized void setUserInterface(MainChatWindowController userInterface) {
        ClientGUI.userInterface = userInterface;
    }

    public MainChatWindowController open(String[] args){

        while(initSemaphore);
        initSemaphore = true;
        userInterfaceSemaphore = true;
        new Thread(()->launch(args)).start();

        while(userInterfaceSemaphore) {
            try {
                Thread.sleep((int)(Math.random()*100));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        MainChatWindowController clientUI = getUserInterface();
        initSemaphore = false;
        return clientUI;
    }
}
