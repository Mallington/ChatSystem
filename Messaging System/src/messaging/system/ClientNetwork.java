/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package messaging.system;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * This class is responsible for CRUD requests to the server
 *
 * @author mathew
 */
public class ClientNetwork extends NetworkUtils{

    /**
     * Generic user interface instance used for outputting console messages
     */
    private ClientUserInterface userInterface = null;
    /**
     * Whether or not the data base needs to be updated
     */
    private boolean updateDB = false;
    /**
     * The set time period at which each update should be requested from the server
     */
    private int updatePeriodMillis = 250;
    /**
     * Pointer to the database to be updated
     */
    private Data dataBase = null;

    /**
     * The thread on which the updater task is run.
     */
    private Thread updaterThread = null;

    /**
     * Instantiates a new Client network.
     *
     * @param serverAddress the server address
     * @param port          the port
     */
    public ClientNetwork(String serverAddress, int port){
        super(serverAddress, port);
    }

    /**
     * Instantiates a new socket
     * @throws IOException the io exception
     */
    private void connect() throws IOException{
        if(userInterface !=null)userInterface.printConsole("Attempting to connect via \""+serverAddress+":"+""+port+"\" as \""+Constants.getUserId()+"\".");

        ServerSocket sock = new ServerSocket();
    }

    /**
     * Sets the user interface.
     *
     * @param userInterface the user interface
     */
    public void setUserInterface(ClientUserInterface userInterface) {
        this.userInterface = userInterface;
        super.setMasterOutput(userInterface);
    }

    /**
     * Runnable runs on an new thread when the listener is requested
     */
    private Runnable updaterTask = () ->{
        while(updateDB && !isNetworkClosed() && !updaterThread.isInterrupted()){

            for(ChatRoom room :dataBase.getChatRooms()){
                if(!isNetworkClosed()) updateChatRoom(room);
            }
            userInterface.updateChatRoomList(dataBase.getChatRooms());

            try{Thread.sleep(updatePeriodMillis);} catch(Exception e){
                updateDB = false;
                System.out.println("Updater thread died");
            }
        }
    };

    /**
     * Starts the updater task. This task regularly requests updates from the server regarding the status of
     * particular chat rooms that is knows about. If any messages, users need to be update or added then this
     * will be done on the update thread.
     *
     * @param dataBase the data base
     */
    public void startUpdaterTask(Data dataBase){
        if(!updateDB){
            this.dataBase = dataBase;
            updateDB = true;
            (updaterThread = new Thread(updaterTask)) .start();
        }
    }

    /**
     * Stops the updater task
     */
    public void stopUpdaterTask(){
        updateDB = false;
        //updaterThread.interrupt();
    }

    /**
     * Updates the status of the chat room by making a request to the server
     *
     * @param room the room
     */
    private void updateChatRoom(ChatRoom room){
        try {
            Packet req = new Packet(Constants.Header.UPDATE);
            req.setPayload(room.getRoomID());
            Packet resp = makeRequest(req);
            ChatRoom ch = (ChatRoom) resp.getPayload();

            dataBase.updateAndFetchdDB(ch, this);
        }
        catch(Exception e){
            if(userInterface !=null && !isNetworkClosed()) userInterface.displayError("Failed to update chat room","Server is being funny");
        }
    }

    /**
     * In this implementation, a packet should not be expected. Hence an 'UNEXPECTED_PACKET' is sent back.
     * This particular part of the protocol can be used when scanning for available servers on the network,
     * an unexpected packet would tell the scanner that this is in fact a client, not a server.
     * @param request Recieved packet
     * @return Outgoing packet
     */

    @Override
    Packet requestRecieved(Packet request) {
        Packet returnPacket = new Packet(Constants.Header.UNEXPECTED_PACKET);
        return returnPacket;
    }

    /**
     * Loads a packet with payloads such as User and Message and objects
     *
     * @param resource the object to be loaded
     * @param header   the header which denotes the type of resource the server should expect
     * @return the loaded packet
     */
    private Packet createResource(Object resource, Constants.Header header){
        Packet request = new Packet(header);
        request.setPayload(resource);
        Packet response = makeRequest(request);
        return (response);
    }

    /**
     * Sends a request to the server, asking it whether the client can make a new user
     *
     * @param user the user to be made
     * @return the boolean denote whether the operation was successful
     */
    public boolean createUser(User user){
        Packet response = createResource(user, Constants.Header.UPDATE_USER);
        try{
            if(response != null && response.getPayload()!=null){
                Constants.setUserId((String)response.getPayload());
                return (response.getHeaderType().equals(Constants.Header.SUCCESS));
            }
        } catch(Exception e){
            System.out.println(e.getCause());
            return false;
        }
        return false;
    }

    /**
     * Sends a peopleOnline request to the server
     *
     * @param message the peopleOnline to be sent
     * @return whether the peopleOnline request was successful
     */
    public boolean sendMessage(Message message){
        Packet response = (createResource(message, Constants.Header.SEND_MESSAGE));
        return (response.getHeaderType()).equals(Constants.Header.SUCCESS);
    }

    /**
     * Sets update period millis
     *
     * @param updatePeriodMillis the update period in milliseconds
     */
    public void setUpdatePeriodMillis(int updatePeriodMillis) {
        this.updatePeriodMillis = updatePeriodMillis;
    }


}
