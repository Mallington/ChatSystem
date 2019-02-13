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
public class MessagingSystem {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ChatServer.main(args);
        for(String s : args )System.out.println(s);
    }
    
}
