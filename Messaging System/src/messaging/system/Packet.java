/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package messaging.system;

import java.io.Serializable;

/**
 * This object is the main container which is written and read from the sockets
 *
 * @author mathew
 */
public class Packet implements Serializable{
    /**
     * This states the type of packet
     */
    private Constants.Header headerType = null;
    /**
     * The sender ID from the client or server it was sent from
     */
    private String senderID = null;
    /**
     * This can be an update container, message etc.
     */
    private Object payload = null;

    /**
     * Instantiates a new Packet.
     *
     * @param headerType the type of header
     */
    public Packet(Constants.Header headerType){
        this.headerType = headerType;
    }

    /**
     * Gets header type.
     *
     * @return the header type
     */
    public Constants.Header getHeaderType() {
        return headerType;
    }

    /**
     * Sets header type.
     *
     * @param headerType the header type
     */
    public void setHeaderType(Constants.Header headerType) {
        this.headerType = headerType;
    }

    /**
     * Gets sender id.
     *
     * @return the sender id
     */
    public String getSenderID() {
        return senderID;
    }

    /**
     * Sets sender id.
     *
     * @param senderID the sender id
     */
    public void setSenderID(String senderID) {
        this.senderID = senderID;
    }

    /**
     * Gets payload.
     *
     * @return the payload
     */
    public Object getPayload() {
        return payload;
    }

    /**
     * Sets payload.
     *
     * @param payload the payload
     */
    public void setPayload(Object payload) {
        this.payload = payload;
    }

}

