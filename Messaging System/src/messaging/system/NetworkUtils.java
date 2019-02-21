/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package messaging.system;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 *
 * @author mathew
 */
public abstract class NetworkUtils {
    private  String serverAddress = null;
    private final int PORT;
    private int timeout = 2000;
    private long recordedMillis = 0;
    private MasterUserInterface masterOutput = null;
    
    public NetworkUtils(int port, MasterUserInterface out){
        this.PORT = port;
        this.masterOutput = out;
    }
    
    public NetworkUtils(String address, int port, MasterUserInterface out){
        this.serverAddress = address;
        this.PORT = port;
        this.masterOutput = out;
    }

    abstract Packet requestRecieved(Packet request);
    
    public void setTimeout(int timeMillis){
        this.timeout = timeMillis;
    }
    
    private void resetTimer(){
        recordedMillis = System.currentTimeMillis();
    }
    
    private boolean timeoutExceeded(){
        return (recordedMillis<System.currentTimeMillis());
    }
    public Packet makeRequest(Packet requestPacket, String address) throws IOException {
        serverAddress = address;
        return makeRequest(requestPacket);
    }
    
    public Packet makeRequest(Packet requestPacket) throws IOException {
        if(serverAddress !=null){
            Packet responce = null;
            resetTimer();

            Socket sock = new Socket(serverAddress, PORT);
            ObjectOutputStream out = new ObjectOutputStream(sock.getOutputStream());
            out.writeObject(requestPacket);

            ObjectInputStream in = new ObjectInputStream(sock.getInputStream());

            while(!timeoutExceeded()){
                if(in.available()>0){
                    try {
                        responce = (Packet)in.readObject();
                        break;
                    } catch (ClassNotFoundException e) {
                        System.out.println("Invalid Packet");
                        responce = null;
                    }

                }
            }

            if(responce == null) {
                System.out.println("Timeout exceeded");
            }
            return responce;
        }
        else{
            System.out.println("Error: IP not set.");
            return null;
        }
    }
    
}
