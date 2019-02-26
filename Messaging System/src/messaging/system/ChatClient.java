/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package messaging.system;

import java.io.IOException;
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


        Constants.updateConstants(args, Constants.NodeType.ChatClient);

        Data db = new Data();
        ClientConsole console = new ClientConsole(db);
        db.addListener(console);
        //rr1
        ClientNetwork client = new ClientNetwork(Constants.getServerAddress(), Constants.getPort(), console, db);
        client.setTimeout(5000);

        User newUser = new User(null, "Mark Allington");
        if(client.createUser(newUser)){
            console.printConsole("User Created.");
        }
        else{
            console.displayError("User Creation Failed", "Perhaps you do not have the right privileges");
        }

        client.startUpdaterTask();

        Scanner sc = new Scanner(System.in);

        while(true){
            System.out.println("Type a message:");
            String in = sc.nextLine();
            Message toSend = new Message();
            toSend.setBody(in);
            toSend.setRoomID(Constants.DEFAULT_CHAT_ROOM_ID);
            toSend.setSenderID(Constants.getUserId());
            client.sendMessage(toSend);
        }



    }
}
