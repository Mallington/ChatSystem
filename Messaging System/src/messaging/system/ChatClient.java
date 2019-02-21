/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package messaging.system;

import java.io.IOException;

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
        Packet request = new Packet();
        request.setHeaderType(Constants.Header.SUCCESS);
        client.setTimeout(5000);
        try {
            client.makeRequest(request);
        } catch (IOException e) {
            console.displayError("Network Error", e.getMessage()+"\n Troubleshooting:\nMake");

        }

    }
}
