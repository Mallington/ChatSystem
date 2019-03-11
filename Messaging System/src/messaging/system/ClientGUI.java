package messaging.system;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ClientGUI extends Application{
    /**
     * Lets any other class know that a GUI is currently being instantiated and it has to wait
     */
    private static boolean initSemaphore =false;

    /**
     * Where the output of a new GUI creation is temporarily held
     */
    private static MainChatWindowController tempUserInterface = null;

    /**
     * Lets the instantiating thread know that it still has to wait, until set false when the UI is ready
     */
    private static boolean  userInterfaceSemaphore = true;

    /**
     * Initialises the GUI
     * @param primaryStage This is what the loaded scene will be added to
     */
    @Override
    public void start(Stage primaryStage) {
        Resource<MainChatWindowController> r = new Resource<MainChatWindowController>("MainChatWindow.fxml");
        try {
            primaryStage.setMinWidth(900);
            primaryStage.setMinHeight(400);

            primaryStage.setScene(new Scene(r.getNode()));

            userInterfaceSemaphore = true;
            setTempUserInterface(r.getController());
            getTempUserInterface().channelName.setText("Loading...");

            userInterfaceSemaphore = false;

            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Gets temp user interface.
     *
     * @return the temp user interface
     */
    private synchronized static MainChatWindowController getTempUserInterface() {
        return ClientGUI.tempUserInterface;
    }

    /**
     * @param tempUserInterface the temp user interface to be set.
     */
    private static synchronized void setTempUserInterface(MainChatWindowController tempUserInterface) {
        ClientGUI.tempUserInterface = tempUserInterface;
    }

    /**
     * This method employs a series of thread safe techniques to start an instance that is static by nature and retrieve
     * the controller associated with it. Once retrieved, an infinite number of these instances can be recreated
     *
     * @param args commandline arguments
     * @param cGUI Instantiating instance that wishes to create a new UI
     * @return Returns tbe controller instance
     */
    private  static MainChatWindowController loadGUI(String[] args, ClientGUI cGUI){
        //Checks to see if an instance is being made and waits like a good boy (or girl) for his turn
        while(initSemaphore);

        //If reached, this means a new instance can be made, then sets 'initSemaphore' to false so that other threads do
        //not interrupt its operation
        initSemaphore = true;

        //Sets flag to true to state an instance is being made
        userInterfaceSemaphore = true;
        //Starts the GUI on a separate thread
        new Thread(()->launch(args)).start();

        //Waits for the interface to be made
        while(userInterfaceSemaphore) {
            try {
                Thread.sleep((int)(Math.random()*100));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        //Once this point is reached, the interface has been initialised
        MainChatWindowController clientUI = cGUI.getTempUserInterface();
        initSemaphore = false;
        return clientUI;
    }

    public MainChatWindowController open(String[] args){
        return loadGUI(args, this);
    }
}
