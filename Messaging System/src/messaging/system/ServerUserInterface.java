/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package messaging.system;

/**
 *
 * @author mathew
 */
public interface ServerUserInterface {
    public void displayError(String title, String body);
    public void displayPort(int port);
}
