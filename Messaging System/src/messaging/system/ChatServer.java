/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package messaging.system;

/**
 * Main entry point for loading the chat server
 *
 * @author mathew
 */
public class ChatServer {
    /**
     * Parameters
     * -csp (Optional): The port that the server will load on.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Constants.updateConstants(args, Constants.NodeType.ChatServer);

        if(GeneralUtils.resolveRunGUIParam()) {
            ChatServerGUI.main(args);
        }
        else{
            Data db = new Data();

            ServerNetwork server = new ServerNetwork(Constants.getPort(), db);

            ServerConsole userInterface = ServerUtils.setupConsole(server);

            ServerUtils.setupNetwork(server, userInterface);
        }
    }
}
