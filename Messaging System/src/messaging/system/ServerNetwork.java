package messaging.system;

import java.util.ArrayList;
import java.util.List;

/**
 * The main 'command center' for handling and routing requests
 * This class handles the main logic for all incoming traffic
 */
public class ServerNetwork extends NetworkUtils {
    /**
     * Used for displaying server related changes
     */
    private ServerUserInterface serverUserInterface = null;
    /**
     * Data Base pointer
     */
    private Data dataBase;

    /**
     * Instantiates a new Server network.
     *
     * @param port     the port number
     * @param dataBase the data base pointer
     */
    public ServerNetwork(int port, Data dataBase) {
        super(port);
        this.dataBase = dataBase;
    }

    /**
     * This particular implementation handles a variety of different requests
     * Each one depending on the type of header in the packet
     * @param request to be dealt with
     * @return the returned packet
     */
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
                    return new Packet(Constants.Header.INVALID_HEADER);
            }
        } catch (Exception e) {
            if(serverUserInterface!=null) serverUserInterface.displayError("Packet Parse Error", e.getLocalizedMessage());
            return new Packet(Constants.Header.FAIL);
        }

    }

    /**
     * Either adds or creates a new user
     * @param request to be handled
     * @return Either SUCCESS or FAIL packet is sent back
     */
    private Packet handleUserUpdate(Packet request) {
        if (request != null && request.getPayload() != null) {
            try {
                Packet response = new Packet(Constants.Header.SUCCESS);

                User user = (User) request.getPayload();
                String UID = dataBase.updateUser(user);
                response.setPayload(UID);

                //Updates amount of users
                serverUserInterface.displaysUserPopulation(dataBase.getUsers().size());

                return response;
            } catch (Exception e) {
                return new Packet(Constants.Header.FAIL);
            }
        } else {
            return new Packet(Constants.Header.FAIL);
        }
    }

    /**
     * Either adds or creates a message
     * @param request to be handled
     * @return Either SUCCESS or FAIL packet is sent back
     */
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
    /**
     * Sends back an update for a specific ChatRoom
     * @param request to be handled
     * @return Either SUCCESS or FAIL packet is sent back
     */
    private Packet handleUpdateRequest(Packet request) {

        try {
            Packet response = new Packet(Constants.Header.SUCCESS);
            String roomID = (String) request.getPayload();
            response.setPayload(dataBase.getChatRoomByID(roomID));
            return response;
        } catch (Exception e) {
            return new Packet(Constants.Header.FAIL);
        }

    }
    /**
     * Sends back updates for messages and users
     * @param request to be handled
     * @return Either SUCCESS or FAIL packet is sent back
     */
    private Packet handleUpdateDBRequest(Packet request) {

        try {
            UpdateRequestContainer cont = (UpdateRequestContainer) request.getPayload();

            List<Message> messages = new ArrayList<Message>();
            List<User> users = new ArrayList<User>();

            for (String id : cont.getMessagesToFetch()) {
                for (Message m : dataBase.getMessages()) if (m.getMessageID().equals(id)) messages.add(m);
            }

            for (String id : cont.getUsersToFetch()) {
                for (User u : dataBase.getUsers()) if (u.getUserID().equals(id)) users.add(u);
            }

            Packet response = new Packet(Constants.Header.SUCCESS);
            response.setPayload(new UpdateResponceContainer(users, messages));
            return response;

        } catch (Exception e) {
            return new Packet(Constants.Header.FAIL);
        }

    }

    /**
     * Handle user login request
     *
     * @param request the request
     * @return Either SUCCESS or FAIL packet is sent back
     */
    public Packet handleUserLoginRequest(Packet request) {
        Packet response = new Packet(Constants.Header.FAIL);
        try {
            if (dataBase.containsUser(request.toString())) {
                response = new Packet(Constants.Header.SUCCESS);
            }
        }
           catch(Exception e){
               System.out.println("User Verification Failed");
           }
            return response;
        }

    /**
     * Handle logoff request
     *
     * @param request the request
     * @return Either SUCCESS or FAIL packet is sent back
     */
    public Packet handleUserLogoffRequest(Packet request) {
        Packet response = new Packet(Constants.Header.FAIL);
        try {
            String UID = request.toString();

            if (dataBase.removeUser(UID)) {

                response = new Packet(Constants.Header.SUCCESS);
            }
        }
        catch(Exception e){
            System.out.println("User Logoff Failed");
        }
        return response;
    }

    /**
     * Sets server user interface.
     *
     * @param serverUserInterface the server user interface
     */
    public void setServerUserInterface(ServerUserInterface serverUserInterface) {
        this.serverUserInterface = serverUserInterface;
        super.setMasterOutput(serverUserInterface);
    }
}
