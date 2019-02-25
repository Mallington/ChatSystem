/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package messaging.system;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

/**
 *
 * @author mathew
 */
public abstract class NetworkUtils {
    private  String serverAddress = null;
    private int port = 0;
    private int timeout = 2000;
    private int retryPeriod = 3000;

    private long recordedMillis = 0;
    private MasterUserInterface masterOutput = null;

    private ServerSocket server= null;
    private boolean listen = false;

    public NetworkUtils(int port, MasterUserInterface out){
        this.port = port;
        this.masterOutput = out;
    }

    public NetworkUtils(String address, int port, MasterUserInterface out){
        this.serverAddress = address;
        this.port = port;
        this.masterOutput = out;
    }

    public boolean initSocket(){
        try {
            server = new ServerSocket(this.port);
            return true;
        } catch (IOException e) {
            masterOutput.displayError("Port already occupied", "Please stop all other instances of the server.");
            return false;
        }
    }

    public void startListening(){
        if(!listen) {
            listen = true;
            while(!initSocket()){
                try {
                    masterOutput.printConsole("Retrying in "+(retryPeriod/1000.0)+" seconds ...");
                    Thread.sleep(retryPeriod);
                } catch (InterruptedException e) {}
            }
            masterOutput.printConsole("Server is open for business.");
            new Thread(serverThread).start();
        }
        else{
            masterOutput.printConsole("Network listener already running.");
        }
    }

    public void stopListening(){
        listen = false;
    }

    private Runnable serverThread = ()->{
        while(listen) {
            try {
                Socket sock = server.accept();
                new Thread(() -> handleRequest(sock)).start();

            } catch (IOException e) {
                masterOutput.displayError("Port already occupied", "Please stop all over instances of the server.\nWill retry in a bit.");

                try {
                    Thread.sleep(retryPeriod);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                masterOutput.printConsole("Retrying in ");
            }
        }

        try {
            if(server !=null) server.close();
        } catch (IOException e) {}
    };
    private boolean handleRequest(Socket sock){
        try{
            ObjectInputStream in  =  new ObjectInputStream(sock.getInputStream());
            Packet request = (Packet)in.readObject();
            Packet response = requestRecieved(request);
            new ObjectOutputStream(sock.getOutputStream()).writeObject(response);
            return true;
        }
        catch (Exception e) { return false;}
    }

    abstract Packet requestRecieved(Packet request);

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeMillis){
        this.timeout = timeMillis;
    }

    public int getRetryPeriod() {
        return retryPeriod;
    }

    public void setRetryPeriod(int retryPeriod) {
        this.retryPeriod = retryPeriod;
    }

    private void resetTimer(){
        recordedMillis = System.currentTimeMillis();
    }

    private boolean timeoutExceeded(){
        return (recordedMillis+timeout<System.currentTimeMillis());
    }
    public Packet makeRequest(Packet requestPacket, String address) throws IOException {
        serverAddress = address;
        return makeRequest(requestPacket);
    }

    public Packet makeRequest(Packet requestPacket){
        if (serverAddress != null) {
            Packet responce = null;
            while(true) {
                try {
                    Socket sock = new Socket(serverAddress, this.port);
                    ObjectOutputStream out = new ObjectOutputStream(sock.getOutputStream());
                    sock.setSoTimeout(timeout);
                    out.writeObject(requestPacket);
                    try {
                        sock.setSoTimeout(timeout);
                        ObjectInputStream in = new ObjectInputStream(sock.getInputStream());
                        Packet ret = (Packet) in.readObject();
                        if(ret !=null) return ret;
                    } catch (ClassNotFoundException e) {
                        masterOutput.displayError("Invalid Packet", "Ignoring incorrect object received");
                    }
                } catch (IOException io) {
                    masterOutput.displayError("Server Is not Available", "Retrying in " + (retryPeriod / 1000.0) + " seconds ...");
                    try {
                        Thread.sleep(retryPeriod);
                    } catch (Exception e) {}
                }
            }
        } else {
            masterOutput.displayError("Null Error", "IP field is null.\nPlease ensure you have entered the server IP");
            return null;
        }
    }

}
