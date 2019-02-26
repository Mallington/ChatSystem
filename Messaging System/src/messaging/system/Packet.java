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

    private Constants.Header headerType = null;
    private String senderID = null;
    private Object payload = null;

    public Packet(Constants.Header headerType){
        this.headerType = headerType;
    }

    public Constants.Header getHeaderType() {
        return headerType;
    }

    public void setHeaderType(Constants.Header headerType) {
        this.headerType = headerType;
    }

    public String getSenderID() {
        return senderID;
    }

    public void setSenderID(String senderID) {
        this.senderID = senderID;
    }

    public Object getPayload() {
        return payload;
    }

    public void setPayload(Object payload) {
        this.payload = payload;
    }

}

