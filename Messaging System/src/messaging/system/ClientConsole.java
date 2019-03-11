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
 * Class is responsible for displaying messages to the commandline, as well as notifying the user
 * of new people online
 *
 * @author mathew
 */
public abstract class ClientConsole extends ConsoleUtils implements ClientUserInterface{
    /**
     * Contains a pointer to the data base instance
     */
    private Data dataBase =null;

    /**
     * Instantiates a new Client console.
     */
    public ClientConsole(){
        super.setMasterInterface(this);
    }

    /**
     * Displays a message box containing the contents of a message.
     *
     * @param message the message object
     * @param width   the width of the message box
     * @return the string form of the message box ready to be printed
     */
    private String displayMessage(Message message, int width) {
        List<String> components = new ArrayList<String>();
        if(message !=null && dataBase !=null){
            User u = dataBase.getUserByID(message.getSenderID());
            ChatRoom room = dataBase.getChatRoomByID(message.getRoomID());
            components.add("Sender: "+((u == null)? message.getSenderID() :u.getDisplayName()));
            components.add("Chat Room: "+((room == null)? message.getRoomID() :room.getName()));
            components.add("Message UID: "+message.getMessageID());
            components.add(printMessageBox(message.getBody(), width-4));
        }
        else{
            components.add("[Message Not Found]");
        }

        return printMessageBox(components, width);
    }

    /**
     * See interface class for documentation
     */
    @Override
    public Data getDataBase() {
        return dataBase;
    }

    /**
     * See interface class for documentation
     */
    @Override
    public void setDataBase(Data dataBase) {
        this.dataBase = dataBase;
    }

    /**
     * See interface class for documentation
     */
    @Override
    public void update(Message oldMessage, Message newMessage) {
        if(oldMessage == null && newMessage !=null){
            System.out.println(displayMessage(newMessage, DEFAULT_BOX_SIZE));
        }
    }

    /**
     * See interface class for documentation
     */
    @Override
    public void update(User oldUser, User newUser) {
        if(oldUser == null && newUser!=null){
            System.out.println(printMessageBox("User is online:\nDisplay Name: "+newUser.getDisplayName()+
                    "\nUID: "+newUser.getUserID(),DEFAULT_BOX_SIZE));
        }
    }

    /**
     * See interface class for documentation
     */
    @Override
    public void updateChatRoomList(List<ChatRoom> roomList) {

    }
}
