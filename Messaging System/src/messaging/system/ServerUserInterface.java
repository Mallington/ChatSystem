/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package messaging.system;

/**
 * Generic interface for server related classes to interface with
 * @author mathew
 */
public interface ServerUserInterface extends MasterUserInterface{
    /**
     * Displays a port on the implementing interface
     * @param port number to be displayed
     */
    public void displayPort(int port);
}
