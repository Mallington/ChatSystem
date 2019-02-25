/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package messaging.system;

import java.io.IOException;

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
        
        ClientConsole console = new ClientConsole();
        Data db = new Data(console);
        db.addListener(console);
        
        ClientNetwork client = new ClientNetwork(Constants.getServerAddress(), Constants.getPort(), console);

        Packet req = new Packet(Constants.Header.UPDATE);
        req.setPayload("RR1");


        client.setTimeout(5000);

        try{
            while (true) {
                Packet resp = client.makeRequest(req);
                ChatRoom ch = (ChatRoom) resp.getPayload();
                db.updateAndFetchdDB(ch, client);
                Thread.sleep(500);
            }
        }
        catch(Exception e){
            console.displayError("FAIL", e.getLocalizedMessage());
        }

        //request.setPayload(new Message());





    }
}
