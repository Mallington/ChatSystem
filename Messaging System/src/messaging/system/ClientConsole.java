/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package messaging.system;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mathew
 */
public class ClientConsole extends ConsoleUtils implements ClientUserInterface{

    @Override
    public void displayMessage(Message message) {
        if(message !=null){
            List<String> components = new ArrayList<String>();
            components.add("UID: "+message.getSenderID());
            components.add("Room ID: "+message.getRoomID());
            components.add("Message ID: "+message.getMessageID());
            components.add("Message:");
            components.add(message.getBody());
            System.out.println(ConsoleUtils.printMessageBox(components));
        }
        else{
            this.displayError("Message not found", "[Message deleted]");
        }
    }
}
