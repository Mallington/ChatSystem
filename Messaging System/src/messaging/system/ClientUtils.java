package messaging.system;

public class ClientUtils {

    public static ClientConsole initConsole(ClientNetwork client){
        return new ClientConsole() {
            @Override
            public void userInputted(String userInput, ConsoleUtils console) {
                switch (userInput.toLowerCase().trim()) {
                    case "exit":
                        console.printConsole("Exiting...");
                        client.stopUpdaterTask();
                        client.setNetworkClosed(true);
                        console.stopConsoleListener();
                        break;
                    default:
                        Message toSend = new Message();
                        toSend.setBody(userInput);
                        toSend.setRoomID(Constants.DEFAULT_CHAT_ROOM_ID);
                        toSend.setSenderID(Constants.getUserId());
                        client.sendMessage(toSend);
                        break;


                }
            }
        };
    }

    public static ClientNetwork initNetwork(){
        ClientNetwork client = new ClientNetwork(Constants.getServerAddress(), Constants.getPort());
        client.setTimeout(5000);
        return client;
    }

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
