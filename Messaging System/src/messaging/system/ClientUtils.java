package messaging.system;

/**
 * This class contains methods associated with setting up the client
 */
public class ClientUtils {

    /**
     * Sets up the main logic for the console, providing an interface to send messages and Exit the program
     *
     * @param client the client
     * @return the client console
     */
    public static ClientConsole initConsole(ClientNetwork client){
        return new ClientConsole() {
            @Override
            public void userInputted(String userInput, ConsoleUtils console) {
                console.printConsole("Inputted: "+userInput);
                switch (userInput.toLowerCase().trim()) {
                    case "exit":
                        console.printConsole("Exiting...");
                        client.stopUpdaterTask();
                        client.setNetworkClosed(true);
                        console.stopConsoleListener();
                        break;
                    default:
                        if(!client.isNetworkClosed() && !client.isConnectionLost()) {
                            Message toSend = new Message();
                            toSend.setBody(userInput);
                            toSend.setRoomID(Constants.DEFAULT_CHAT_ROOM_ID);
                            toSend.setSenderID(Constants.getUserId());
                            client.sendMessage(toSend);
                        }
                        else{
                            console.printConsole("NOT SENT - Please wait for client to connect again.");
                        }
                        break;


                }
            }
        };
    }

    /**
     * Initialise a new network client
     *
     * @return the client network object
     */
    public static ClientNetwork initNetwork(){
        ClientNetwork client = new ClientNetwork(Constants.getServerAddress(), Constants.getPort());
        client.setTimeout(5000);
        return client;
    }

    /**
     * Creates a new database, sets client console listeners, creates a new user and starts the update requests
     *
     * @param clientInterface the client interface
     * @param client          the client
     */
    public static void initInterface(ClientUserInterface clientInterface, ClientNetwork client){
        Data db = new Data();
        clientInterface.setDataBase(db);
        db.addListener(clientInterface);

        client.setUserInterface(clientInterface);

        User newUser = new User(null, "Mark Allington- " + (int) (Math.random() * 9999));
        if (client.createUser(newUser)) {
            clientInterface.printConsole("User Created.");
        } else {
            if(!client.isNetworkClosed())clientInterface.displayError("User Creation Failed", "Perhaps you do not have the right privileges");
        }

        client.startUpdaterTask(db);
    }
}
