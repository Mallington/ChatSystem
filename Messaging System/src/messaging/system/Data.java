/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package messaging.system;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author mathew
 */
public class Data implements Serializable {
    
    private List<Message> messages = new ArrayList<Message>();
    private List<User> users = new ArrayList<User>();
    private List<ChatRoom> chatRooms = new ArrayList<ChatRoom>();

    transient private List<ChatRoomChangeListener> changeListeners = null;

    public Data(){
        changeListeners = new ArrayList<ChatRoomChangeListener>();
        createDefaultChatRoom();
    }

    //Getters and setters

    synchronized public List<Message> getMessages() {
        return messages;
    }

    synchronized public List<User> getUsers() {
        return users;
    }

    synchronized public List<ChatRoom> getChatRooms() {
        return chatRooms;
    }

    public ChatRoom getChatRoomByID(String ID){
        for(ChatRoom chat : getChatRooms()) if(chat.getRoomID().equals(ID)) return chat;
        return null;
    }

    public Message getMessageByID(String ID){
        for(Message m : getMessages()) if(m.getMessageID().equals(ID)) return m;
        return null;
    }

    public User getUserByID(String ID){
        for(User u : getUsers()) if(u.getUserID().equals(ID)) return u;
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
        for(ChatRoom room : getChatRooms()) if(room.getRoomID().equals(update.getRoomID())){
            getChatRooms().remove(room);
            getChatRooms().add(update);
        }
    }


    //Validation

    public boolean containsChatRoom(String UID){
        for(ChatRoom room : getChatRooms()) if(room.getRoomID().equals(UID)) return true;
        return false;
    }

    public boolean containsUser(String UID){
        for(User user : getUsers()) if(user.getUserID().equals(UID)) return true;
        return false;
    }

    public boolean containsMessage(String UID){
        for(Message message : getMessages()) if(message.getMessageID().equals(UID)) return true;
        return false;
    }

    //Update methods

    private String genUID(){
        return UUID.randomUUID().toString();
    }
    String updateUser(User user){
        if(!containsUser(user.getUserID())){
            String newUID = "";
            user.setUserID((newUID =genUID()));
            getUsers().add(user);
            addToDefaultChatRoom(user);
            return newUID;
        } else{
            getUsers().remove(getUserByID(user.getUserID()));
            getUsers().add(user);
            return user.getUserID();
        }
    }

    String updateMessage(Message message){
        if(!containsMessage(message.getMessageID())){
            String newUID = "";
            message.setMessageID((newUID =genUID()));
            getMessages().add(message);
            getChatRoomByID(message.getRoomID()).getMessageIDS().add(message.getMessageID());
            return newUID;
        } else{
            getMessages().remove(getMessageByID(message.getMessageID()));
            getMessages().add(message);
            return message.getMessageID();
        }
    }

    private void updateDB(UpdateResponceContainer update){

        for (User u : update.getUsers()) {
            for (ChatRoomChangeListener change : changeListeners) change.update(null, u);
            getUsers().add(u);
        }
        for (Message m : update.getMessages()) {
            for (ChatRoomChangeListener change : changeListeners) change.update(null, m);
            getMessages().add(m);
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
                        System.out.println("Not Authorised\n"+"Server has locked us out");
                        break;

                    default:
                        System.out.println("I think someone is trying to tamper.");
                        break;

                }
            }

        }
        catch (Exception e){

        }



    }

    private void createDefaultChatRoom(){
        ChatRoom defaultChatRoom = new ChatRoom(Constants.DEFAULT_CHAT_ROOM_ID);
        defaultChatRoom.setName("Default Chat Room");
        getChatRooms().add(defaultChatRoom);
    }
    private void addToDefaultChatRoom(User user){
        getChatRoomByID(Constants.DEFAULT_CHAT_ROOM_ID).getUserIDS().add(user.getUserID());
    }

    
}
