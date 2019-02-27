/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package messaging.system;

import java.io.Serializable;

/**
 *
 * @author mathew
 */
public class Constants {

    public final static String DEFAULT_CHAT_ROOM_ID = "f09f22a3-de09-4830-ae58-b0dc91d41e67";

    private static String SERVER_ADDRESS = "localhost";
    private static int PORT = 14005;

    private static String USER_ID = "GUEST";

    public enum NodeType {
        ChatServer, ChatClient
    }
    
    public enum Header implements Serializable{
        UPDATE, UPDATE_USER, LOGIN_USER, SEND_MESSAGE, UPDATE_DB, NOT_AUTHORISED, SUCCESS, INVALID_HEADER, UNEXPECTED_PACKET, FAIL
    }


    public static void updateConstants(String[] args, NodeType type) {

        ParameterParser parser = new ParameterParser();

        switch (type) {
            case ChatServer:
                parser.add(new Parameter("csp", false));
                Integer cspArg = null;

                if (parser.parseParameters(args) && (cspArg = parser.getID("csp").parseInteger()) != null) {
                    PORT = cspArg;
                }

                break;
            case ChatClient:

                parser.add(new Parameter("ccp", false))
                        .add(new Parameter("cca", false))
                        .add(new Parameter("user", false));
                String ccaArg = null;
                Integer ccpArg = null;

                boolean sucessfulParse = parser.parseParameters(args);

                if ((sucessfulParse) && (ccpArg = parser.getID("ccp").parseInteger()) != null) {
                    PORT = ccpArg;
                }
                if (sucessfulParse && (ccaArg = parser.getID("cca").getValue()) != null) {
                    SERVER_ADDRESS = ccaArg;
                }
                if (sucessfulParse && (ccaArg = parser.getID("user").getValue()) != null) {
                    USER_ID = ccaArg;
                }
                break;
        }
    }
    
    public static String getServerAddress(){
        return SERVER_ADDRESS;
    }
    
    public static int getPort(){
        return PORT;
    }

    public static String getUserId() {return USER_ID;}

    public static void setUserId(String userId) {
        USER_ID = userId;
    }
}
