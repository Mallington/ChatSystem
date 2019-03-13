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
 * This is the main storage container for either client or server
 * Contains messages, Chat Rooms and User objects, each one referenced by specific IDs,
 * like a relational database.
 * @author mathew
 */
public class Data implements Serializable {
    /**
     * List of all message objects
     */
    private List<Message> messages = new ArrayList<Message>();
    /**
     * List of user objects
     */
    private List<User> users = new ArrayList<User>();
    /**
     * List of all chat rooms containing references to the IDs of the users and messages in them
     */
    private List<ChatRoom> chatRooms = new ArrayList<ChatRoom>();

    /**
     * Contains listeners that are activated when data inside the container is changed
     */
    transient private List<ChatRoomChangeListener> changeListeners = null;

    /**
     * Main point of entry for the class, also generates the default chat room that every user shares
     */
    public Data(){
        changeListeners = new ArrayList<ChatRoomChangeListener>();
        createDefaultChatRoom();
    }

    /*Getters and setters */

    /**
     * Gets a list of all the messages.
     *
     * @return the messages
     */
    synchronized public List<Message> getMessages() {
        return messages;
    }

    /**
     * Gets a list of all the users.
     *
     * @return the users
     */
    synchronized public List<User> getUsers() {
        return users;
    }

    /**
     * Gets a list of all the chat rooms
     *
     * @return the chat rooms
     */
    synchronized public List<ChatRoom> getChatRooms() {
        return chatRooms;
    }


    /**
     * Gets chat room by ID
     *
     * @param ID the id
     * @return the chat room
     */
    public ChatRoom getChatRoomByID(String ID){
        for(ChatRoom chat : getChatRooms()) if(chat.getRoomID().equals(ID)) return chat;
        return null;
    }

    /**
     * Gets message by ID
     *
     * @param ID the id
     * @return the message
     */
    public Message getMessageByID(String ID){
        for(Message m : getMessages()) if(m.getMessageID().equals(ID)) return m;
        return null;
    }

    /**
     * Gets user by ID
     *
     * @param ID the id
     * @return the user
     */
    public User getUserByID(String ID){
        for(User u : getUsers()) if(u.getUserID().equals(ID)) return u;
        return null;
    }

    /**
     * Adds a listener which will be activated when a change of data occurs
     *
     * @param listener the listener
     */
    public void addListener(ChatRoomChangeListener listener){
        if(changeListeners !=null) changeListeners.add(listener);
    }

    /**
     * Removes a istener.
     *
     * @param listener the listener to remove
     */
    public void removeListener(ChatRoomChangeListener listener){
        if(changeListeners !=null) changeListeners.remove(listener);
    }

    /**
     * Removes all the listeners.
     */
    public void removeAllListeners(){
        if(changeListeners != null) changeListeners = new ArrayList<ChatRoomChangeListener>();
    }

    /*Deletion*/

    public void updateChatRoom(ChatRoom update){
        for(ChatRoom room : getChatRooms()) if(room.getRoomID().equals(update.getRoomID())){
            getChatRooms().remove(room);
            getChatRooms().add(update);
        }
    }


    /*Validation */

    /**
     * Whether or not a specific chat room exists
     *
     * @param UID the uid of the chat room
     * @return True - It's there, False - It's not
     */
    public boolean containsChatRoom(String UID){
        for(ChatRoom room : getChatRooms()) if(room.getRoomID().equals(UID)) return true;
        return false;
    }

    /**
     * Whether or not a specific user exists
     *
     * @param UID the uid of the chat room
     * @return True - It's there, False - It's not
     */
    public boolean containsUser(String UID){
        for(User user : getUsers()) if(user.getUserID().equals(UID)) return true;
        return false;
    }

    /**
     * Whether or not a specific messsage exists
     *
     * @param UID the uid of the chat room
     * @return True - It's there, False - It's not
     */
    public boolean containsMessage(String UID){
        for(Message message : getMessages()) if(message.getMessageID().equals(UID)) return true;
        return false;
    }

    /*Update methods*/

    /**
     * Generates a unique ID
     * @return the UID
     */
    private String genUID(){
        return UUID.randomUUID().toString();
    }

    /**
     * Either updates or creates a new user, depending on whether or not it is there
     *
     * @param user the user to be updated
     * @return the new ID of the user
     */
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

    /**
     *Either updates or creates a new message, depending on whether or not it is there
     *
     * @param message the message
     * @return the new ID of the message
     */
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

    /**
     * Adds new messages and new users to the database, then notifies the appropriate listeners
     * @param update container to unload
     */
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

    /**
     * Update and fetch db.
     *
     * @param chatRoom      the chat room
     * @param clientNetwork the client network
     */
    public void updateAndFetchdDB(ChatRoom chatRoom, ClientNetwork clientNetwork){ //!!Remove listener not yet implemented!!

        List<String> messagesToFetch = new ArrayList<String>();
        List<String> usersToFetch = new ArrayList<String>();

        //Finding out which people Online objects need to be fetched from the server:
        for(String messageID : chatRoom.getMessageIDS()){
            if(!containsMessage(messageID)) {
                messagesToFetch.add(messageID);
               }
        }

        //Finds out which user objects need to be fetched from server:
        for(String user : chatRoom.getUserIDS()){
            if(!containsUser(user)) usersToFetch.add(user);
        }

        //Requests the user and peopleOnline objects via an UPDATE_DB packet
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
            System.out.println("Failed container update+ ");
            e.printStackTrace();
        }



    }

    /**
     * Creates a default chat room that every user has access to
     */
    private void createDefaultChatRoom(){
        ChatRoom defaultChatRoom = new ChatRoom(Constants.DEFAULT_CHAT_ROOM_ID);
        defaultChatRoom.setName("Default Chat Room");
        getChatRooms().add(defaultChatRoom);
    }

    /**
     * Adds a user to the default chat room
     * @param user to be added
     */
    private void addToDefaultChatRoom(User user){
        getChatRoomByID(Constants.DEFAULT_CHAT_ROOM_ID).getUserIDS().add(user.getUserID());
    }

    
}
