package messaging.system;

import java.util.ArrayList;
import java.util.List;

import static messaging.system.Constants.Header.FAIL;
import static messaging.system.Constants.Header.INVALID_HEADER;
import static messaging.system.Constants.Header.SUCCESS;

public class ServerNetwork extends NetworkUtils {

    private ServerUserInterface serverUserInterface = null;
    private Data dataBase;

    public ServerNetwork(int port, Data dataBase) {
        super(port);
        this.dataBase = dataBase;
    }

    @Override
    Packet requestRecieved(Packet request) {
        try {
            switch (request.getHeaderType()) {

                case SEND_MESSAGE:
                    return handleMessageUpdate(request);

                case UPDATE_DB:
                    return handleUpdateDBRequest(request);

                case UPDATE:
                    return handleUpdateRequest(request);

                case UPDATE_USER:
                    return handleUserUpdate(request);

                case LOGIN_USER:
                    return handleUserLoginRequest(request);

                default:
                    return new Packet(INVALID_HEADER);
            }
        } catch (Exception e) {
            if(serverUserInterface!=null) serverUserInterface.displayError("Packet Parse Error", e.getLocalizedMessage());
            return new Packet(FAIL);
        }

    }

    private Packet handleUserUpdate(Packet request) {
        if (request != null && request.getPayload() != null) {
            try {
                Packet response = new Packet(Constants.Header.SUCCESS);

                User user = (User) request.getPayload();
                String UID = dataBase.updateUser(user);
                response.setPayload(UID);

                return response;
            } catch (Exception e) {
                return new Packet(Constants.Header.FAIL);
            }
        } else {
            return new Packet(Constants.Header.FAIL);
        }
    }

    private Packet handleMessageUpdate(Packet request) {
        if (request != null && request.getPayload() != null) {
            try {
                Packet response = new Packet(Constants.Header.SUCCESS);

                Message message = (Message) request.getPayload();
                message.setMessageID("TBC");
                if(serverUserInterface!=null) serverUserInterface.printConsole("Routing Message:\n" + message.toString());
                dataBase.updateMessage(message);
                return response;
            } catch (Exception e) {
                return new Packet(Constants.Header.FAIL);
            }
        } else {
            return new Packet(Constants.Header.FAIL);
        }
    }

    private Packet handleUpdateRequest(Packet pack) {

        try {
            Packet response = new Packet(SUCCESS);
            String roomID = (String) pack.getPayload();
            response.setPayload(dataBase.getChatRoomByID(roomID));
            return response;
        } catch (Exception e) {
            return new Packet(FAIL);
        }

    }

    private Packet handleUpdateDBRequest(Packet pack) {

        try {
            UpdateRequestContainer cont = (UpdateRequestContainer) pack.getPayload();

            List<Message> messages = new ArrayList<Message>();
            List<User> users = new ArrayList<User>();

            for (String id : cont.messagesToFetch) {
                for (Message m : dataBase.getMessages()) if (m.getMessageID().equals(id)) messages.add(m);
            }

            for (String id : cont.usersToFetch) {
                for (User u : dataBase.getUsers()) if (u.getUserID().equals(id)) users.add(u);
            }

            Packet response = new Packet(SUCCESS);
            response.setPayload(new UpdateResponceContainer(users, messages));
            return response;

        } catch (Exception e) {
            return new Packet(FAIL);
        }

    }

    public Packet handleUserLoginRequest(Packet request) {
        Packet response = new Packet(FAIL);
        try {
            if (dataBase.containsUser(request.toString())) {
                response = new Packet(SUCCESS);
            }
        }
           catch(Exception e){
               System.out.println("User Verification Failed");
           }
            return response;
        }


    public void setServerUserInterface(ServerUserInterface serverUserInterface) {
        this.serverUserInterface = serverUserInterface;
        super.setMasterOutput(serverUserInterface);
    }
}
