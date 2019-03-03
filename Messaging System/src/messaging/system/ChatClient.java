/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package messaging.system;

import java.util.Scanner;

/**
 *
 * @author mathew
 */
public class ChatClient {
     /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException {

        ClientGUI UI = new ClientGUI();

        MainChatWindowController cl = UI.open();

        Constants.updateConstants(args, Constants.NodeType.ChatClient);

        Data db = new Data();

        ClientNetwork client = new ClientNetwork(Constants.getServerAddress(), Constants.getPort());
        client.setTimeout(5000);

        ClientConsole console = new ClientConsole() {
            @Override
            public void userInputted(String userInput, ConsoleUtils console) {
                System.out.println("Switching \""+userInput+"\"");
                switch (userInput) {
                    case "EXIT":
                        console.printConsole("Exiting...");
                        client.stopUpdaterTask();
                        console.stopConsoleListener();
                        break;
                    default:
                        Message toSend = new Message();
                        toSend.setBody(userInput);
                        toSend.setRoomID(Constants.DEFAULT_CHAT_ROOM_ID);
                        toSend.setSenderID(Constants.getUserId());
                        client.sendMessage(toSend);
                        break;


                }
            }
        };

        console.setDataBase(db);
        db.addListener(console);

        client.setUserInterface(console);
        //rr1

        User newUser = new User(null, "Mark Allington- "+(int)(Math.random()*9999));
        if(client.createUser(newUser)){
            console.printConsole("User Created.");
        }
        else{
            console.displayError("User Creation Failed", "Perhaps you do not have the right privileges");
        }

        client.startUpdaterTask(db);
        console.startConsoleListener();



    }
}
