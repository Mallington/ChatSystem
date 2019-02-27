/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package messaging.system;

import java.util.List;

/**
 *
 * @author mathew
 */
public interface ClientUserInterface  extends MasterUserInterface,  ChatRoomChangeListener{
    public void updateChatRoomList(List<ChatRoom> roomList);
}
