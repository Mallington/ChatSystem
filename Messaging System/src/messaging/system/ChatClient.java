/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package messaging.system;

/**
 *
 * @author mathew
 */
public class ChatClient {
     /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Constants.updateConstants(args, Constants.NodeType.ChatClient);
        
        ClientConsole console = new ClientConsole();
        
        ClientNetwork client = new ClientNetwork(Constants.getServerAddress(), Constants.getPort(), console);
        
    }
}
