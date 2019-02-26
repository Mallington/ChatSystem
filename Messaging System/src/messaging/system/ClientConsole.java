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
    private Data dataBase =null;

    public ClientConsole(Data dataBase){
        this.dataBase = dataBase;
    }

    private String displayMessage(Message message, int width) {
        List<String> components = new ArrayList<String>();
        if(message !=null){
            components.add("Sender: "+dataBase.getUserByID(message.getSenderID()).getDisplayName());
            components.add("Chat Room: "+dataBase.getChatRoomByID(message.getRoomID()).getName());
            components.add("Message UID: "+message.getMessageID());
            components.add(printMessageBox(message.getBody(), width-4));
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
            System.out.println(displayMessage(newMessage, DEFAULT_BOX_SIZE));
        }
    }

    @Override
    public void update(User oldUser, User newUser) {
        if(oldUser == null && newUser!=null){
            System.out.println(printMessageBox("New user is online:\nDisplay Name: "+newUser.getDisplayName()+
                    "\nUID: "+newUser.getUserID(),DEFAULT_BOX_SIZE));
        }
    }
}
