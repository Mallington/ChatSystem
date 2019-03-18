package messaging.system;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * This is the main operations centre for the GUI, it is responsible for updating the main channel view, the list of
 * channels in the left sidebar, the console and taking in input from the user
 */
public class MainChatWindowController implements ClientUserInterface, Initializable {
    /**
     * The text flow responsible for outputting console messages
     */
    @FXML
    private TextFlow consoleOutput = new TextFlow();

    /**
     * This is where the Channel View Components are displayed
     */
    @FXML
    private AnchorPane channelViewPane = new AnchorPane();

    /**
     * The Channel name.
     */
    @FXML
    private Text channelName = new Text();

    /**
     * Text field responsible for inputting user text
     */
    @FXML
    private TextField messageInput = new TextField();
    /**
     * Pointer to the data base
     */
    private Data dataBase =null;

    /**
     * Contains all of the active channel components that are responsible for displaying a channel's messages
     */
    private List<ChannelViewComponent> channelViewComponents = new ArrayList<ChannelViewComponent>();

    /**
     * Current channel to be displayed
     */
    private String currentViewID =null;

    /**
     * Network instance to be used to send messages
     */
    private ClientNetwork network = null;



    /**
     * List view of components displaying the statuses of each channel in the left sidebar
     */
    @FXML
    private ListView<GroupComponent> channelList;
    /**
     * List of components displaying the statuses of each channel in the left sidebar
     */
    private List<GroupComponent> groupListViewComponents = new ArrayList<>();
    /**
     * Controls the list view containing the statuses of each channel
     */
    private CustomListCellController<GroupComponent> channelListController = null;




    /**
     * List view of components displaying the statuses of each user on the server
     */
    @FXML
    private ListView<UserComponent> userListView;

    /**
     * Controls the list view containing the statuses of each user
     */
    private CustomListCellController<UserComponent> userListController = null;

    /**
     * Used for sending notification
     */
    private NotificationManager noticationController;



    /**
     * Initialises the controller
     * @param location Not used
     * @param resources Not used
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            channelListController = new CustomListCellController<GroupComponent>(channelList);
            userListController = new CustomListCellController<UserComponent>(userListView);
            noticationController = new NotificationManager();
        }catch(Exception e){
            System.out.println("Init in Main Controller failed!");
        }
    }

    /**
     * Sets channel name.
     *
     * @param channelName the channel name
     */
    public void setChannelName(String channelName) {
        this.channelName.setText(channelName);
    }

    /**
     * Sets network
     * @param network
     */
    public void setNetwork(ClientNetwork network) {
        this.network = network;
    }

    /**
     * Method is triggered every time a key is pressed.
     * When the enter key is pressed, a message will be sent depending on whether or not the network is currently
     * connected
     *
     * @param event the key pressed
     */
    public void sendMessageTrigger(KeyEvent event){
        if(event.getCode().equals(KeyCode.ENTER)){
            if(network !=null){
               if(!messageInput.equals("") && messageInput!=null && !network.isConnectionLost() &&!network.isNetworkClosed()){
                    Message m = new Message();
                    m.setBody(messageInput.getText());
                    m.setRoomID(currentViewID);
                    m.setSenderID(Constants.getUserId());
                    network.sendMessage(m);
                    messageInput.setText(null);
                }
                else{
                    printConsole("Message not sent:");
                    printConsole("Please wait for connection");
                }

            }
        }

    }

    /**
     * Sets the event handler which needs to be actioned upon the window exiting
     * @param handler the handler
     */
    public void setOnClose(EventHandler handler){
        messageInput.getScene().getWindow().setOnCloseRequest(handler);
    }

    /**
     * Triggered by the add channel button
     */
    public void addChannel(){
        //For future development
    }

    /**
     * Triggered by the add user button
     */
    public void addMember(){
        //For future development
    }

    /**
     * Assembles data and sends a notification with derived from the contents of the message object
     * @param message to be in notification
     */
    public void sendNotification(Message message){
        try {
            String sender = dataBase.getUserByID(message.getSenderID()).getDisplayName();
            String room = dataBase.getChatRoomByID(message.getRoomID()).getName();

            noticationController.sendNotification("From: " + sender, "Room: " + room, message.getBody());
        } catch(Exception e){

        }
    }

    /**
     * Either adds a chat room and adds the corresponding components or updates existing ones
     * @param chatRoom
     */
    private void updateChannel(ChatRoom chatRoom){
        //Updates the channel view component
            ChannelViewComponent channelComp;
            if((channelComp = getChannelViewByID(chatRoom.getRoomID())) !=null) {
                channelComp.setChatRoom(chatRoom);
            }
            else {

                ChannelViewComponent channelViewComponent = new ChannelViewComponent(chatRoom);
                try {
                    channelViewComponent.getController().setDataBase(dataBase);
                } catch (IOException e) {
                    displayError("Data Error","Failed to set database");
                }
                channelViewComponents.add(channelViewComponent);
                currentViewID = channelViewComponent.getChannelID();

                GroupComponent channelView = new GroupComponent(channelViewComponent.getChatRoom().getName(), channelViewComponent.getChatRoom().getUserIDS().size());
                channelView.setChannelID(channelViewComponent.getChannelID());
                channelListController.add(channelView);
                groupListViewComponents.add(channelView);
            }


        //Updates the channel view sidebar
        try {
            if (null != groupListViewComponents) {
                for (GroupComponent sideView : groupListViewComponents) {
                    if (sideView != null && chatRoom != null && sideView.getChannelID().equals(chatRoom.getRoomID())) {
                        try {
                            sideView.getController().setGroupName(chatRoom.getName());
                            sideView.getController().setNumberOfPeople(chatRoom.getUserIDS().size());
                        } catch (IOException e) {
                            System.out.println("Error, could not set group component");
                        }

                    }
                }
            }
        } catch(Exception e){

        }
    }

    private ChannelViewComponent getChannelViewByID(String ID){
        for(ChannelViewComponent channelComp : channelViewComponents){
            if(channelComp.getChannelID().equals(ID)) {
                return channelComp;
            }
        }

        return null;
    }

    /**
     * Sets the channel name text, the main view and the sidebars
     */
    private void updateView(){
        Platform.runLater(()->{
        ChannelViewComponent comp;

        if((comp =getChannelViewByID(currentViewID))!=null ){

            channelName.setText(comp.getChatRoom().getName());
            ObservableList<Node> nodeList = channelViewPane.getChildren();
            nodeList.remove(0, nodeList.size());
            try {

                comp.getController().bindPane(channelViewPane);

                nodeList.add(comp.getNode());
            } catch (IOException e) {
                displayError("Node Update Failed", "ChannelID: "+currentViewID);
            }
        }
        });
    }

    /**
     * Updates each channel and there associated component
     * @param roomList the room list
     */
    @Override
    public void updateChatRoomList(List<ChatRoom> roomList) {
        for(ChatRoom room : roomList){
            updateChannel(room);
        }

        updateView();
    }

    /**
     * Gets database instance
     * @return database
     */
    @Override
    public Data getDataBase() {
        return dataBase;
    }

    /**
     * Adds the new and updates the old messages
     * @param oldMessage
     * @param newMessage
     */
    @Override
    public void update(Message oldMessage, Message newMessage) {
        Platform.runLater(()->{
        ChannelViewComponent channelView = getChannelViewByID(newMessage.getRoomID());

        if(channelView !=null){
            try {
                channelView.getController().updateMessage(newMessage);
                sendNotification(newMessage);
            } catch (IOException e) {
                displayError("Channel View Controller","Failed to obtain: "+newMessage.getRoomID());
            }
        }
        else{
            displayError("Channel View Not Found","Could not find: "+newMessage.getRoomID());
        }

        });
    }
    /**
     * Adds the new and updates the old user
     * @param oldUser
     * @param newUser
     */
    @Override
    public void update(User oldUser, User newUser) {
        try {
            if (newUser != null) {
                Platform.runLater(()->userListController.add(new UserComponent(newUser.getDisplayName(), newUser.getUserID())));
            }
        }catch(Exception e){

        }
    }

    @Override
    public void printConsole(String consoleMessage) {
        if(!consoleMessage.contains("\n")) consoleMessage += '\n';
        Text t = new Text(consoleMessage);
        t.setFill(Paint.valueOf("white"));
        Platform.runLater(() ->consoleOutput.getChildren().addAll(t));
    }

    @Override
    public void displayError(String title, String body) {
        Text t = new Text("\nOOPs:["+title+"]\n"+body+"\n");
        t.setFill(Paint.valueOf("white"));
        Platform.runLater( ()->{
            consoleOutput.getChildren().addAll(t);
        });
    }

    /**
     * Sets database instanse
     * @param dataBase the data base
     */
    public void setDataBase(Data dataBase) {
        this.dataBase = dataBase;
    }
}
