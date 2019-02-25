/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package messaging.system;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mathew
 */
public class Data implements Serializable {
    
    private List<Message> messages = new ArrayList<Message>();
    private List<User> users = new ArrayList<User>();
    private List<ChatRoom> chatRooms = new ArrayList<ChatRoom>();

    private MasterUserInterface userInterface;

    transient private List<ChatRoomChangeListener> changeListeners = null;

    public Data(MasterUserInterface userInterface){
        changeListeners = new ArrayList<ChatRoomChangeListener>();
        this.userInterface = userInterface;
    }

    //Getters and setters

    public List<Message> getMessages() {
        return messages;
    }

    public List<User> getUsers() {
        return users;
    }

    public List<ChatRoom> getChatRooms() {
        return chatRooms;
    }

    public ChatRoom getChatRoomByID(String ID){
        for(ChatRoom chat : chatRooms) if(chat.getRoomID().equals(ID)) return chat;
        return null;
    }

    public void addListener(ChatRoomChangeListener listener){
        if(changeListeners !=null) changeListeners.add(listener);
    }
    public void removeListener(ChatRoomChangeListener listener){
        if(changeListeners !=null) changeListeners.remove(listener);
    }
    public void removeAllListeners(){
        if(changeListeners != null) changeListeners = new ArrayList<ChatRoomChangeListener>();
    }

    //Deletion

    public void updateChatRoom(ChatRoom update){
        for(ChatRoom room : chatRooms) if(room.getRoomID().equals(update.getRoomID())){
            chatRooms.remove(room);
            chatRooms.add(update);
        }
    }


    //Validation

    public boolean containsChatRoom(String UID){
        for(ChatRoom room : chatRooms) if(room.getRoomID().equals(UID)) return true;
        return false;
    }

    public boolean containsUser(String UID){
        for(User user : users) if(user.getUserID().equals(UID)) return true;
        return false;
    }

    public boolean containsMessage(String UID){
        for(Message message : messages) if(message.getMessageID().equals(UID)) return true;
        return false;
    }

    //Update methods

    private void updateDB(UpdateResponceContainer update){

        for (User u : update.getUsers()) {
            for (ChatRoomChangeListener change : changeListeners) change.update(null, u);
            users.add(u);
        }
        for (Message m : update.getMessages()) {
            for (ChatRoomChangeListener change : changeListeners) change.update(null, m);
            messages.add(m);
        }
    }

    public void updateAndFetchdDB(ChatRoom chatRoom, ClientNetwork clientNetwork){ //!!Remove listener not yet implemented!!

        List<String> messagesToFetch = new ArrayList<String>();
        List<String> usersToFetch = new ArrayList<String>();

        //Finding out which message objects need to be fetched from the server:
        for(String messageID : chatRoom.getMessageIDS()){
            if(!containsMessage(messageID)) {
                messagesToFetch.add(messageID);
               }
        }

        //Finds out which user objects need to be fetched from server:
        for(String user : chatRoom.getUserIDS()){
            if(!containsUser(user)) usersToFetch.add(user);
        }

        //Requests the user and message objects via an UPDATE_DB packet
        Packet req = new Packet(Constants.Header.UPDATE_DB);
        req.setPayload(new UpdateRequestContainer(messagesToFetch,usersToFetch));

        //Contains the requested objects
        Packet response = clientNetwork.makeRequest(req);


        try{
            if(response !=null) {
                switch(response.getHeaderType()){
                    case SUCCESS:
                        updateDB((UpdateResponceContainer) response.getPayload());
                        updateChatRoom(chatRoom);
                        break;

                    case NOT_AUTHORISED:
                        userInterface.displayError("Not Authorised","Server has locked us out");
                        break;

                    default:
                        userInterface.printConsole("I think someone is trying to tamper.");
                        break;

                }
            }

        }
        catch (Exception e){

        }



    }


    
}
