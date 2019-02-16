/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package messaging.system;

import java.io.Serializable;

/**
 *
 * @author mathew
 */
public class Packet implements Serializable{
    String SENDER_IP = "";
    String SENDER_ID = null;
    
    Object PAYLOAD = null;
}
