/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package messaging.system;

import java.io.IOException;
import java.net.ServerSocket;

/**
 *
 * @author mathew
 */
public class ClientNetwork extends NetworkUtils{
    
    private ClientUserInterface userInterface;
    
    public ClientNetwork(String serverAddress, int port, ClientUserInterface user){
        super(serverAddress, port, user);
        
        this.userInterface = user;
        userInterface.printConsole("Attempting to connect via \""+serverAddress+":"+""+port+"\".");
    }
    
    private void connect() throws IOException{
        ServerSocket sock = new ServerSocket();
    }

    @Override
    Packet requestRecieved(Packet request) {
        Packet returnPacket = new Packet();
        returnPacket.setHeaderType(Constants.Header.UNEXPECTED_PACKET);

        return returnPacket;
    }
}
