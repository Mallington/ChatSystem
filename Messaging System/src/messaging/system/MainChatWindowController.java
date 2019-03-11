package messaging.system;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MainChatWindowController implements ClientUserInterface, Initializable {
    @FXML
    TextFlow consoleOutput = new TextFlow();

    @FXML
    AnchorPane channelViewPane = new AnchorPane();

    public CustomListCellController<MessageComponent> channelListController = null;
    @FXML
    ListView<MessageComponent> channelList;

    @FXML
    Text channelName = new Text();

    Data dataBase =null;

    private List<ChannelViewComponent> channelViewComponents = new ArrayList<ChannelViewComponent>();

    private String currentViewID =null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        channelListController = new CustomListCellController<MessageComponent>(channelList);
        for(int i =0; i< 10; i++){
            channelListController.add(new MessageComponent("Mathew Allington"+i,"Hello","My Name is mathew",(Math.random()>0.5)));
        }


    }

    public void sendMessageTrigger(){

    }

    public void addChannel(){

    }

    public void addMember(){

    }

    private void updateChannel(ChatRoom chatRoom){
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

    private void updateView(){
        Platform.runLater(()->{
        ChannelViewComponent comp;

        if((comp =getChannelViewByID(currentViewID))!=null ){

            channelName.setText(comp.getChatRoom().getName());
            ObservableList<Node> nodeList = channelViewPane.getChildren();
            nodeList.remove(0, nodeList.size());
            try {
                comp.getNode().setLayoutX(channelViewPane.getParent().getLayoutX());
                comp.getNode().setLayoutY(channelViewPane.getParent().getLayoutY());
                nodeList.add(comp.getNode());
            } catch (IOException e) {
                displayError("Node Update Failed", "ChannelID: "+currentViewID);
            }
        }
        });
    }

    @Override
    public void updateChatRoomList(List<ChatRoom> roomList) {
        for(ChatRoom room : roomList){
            updateChannel(room);
        }

        updateView();
    }

    @Override
    public Data getDataBase() {
        return dataBase;
    }

    @Override
    public void update(Message oldMessage, Message newMessage) {
        Platform.runLater(()->{
        ChannelViewComponent channelView = getChannelViewByID(newMessage.getRoomID());

        if(channelView !=null){
            try {
                channelView.getController().updateMessage(newMessage);
            } catch (IOException e) {
                displayError("Channel View Controller","Failed to obtain: "+newMessage.getRoomID());
            }
        }
        else{
            displayError("Channel View Not Found","Could not find: "+newMessage.getRoomID());
        }

        });
    }

    @Override
    public void update(User oldUser, User newUser) {

    }

    @Override
    public void printConsole(String consoleMessage) {
        Platform.runLater(() ->consoleOutput.getChildren().addAll(new Text(consoleMessage)));
    }

    @Override
    public void displayError(String title, String body) {
        Platform.runLater( ()->{
            consoleOutput.getChildren().addAll(new Text("\nOOPs:["+title+"]\n"+body));
        });
    }

    public void setDataBase(Data dataBase) {
        this.dataBase = dataBase;
    }
}
