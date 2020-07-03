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

            //Runs the GUI
            ChatServerGUI.main(args);
        }
        else{
            //Runs the console

            Data db = new Data();

            //Creates a new network instance used for listening for packets
            ServerNetwork server = new ServerNetwork(Constants.getPort(), db);

            //Sets up console with main listener for inputting messages and EXIT command
            ServerConsole userInterface = ServerUtils.setupConsole(server);

            //Starts listening for requests
            ServerUtils.setupNetwork(server, userInterface);
        }
    }
}
