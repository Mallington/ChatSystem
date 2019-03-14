package messaging.system;

import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * Main entry point for running the GUI
 */
public class ChatClientGUI {
    /**
     *Parameters:
     * Regardless of the -GUI parameter, GUI will be run
     * -cca (Optional): Address in the form of an IP address or another form
     * -ccp (Optional): Port to be connected to
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException {
        Constants.updateConstants(args, Constants.NodeType.ChatClient);

        GeneralUtils.promptUserOptions(args, Constants.NodeType.ChatClient, "-cca", "-ccp", "-user", "-name");

        ClientNetwork client = ClientUtils.initNetwork();
        Data db = new Data();

        MainChatWindowController clientInterface = ClientUtils.setupClientGUI(args);
        clientInterface.setOnClose(event -> {
            client.setNetworkClosed(true);
        });
        ClientUtils.initInterface(clientInterface, client, db);

    }
}
