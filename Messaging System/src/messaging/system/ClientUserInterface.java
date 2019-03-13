/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package messaging.system;

import java.util.List;

/**
 * A generic interface, used by the network and data class used for writing updates to the implementing class.
 * This means the classes such as the network classes can freely interface with either the GUI or console regardless
 * of its type, with the assurance that the valid client methods are implemented.
 *
 * @author mathew
 */
public interface ClientUserInterface  extends MasterUserInterface,  ChatRoomChangeListener{
    /**
     * Updates the list of chat rooms that the user is in.
     *
     * @param roomList the room list
     */
    public void updateChatRoomList(List<ChatRoom> roomList);

    /**
     * Gets data base.
     *
     * @return the data base
     */
    public Data getDataBase();

    /**
     * Sets data base.
     *
     * @param dataBase the data base
     */
    public void setDataBase(Data dataBase);
}
