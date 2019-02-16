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
public class Data {
    
    List<Message> messages = new ArrayList<Message>();
    List<User> users = new ArrayList<User>();
    List<ChatRoom> chatRooms = new ArrayList<ChatRoom>();
    
    public class User{
        String USER_ID;
        String DISPLAY_NAME;
    }
    public class ChatRoom{
        List<String> USER_IDS = new ArrayList<String>();
        List<String> MESSAGE_IDS = new ArrayList<String>();
    }
    
    public class Message{
        String MESSAGE_ID = null;
        String SENDER_ID;
        String ROOM_ID;
        String BODY;
    }
    
}
