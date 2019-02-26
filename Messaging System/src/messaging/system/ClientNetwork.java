/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package messaging.system;

import java.io.IOException;
import java.net.ServerSocket;

/**
 *
 * @author mathew
 */
public class ClientNetwork extends NetworkUtils{
    
    private ClientUserInterface userInterface;
    private boolean updateDB = false;
    private int updatePeriodMillis = 250;
    private Data dataBase;
    
    public ClientNetwork(String serverAddress, int port, ClientUserInterface user, Data dataBase){
        super(serverAddress, port, user);
        this.dataBase = dataBase;
        this.userInterface = user;
        userInterface.printConsole("Attempting to connect via \""+serverAddress+":"+""+port+"\" as \""+Constants.getUserId()+"\".");
    }
    
    private void connect() throws IOException{
        ServerSocket sock = new ServerSocket();
    }

    private Runnable updaterTask = () ->{
        while(updateDB){
            for(ChatRoom room :dataBase.getChatRooms()){
                updateChatRoom(room);
            }

            try{Thread.sleep(updatePeriodMillis);} catch(Exception e){updateDB = false;}
        }
    };

    public void startUpdaterTask(){
        if(!updateDB){
            updateDB = true;
            new Thread(updaterTask).start();
        }
    }

    public void stopUpdaterTask(){
        updateDB = false;
    }

    private void updateChatRoom(ChatRoom room){
        try {
            Packet req = new Packet(Constants.Header.UPDATE);
            req.setPayload(room.getRoomID());
            Packet resp = makeRequest(req);
            ChatRoom ch = (ChatRoom) resp.getPayload();

            dataBase.updateAndFetchdDB(ch, this);
        }
        catch(Exception e){
            userInterface.displayError("Failed to update chat room","Server is being funny");
        }
    }

    @Override
    Packet requestRecieved(Packet request) {
        Packet returnPacket = new Packet(Constants.Header.UNEXPECTED_PACKET);
        return returnPacket;
    }

    private Packet createResource(Object resource, Constants.Header header){
        Packet request = new Packet(header);
        request.setPayload(resource);
        Packet response = makeRequest(request);
        return (response);
    }

    public boolean createUser(User user){
        Packet response = createResource(user, Constants.Header.CREATE_USER);
        try{
            if(response != null && response.getPayload()!=null){
                Constants.setUserId((String)response.getPayload());

                return (response.getHeaderType().equals(Constants.Header.SUCCESS));
            }
        } catch(Exception e){
            return false;
        }
        return false;
    }
    public boolean sendMessage(Message message){
        Packet response = (createResource(message, Constants.Header.SEND_MESSAGE));
        return (response.getHeaderType()).equals(Constants.Header.SUCCESS);
    }

    public void setUpdatePeriodMillis(int updatePeriodMillis) {
        this.updatePeriodMillis = updatePeriodMillis;
    }
}
