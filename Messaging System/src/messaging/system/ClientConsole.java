/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package messaging.system;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mathew
 */
public class ClientConsole extends ConsoleUtils implements ClientUserInterface{


    private String displayMessage(Message message, String title, int width) {
        List<String> components = new ArrayList<String>();
        if(message !=null){
            components.add(printMessageBox(title, width-4));
            components.add("UID: "+message.getSenderID());
            components.add("Room ID: "+message.getRoomID());
            components.add("Message ID: "+message.getMessageID());
            components.add("Message:");
            components.add(message.getBody());
        }
        else{
            components.add("[Message Not Found]");
        }

        return printMessageBox(components, width);
    }


    //NEED TO DO NEXT

    @Override
    public void update(Message oldMessage, Message newMessage) {
        if(oldMessage == null && newMessage !=null){
            System.out.println(displayMessage(newMessage, "New Message:", DEFAULT_BOX_SIZE));
        }
    }

    @Override
    public void update(User oldUser, User newUser) {

    }
}
