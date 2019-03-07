/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package messaging.system;

/**
 *
 * @author mathew
 */
public class ChatClient {
     /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException {
        Constants.updateConstants(args, Constants.NodeType.ChatClient);

        boolean runUI;
        if(Constants.getRunGui()==null) runUI = ConsoleUtils.getYesNoChoice("Would you like to run the GUI?");
        else runUI = Constants.getRunGui();


        if (runUI) {
            ChatClientGUI.main(args);
        }
        else {

            ClientNetwork client = ClientUtils.initNetwork();
            Data db = new Data();

            ClientConsole consoleInst = ClientUtils.initConsole(client);

            if (consoleInst != null) {consoleInst.startConsoleListener();}

            ClientUtils.initInterface(consoleInst, client);


        }
    }


}
