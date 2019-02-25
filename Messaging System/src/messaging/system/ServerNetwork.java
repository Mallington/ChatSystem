package messaging.system;

import java.util.ArrayList;
import java.util.List;

import static messaging.system.Constants.Header.FAIL;
import static messaging.system.Constants.Header.INVALID_HEADER;
import static messaging.system.Constants.Header.SUCCESS;

public class ServerNetwork extends NetworkUtils {

    private ServerUserInterface serverUserInterface;
    private Data dataBase;

    public ServerNetwork(int port,ServerUserInterface serverInterface, Data dataBase) {
        super(port, serverInterface);
        this.serverUserInterface = serverInterface;
        this.dataBase = dataBase;
    }

    @Override
    Packet requestRecieved(Packet request) {
        try {
            switch (request.getHeaderType()) {

                case SEND_MESSAGE:
                    serverUserInterface.printConsole("Request Type: "+request.getHeaderType()
                            +"\nSender ID:"+request.getSenderID()
                            +"\nPayload Type:"+((request.getPayload() !=null) ? request.getPayload().getClass().toString():"Payload Empty"));
                    return new Packet(SUCCESS);

                case UPDATE_DB:
                    return handleUpdateDBRequest(request);

                case UPDATE:
                    return handleUpdateRequest(request);

                default:
                    return new Packet(INVALID_HEADER);
            }
        }
        catch (Exception e){
            serverUserInterface.displayError("Packet Parse Error",e.getLocalizedMessage());
            return new Packet(FAIL);
        }

    }

    private Packet handleUpdateRequest(Packet pack){

        try{
            Packet  response = new Packet(SUCCESS);
            String roomID = (String)pack.getPayload();
            response.setPayload(dataBase.getChatRoomByID(roomID));
            return response;
        }
        catch(Exception e){
            return new Packet(FAIL);
        }

    }

    private Packet handleUpdateDBRequest(Packet pack){

        try{
            UpdateRequestContainer cont = (UpdateRequestContainer) pack.getPayload();

            List<Message> messages = new ArrayList<Message>();
            List<User> users = new ArrayList<User>();

            for(String id : cont.messagesToFetch) {
                for(Message m : dataBase.getMessages()) if(m.getMessageID().equals(id)) messages.add(m);
            }

            for(String id : cont.usersToFetch) {
                for(User u : dataBase.getUsers()) if(u.getUserID().equals(id)) users.add(u);
            }

            Packet response = new Packet(SUCCESS);
            response.setPayload(new UpdateResponceContainer(users, messages));
            return response;

        }
        catch (Exception e){
           return new Packet(FAIL);
        }

    }
}
