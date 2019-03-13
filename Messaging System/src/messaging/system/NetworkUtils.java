/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package messaging.system;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * The type Network utils.
 *
 * @author mathew
 */
public abstract class NetworkUtils {
    String serverAddress = null;
    int port = -1;
    private int timeout = 2000;
    private int retryPeriod = 3000;

    private boolean connectionLost = false;

    private long recordedMillis = 0;
    private MasterUserInterface masterOutput = null;

    private ServerSocket server= null;
    private boolean listen = false;
    private boolean networkClosed = false;

    private Thread serverThread = null;

    public NetworkUtils(int port){
        this.port = port;
    }

    public NetworkUtils(String address, int port){
        this.serverAddress = address;
        this.port = port;
    }

    public synchronized boolean isNetworkClosed() {
        return networkClosed;
    }

    public synchronized void setNetworkClosed(boolean networkClosed) {
        this.networkClosed = networkClosed;
    }

    public boolean initSocket(){
        try {
            server = new ServerSocket(this.port);
            return true;
        } catch (IOException e) {
            if(masterOutput!=null) masterOutput.displayError("Port already occupied", "Please stop all other instances of the server.");
            return false;
        }
    }

    public void startListening(){
        if(!listen) {
            listen = true;
            while(!initSocket()&&!serverThread.isInterrupted()){
                try {
                    if(masterOutput!=null) masterOutput.printConsole("Retrying in "+(retryPeriod/1000.0)+" seconds ...");
                    Thread.sleep(retryPeriod);
                } catch (InterruptedException e) {}
            }
            if(masterOutput!=null) masterOutput.printConsole("Server is open for business.");
            (serverThread = new Thread(serverRunnable)).start();
        }
        else{
            if(masterOutput!=null) masterOutput.printConsole("Network listener already running.");
        }
    }

    public void stopListening(){
        if(masterOutput !=null) masterOutput.printConsole("Stopping server thread");
        try{
            server.close();
        }catch(Exception e){
            if(masterOutput !=null)masterOutput.displayError("Server Thread","Failed to close");
        }
        listen = false;
    }

    private Runnable serverRunnable = ()->{
        while(listen) {
            try {
                Socket sock = server.accept();
                new Thread(() -> handleRequest(sock)).start();

            } catch (IOException e) {
                if(listen) {
                    if (masterOutput != null)
                        masterOutput.displayError("Port already occupied", "Please stop all over instances of the server.\nWill retry in a bit.");

                    try {
                        Thread.sleep(retryPeriod);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }

                    if (masterOutput != null) masterOutput.printConsole("Retrying in ");
                }
            }
        }
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

    public void setMasterOutput(MasterUserInterface masterOutput) {
        this.masterOutput = masterOutput;
    }

    private boolean timeoutExceeded(){
        return (recordedMillis+timeout<System.currentTimeMillis());
    }
    public Packet makeRequest(Packet requestPacket, String address) throws IOException {
        serverAddress = address;
        return makeRequest(requestPacket);
    }
    private Packet  linePacket(Packet packet){
        packet.setSenderID(Constants.getUserId());
        return packet;
    }

    public Packet makeRequest(Packet requestPacket){
        if (serverAddress != null) {
            Packet responce = null;
            while(!isNetworkClosed()) {
                try {
                    Socket sock = new Socket(serverAddress, this.port);
                    ObjectOutputStream out = new ObjectOutputStream(sock.getOutputStream());
                    sock.setSoTimeout(timeout);
                    requestPacket = linePacket(requestPacket);
                    out.writeObject(requestPacket);
                    try {
                        sock.setSoTimeout(timeout);
                        ObjectInputStream in = new ObjectInputStream(sock.getInputStream());
                        Packet ret = (Packet) in.readObject();
                        connectionLost = false;
                        if(ret !=null) return ret;
                    } catch (ClassNotFoundException e) {
                        if(masterOutput!=null) masterOutput.displayError("Invalid Packet", "Ignoring incorrect object received");
                    }
                } catch (IOException io) {
                    if(masterOutput!=null) {
                        connectionLost = true;
                        masterOutput.displayError("Server Is not Available", "Retrying in " + (retryPeriod / 1000.0) + " seconds ...");
                    }
                    try {
                        Thread.sleep(retryPeriod);
                    } catch (Exception e) {
                    }
                }
            }
        } else {
            if(masterOutput!=null) masterOutput.displayError("Null Error", "IP field is null.\nPlease ensure you have entered the server IP");
            connectionLost = false;
            return null;
        }
        connectionLost = false;
        return null;
    }

    public boolean isConnectionLost() {
        return connectionLost;
    }
}
