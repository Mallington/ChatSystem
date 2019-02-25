/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package messaging.system;

import java.util.ArrayList;
import java.util.UUID;

/**
 *
 * @author mathew
 */
public class ChatServer {
    /**
     * @param args the command line arguments
     */
   static Data db;
    public static void main(String[] args) {
        Constants.updateConstants(args, Constants.NodeType.ChatServer);

        ServerConsole serverConsole = new ServerConsole();
        db= new Data(serverConsole);

        User user = new User("AA1", "Mathew Allington");
        ChatRoom chatRoom = new ChatRoom(new ArrayList<String>(), new ArrayList<String>());
        chatRoom.getUserIDS().add(user.getUserID());
        chatRoom.setRoomID("RR1");
        db.getUsers().add(user);
        db.getChatRooms().add(chatRoom);
      new Thread(()->{
          while (true){


        Message m = new Message();
        m.setBody("This is a test message,\n hopefully you should have got this");
        String MID = UUID.randomUUID().toString();
        m.setSenderID(user.getUserID());
        m.setMessageID(MID);
        m.setRoomID(chatRoom.getRoomID());

        chatRoom.getMessageIDS().add(m.getMessageID());

        db.getMessages().add(m);


          try {
              Thread.sleep((int)(Math.random() *5000));
          } catch (InterruptedException e) {
              e.printStackTrace();
          }
      }
      }).start();

        ServerNetwork server = new ServerNetwork(Constants.getPort(), serverConsole, db);

     serverConsole.displayPort(Constants.getPort());
     server.startListening();
    }
}
