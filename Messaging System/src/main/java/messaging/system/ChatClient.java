/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package messaging.system;

/**
 * Main entry point for the ChatClient, will either run the GUI or the commandline
 * interface according to the -GUI parameter (true or false)
 * @author mathew
 */
public class ChatClient {
     /**
      *Parameters:
      * -GUI (Optional): Accepts true or false
      * -cca (Optional): Address in the form of an IP address or another form
      * -ccp (Optional): Port to be connected to
      * -user (Optional): Specified the uniquely assigned ID for an existing user
      * -name (Optional): Sets the display name of the user
      *
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException {
        Constants.updateConstants(args, Constants.NodeType.ChatClient);

        if (GeneralUtils.resolveRunGUIParam()) {
            ChatClientGUI.main(args);
        }
        else {

            ClientNetwork client = ClientUtils.initNetwork();
            Data db = new Data();

            ClientConsole consoleInst = ClientUtils.initConsole(client);
            consoleInst.printConsole(Constants.CLIENT_ASCII);

            if (consoleInst != null) {consoleInst.startConsoleListener();}

            ClientUtils.initInterface(consoleInst, client,db);


        }
    }


}
