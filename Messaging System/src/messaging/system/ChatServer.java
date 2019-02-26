/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package messaging.system;

import java.util.ArrayList;
import java.util.UUID;

/**
 *
 * @author mathew
 */
public class ChatServer {
    /**
     * @param args the command line arguments
     */
   static Data db;
    public static void main(String[] args) {
        Constants.updateConstants(args, Constants.NodeType.ChatServer);

        ServerConsole serverConsole = new ServerConsole();
        db= new Data();

        ServerNetwork server = new ServerNetwork(Constants.getPort(), serverConsole, db);

        serverConsole.displayPort(Constants.getPort());
        server.startListening();
    }
}
