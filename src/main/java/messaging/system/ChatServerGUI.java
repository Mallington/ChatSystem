package messaging.system;

public class ChatServerGUI {
    public static void main(String args[]){
        Constants.updateConstants(args, Constants.NodeType.ChatServer);

        //Allows user to edit parameters
        GeneralUtils.promptUserOptions(args, Constants.NodeType.ChatServer, "-csp");

        Data db = new Data();
        ServerNetwork server = new ServerNetwork(Constants.getPort(), db);

        MainServerWindowController serverUserInterface = ServerUtils.setupServerGUI(args);
        ServerUtils.setupNetwork(server, serverUserInterface);
    }
}
