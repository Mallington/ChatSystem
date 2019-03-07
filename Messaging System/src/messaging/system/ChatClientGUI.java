package messaging.system;

public class ChatClientGUI {
    public static void main(String[] args) throws InterruptedException {
        Constants.updateConstants(args, Constants.NodeType.ChatClient);

        ClientNetwork client = ClientUtils.initNetwork();
        Data db = new Data();
        ClientGUI GUI = new ClientGUI();

        ClientUserInterface clientInterface = GUI.open(args);

        ClientUtils.initInterface(clientInterface, client);


    }
}
