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
public class ChatServer {
    /**
     * @param args the command line arguments
     */
   static Data db;
    public static void main(String[] args) {
        Constants.updateConstants(args, Constants.NodeType.ChatServer);

        db= new Data();

        ServerNetwork server = new ServerNetwork(Constants.getPort(), db);

        ServerConsole serverConsole = new ServerConsole() {
            @Override
            public void userInputted(String userInput, ConsoleUtils console) {
                switch(userInput){
                    case "EXIT":
                        server.stopListening();
                        console.stopConsoleListener();
                        break;
                    default:
                        console.printConsole("Command "+userInput+" not recognised.");
                }
            }
        };
        serverConsole.startConsoleListener();

        server.setServerUserInterface(serverConsole);

        serverConsole.displayPort(Constants.getPort());
        server.startListening();
    }
}
