package messaging.system;

import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class ServerUtils {
    public static ServerConsole setupConsole(ServerNetwork server){
        ServerConsole serverConsole = new ServerConsole() {
            @Override
            public void userInputted(String userInput, ConsoleUtils console) {
                switch(userInput){
                    case "EXIT":
                        server.stopListening();
                        console.stopConsoleListener();
                        break;
                    default:
                        console.printConsole("Command "+userInput+" not recognised.");
                }
            }
        };
        serverConsole.printConsole(Constants.SERVER_ASCII);
        serverConsole.startConsoleListener();
        return serverConsole;
    }

    public static void setupNetwork(ServerNetwork server, ServerUserInterface userInterface){
        server.setServerUserInterface(userInterface);
        if(userInterface !=null) userInterface.displayPort(Constants.getPort());
        server.startListening();
    }

    public static MainServerWindowController setupServerGUI(String[] args){
        StageLoader<MainServerWindowController> GUI = new StageLoader();

        StageRunnable<MainServerWindowController> setup = new StageRunnable<MainServerWindowController>() {
            @Override
            Resource setupStage(Stage primaryStage) {
                Resource<MainServerWindowController> r = new Resource<MainServerWindowController>("MainServerWindow.fxml");
                primaryStage.setMinWidth(385);
                primaryStage.setMinHeight(400);

                primaryStage.setWidth(600);
                primaryStage.setHeight(400);

                primaryStage.setTitle("ChatServer");
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
