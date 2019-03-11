package messaging.system;

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

        ClientNetwork client = ClientUtils.initNetwork();
        Data db = new Data();
        ClientGUI GUI = new ClientGUI();
        ClientUserInterface clientInterface = GUI.open(args);

        ClientUtils.initInterface(clientInterface, client);


    }
}