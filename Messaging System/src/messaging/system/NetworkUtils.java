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
public abstract class NetworkUtils {
    private  String serverAddress = null;
    private final int PORT;
    private int timeout = 2000;
    private long recordedMillis = 0;
    
    public NetworkUtils(int PORT){
        this.PORT = PORT;
    }
    
    public NetworkUtils(String address, int port){
        this.serverAddress = address;
        this.PORT = port;
    }
    
    public void setTimeout(int timeMillis){
        this.timeout = timeMillis;
    }
    
    public void resetTimer(){
        recordedMillis = System.currentTimeMillis();
    }
    
    public boolean timeoutExceeded(){
        return (recordedMillis<System.currentTimeMillis());
    }
    
    public Packet makeRequest(Packet requestPacket){
        
        return null;
    }
    
}
