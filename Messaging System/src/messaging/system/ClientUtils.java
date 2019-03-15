package messaging.system;

import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * This class contains methods associated with setting up the client
 */
public class ClientUtils {

    /**
     * Sets up the main logic for the console, providing an interface to send messages and Exit the program
     *
     * @param client the client
     * @return the client console
     */
    public static ClientConsole initConsole(ClientNetwork client){
        return new ClientConsole() {
            @Override
            public void userInputted(String userInput, ConsoleUtils console) {
                console.printConsole("Inputted: "+userInput);
                switch (userInput.trim()) {
                    case "EXIT":
                        console.printConsole("Exiting...");

                        if(!client.isNetworkClosed() && !client.isConnectionLost()) {
                            client.logOff();
                        }

                        client.stopUpdaterTask();
                        client.setNetworkClosed(true);
                        console.stopConsoleListener();
                        break;
                    default:
                        if(!client.isNetworkClosed() && !client.isConnectionLost()) {
                            Message toSend = new Message();
                            toSend.setBody(userInput);
                            toSend.setRoomID(Constants.DEFAULT_CHAT_ROOM_ID);
                            toSend.setSenderID(Constants.getUserId());
                            client.sendMessage(toSend);
                        }
                        else{
                            console.printConsole("NOT SENT - Please wait for client to connect again.");
                        }
                        break;


                }
            }
        };
    }

    /**
     * Initialise a new network client
     *
     * @return the client network object
     */
    public static ClientNetwork initNetwork(){
        ClientNetwork client = new ClientNetwork(Constants.getServerAddress(), Constants.getPort());
        client.setTimeout(5000);
        return client;
    }

    /**
     * Creates a new database, sets client console listeners, creates a new user and starts the update requests
     *
     * @param clientInterface the client interface
     * @param client          the client
     */
    public static void initInterface(ClientUserInterface clientInterface, ClientNetwork client, Data db){

        clientInterface.setDataBase(db);
        clientInterface.setNetwork(client);
        db.addListener(clientInterface);

        client.setUserInterface(clientInterface);

        User newUser = new User(null, (Constants.getUserName() ==null)? "Unknown" : Constants.getUserName());


        if(!Constants.getUserId().equals("GUEST")) newUser.setUserID(Constants.getUserId());

        if (client.createUser(newUser)) {
            clientInterface.printConsole("User Created.");
        } else {
            if(!client.isNetworkClosed())clientInterface.displayError("User Creation Failed", "Perhaps you do not have the right privileges");
        }

        client.startUpdaterTask(db);
    }

    /**
     * Setups up and loads the GUI window
     *
     * @param args the commandline arguments
     * @return the main chat window controller
     */
    public static MainChatWindowController setupClientGUI(String args[]){
        StageLoader<MainChatWindowController> GUI = new StageLoader();

        StageRunnable<MainChatWindowController> setup = new StageRunnable<MainChatWindowController>() {
            @Override
            Resource setupStage(Stage primaryStage) {
                Resource<MainChatWindowController> r = new Resource<MainChatWindowController>("MainChatWindow.fxml");
                primaryStage.setMinWidth(900);
                primaryStage.setMinHeight(400);
                primaryStage.setTitle("Chat Client");
                primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                    @Override
                    public void handle(WindowEvent event) {
                        System.exit(0);
                    }
                });
                return r;
            }
        };

       return GUI.open(args, setup);
    }

}
