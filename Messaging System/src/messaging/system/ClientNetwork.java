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
     * The Updater task.
     */
    private Runnable updaterTask = () ->{
        while(updateDB && !isNetworkClosed()){

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
     * Start updater task.
     *
     * @param dataBase the data base
     */
    public void startUpdaterTask(Data dataBase){
        if(!updateDB){
            this.dataBase = dataBase;
            updateDB = true;
            new Thread(updaterTask).start();
        }
    }

    /**
     * Stop updater task.
     */
    public void stopUpdaterTask(){
        updateDB = false;
    }

    /**
     * Update chat room.
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

    @Override
    Packet requestRecieved(Packet request) {
        Packet returnPacket = new Packet(Constants.Header.UNEXPECTED_PACKET);
        return returnPacket;
    }

    /**
     * Create resource packet.
     *
     * @param resource the resource
     * @param header   the header
     * @return the packet
     */
    private Packet createResource(Object resource, Constants.Header header){
        Packet request = new Packet(header);
        request.setPayload(resource);
        Packet response = makeRequest(request);
        return (response);
    }

    /**
     * Create user boolean.
     *
     * @param user the user
     * @return the boolean
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
     * Send message boolean.
     *
     * @param message the message
     * @return the boolean
     */
    public boolean sendMessage(Message message){
        Packet response = (createResource(message, Constants.Header.SEND_MESSAGE));
        return (response.getHeaderType()).equals(Constants.Header.SUCCESS);
    }

    /**
     * Sets update period millis.
     *
     * @param updatePeriodMillis the update period millis
     */
    public void setUpdatePeriodMillis(int updatePeriodMillis) {
        this.updatePeriodMillis = updatePeriodMillis;
    }


}
