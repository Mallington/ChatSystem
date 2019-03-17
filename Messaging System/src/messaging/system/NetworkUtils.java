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
 * This class is used by both ClientNetwork and ServerNetwork
 * It provides an interface that can listen for incoming requests and make them,
 * while notifying the appropriate listeners attached to it
 *
 * @author mathew
 */
public abstract class NetworkUtils {
    /**
     * Address to be connected to
     */
    String serverAddress = null;
    /**
     * Port number to be used
     */
    int port = -1;
    /**
     * Maximum time that should be waited for a request to be received
     */
    private int timeout = 2000;
    /**
     * The time between requests
     */
    private int retryPeriod = 3000;
    /**
     * Whether or not a packet has been lost in a recent request
     */
    private boolean connectionLost = false;
    /**
     * Time when request is first sent out
     */
    private long recordedMillis = 0;
    /**
     * Master user interface used for displaying technical information to the user
     */
    private MasterUserInterface masterOutput = null;
    /**
     * Main interface used for receiving data
     */
    private ServerSocket server= null;
    /**
     * Whether or not the server should listen for incoming packets
     */
    private boolean listen = false;
    /**
     * Whether or not network thread should expire
     */
    private boolean networkClosed = false;
    /**
     * The thread that the listener runs on
     */
    private Thread serverThread = null;

    /**
     * Point of entry for server based applications
     * @param port
     */
    public NetworkUtils(int port){
        this.port = port;
    }

    /**
     * Point of entry for client based applications
     * @param address
     * @param port
     */
    public NetworkUtils(String address, int port){
        this.serverAddress = address;
        this.port = port;
    }

    /**
     * Whether or not the connection has been closed
     * @return true or false
     */
    public synchronized boolean isNetworkClosed() {
        return networkClosed;
    }

    /**
     * Closes any current networ connections
     * @param networkClosed
     */
    public synchronized void setNetworkClosed(boolean networkClosed) {
        this.networkClosed = networkClosed;
    }

    /**
     * Initialises server socket
     * @return Whether or not the operation is successful
     */
    public boolean initSocket(){
        try {
            server = new ServerSocket(this.port);

            //If it reaches this point, the server initialisation was successful, returns true to indicate success
            return true;
        } catch (IOException e) {
            if(masterOutput!=null) masterOutput.displayError("Port already occupied", "Please stop all other instances of the server.");

            //Unsuccessful return false
            return false;
        } catch(Exception e){
            if(masterOutput!=null) masterOutput.displayError("Unknown Error", e.getMessage());

            //Unsuccessful return false
            return false;
        }
    }

    /**
     * Starts listening for incoming packets, if the port is already occupied then the it will keep on re-attempting until thread is
     * interrupted
     */
    public void startListening(){

        //First checks to see if a listener is already listening, if not it will attempt to create a socket
        if(!listen) {
            listen = true;

            // will attempt to initiate socket with the method: initSocket(), if false is returned it will keep on trying

            while(!initSocket()&&  (serverThread==null ||serverThread.isInterrupted())){
                try {
                    //Lets user know it will re-attempt connection
                    if(masterOutput!=null) masterOutput.printConsole("Retrying in "+(retryPeriod/1000.0)+" seconds ...");
                    //Waits for se period
                    Thread.sleep(retryPeriod);
                } catch (InterruptedException e) {}
            }

            //If the main while loop is exited, it means that the socket has been successfully initiated

            if(masterOutput!=null) masterOutput.printConsole("Server is open for business.");

            //Starts server thread and stores the location of the thread in the variable: serverThread
            (serverThread = new Thread(serverRunnable)).start();
        }
        else{
            if(masterOutput!=null) masterOutput.printConsole("Network listener already running.");
        }
    }

    /**
     * Stops listening for incoming packets
     */
    public void stopListening(){
        if(masterOutput !=null) masterOutput.printConsole("Stopping server thread");

        try{
            //Attempts to close server thread
            server.close();
        }catch(Exception e){
            if(masterOutput !=null)masterOutput.displayError("Server Thread","Failed to close");
        }

        //Sets server listener flag to false, causing the serverThread to naturally come to an end
        listen = false;
    }

    /**
     * Runnable runs in background listening for incoming requests, notifying the appropriate listener methods
     */
    private Runnable serverRunnable = ()->{
        //Will keep on accepting packets until listen is set to false
        while(listen) {

            try {
                //Waits here until new packet is received
                Socket sock = server.accept();

                //New thread is spawned to handle incoming request
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

    /**
     * Processes an incoming request, noticing the abstract method: requestRecieved()
     * @param sock with the packet to handle
     * @return Whether request handling was successful
     */
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

    /**
     * This method is overriden by the child classes, letting them play with the incoming
     * packets
     * @param request to be dealt with
     * @return response to be sent back to the client
     */
    abstract Packet requestRecieved(Packet request);

    public int getTimeout() {
        return timeout;
    }

    /**
     * Set timeout.
     *
     * @param timeMillis the time millis
     */
    public void setTimeout(int timeMillis){
        this.timeout = timeMillis;
    }

    /**
     * Gets retry period.
     *
     * @return the retry period
     */
    public int getRetryPeriod() {
        return retryPeriod;
    }

    /**
     * Sets retry period.
     *
     * @param retryPeriod the retry period
     */
    public void setRetryPeriod(int retryPeriod) {
        this.retryPeriod = retryPeriod;
    }

    /**
     * Resets timer used for detecting request timeouts
     */
    private void resetTimer(){
        recordedMillis = System.currentTimeMillis();
    }

    /**
     * Sets master output.
     *
     * @param masterOutput the master output
     */
    public void setMasterOutput(MasterUserInterface masterOutput) {
        this.masterOutput = masterOutput;
    }

    /**
     *
      * @return Whether or not timeout has been exceeded
     */
    private boolean timeoutExceeded(){
        return (recordedMillis+timeout<System.currentTimeMillis());
    }
    private Packet  linePacket(Packet packet){
        packet.setSenderID(Constants.getUserId());
        return packet;
    }

    /**
     * Makes a structured request to a server and waits for a response
     * Periodically resending the request if a response has not been given
     * @param requestPacket packet to be sent
     * @return packet being returned
     */
    public Packet makeRequest(Packet requestPacket){
        if (serverAddress != null) {
            Packet responce = null;

            //First checks that network isn't closed
            while(!isNetworkClosed()) {
                try {

                    //Creates a new socket to output on
                    Socket sock = new Socket(serverAddress, this.port);

                    //initialises a stream that a packet can be written to
                    ObjectOutputStream out = new ObjectOutputStream(sock.getOutputStream());

                    //Sets amount of time to wait before a timeout is declared
                    sock.setSoTimeout(timeout);

                    //Adds specific client information such as the ID of the sender
                    requestPacket = linePacket(requestPacket);

                    //Sends the packet to the receiving socket
                    out.writeObject(requestPacket);

                    try {
                        sock.setSoTimeout(timeout);

                        //Waits for response packet and receives it
                        ObjectInputStream in = new ObjectInputStream(sock.getInputStream());
                        Packet ret = (Packet) in.readObject();

                        //Sets status to notify implementing classes that network connection has not been lost
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
