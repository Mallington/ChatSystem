/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package messaging.system;

import java.io.Serializable;

/**
 * This is a static class contains constants only
 * @author mathew
 */
public class Constants {

    /**
     * Source for generated ASCII: http://patorjk.com/software/taag/
     *
     * Ascii art for both the client and server start ups
     */
    public static String CLIENT_ASCII =
            " ██████╗██╗     ██╗███████╗███╗   ██╗████████╗\n" +
            "██╔════╝██║     ██║██╔════╝████╗  ██║╚══██╔══╝\n" +
            "██║     ██║     ██║█████╗  ██╔██╗ ██║   ██║   \n" +
            "██║     ██║     ██║██╔══╝  ██║╚██╗██║   ██║   \n" +
            "╚██████╗███████╗██║███████╗██║ ╚████║   ██║   \n";

    public static String SERVER_ASCII =
            "███████╗███████╗██████╗ ██╗   ██╗███████╗██████╗ \n" +
            "██╔════╝██╔════╝██╔══██╗██║   ██║██╔════╝██╔══██╗\n" +
            "███████╗█████╗  ██████╔╝██║   ██║█████╗  ██████╔╝\n" +
            "╚════██║██╔══╝  ██╔══██╗╚██╗ ██╔╝██╔══╝  ██╔══██╗\n" +
            "███████║███████╗██║  ██║ ╚████╔╝ ███████╗██║  ██║\n" +
            "╚══════╝╚══════╝╚═╝  ╚═╝  ╚═══╝  ╚══════╝╚═╝  ╚═╝\n";

    /**
     * This always remains constant, the default ID if the guest chat room on the server
     */
    public final static String DEFAULT_CHAT_ROOM_ID = "f09f22a3-de09-4830-ae58-b0dc91d41e67";

    /**
     * This is the default server address
     */
    private static String SERVER_ADDRESS = "localhost";

    /**
     * This is the default port
     */
    private static int PORT = 14005;

    /**
     * Default user ID, to be used when requesting new login from the server
     */
    private static String USER_ID = "GUEST";

    /**
     * Default display name for the user
     */
    private static String USER_NAME = null;

    /**
     * Whether or not the GUI should be ran, if null then user will be asked on startup
     */
    private static Boolean RUN_GUI = null;


    /**
     * An enum denoting the different types of nodes that can interact via the network protocol
     */
    public enum NodeType {
        ChatServer, ChatClient
    }


    /**
     * This enum denotes the type of packet being sent, whether it is a request or a response
     */
    public enum Header implements Serializable{
        UPDATE, UPDATE_USER, LOGIN_USER, SEND_MESSAGE, UPDATE_DB, NOT_AUTHORISED, SUCCESS, INVALID_HEADER, UNEXPECTED_PACKET, FAIL
    }


    /**
     * This method parses the commandline parameters and updates the constants accordingly
     *
     * @param args the commandline parameters
     * @param type the type of node - client or server?
     */
    public static void updateConstants(String[] args, NodeType type) {

        ParameterParser parser = new ParameterParser();

        switch (type) {
            //Updates the server parameters
            case ChatServer:
                //Server port
                parser.add(new Parameter("csp", false));
                Integer cspArg = null;

                if (parser.parseParameters(args) && (cspArg = parser.getID("csp").parseInteger()) != null) {
                    PORT = cspArg;
                }

                break;

            //Updates the client parameters
            case ChatClient:
                //Client port
                parser.add(new Parameter("ccp", false))
                        //Client address
                        .add(new Parameter("cca", false))
                        //User to login as
                        .add(new Parameter("user", false))
                        //Whether on not the GUI should be run
                        .add(new Parameter("GUI", false))
                        .add(new Parameter("name", false));
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
                if (sucessfulParse && (ccaArg = parser.getID("name").getValue()) != null) {
                    USER_NAME= ccaArg;
                }
                if (sucessfulParse && (ccaArg = parser.getID("GUI").getValue()) != null) {
                    try{
                        RUN_GUI = Boolean.parseBoolean(ccaArg);
                    }
                    catch(Exception e){}
                }
                break;
        }
    }

    /**
     * Get server address string.
     *
     * @return the string
     */
    public static String getServerAddress(){
        return SERVER_ADDRESS;
    }

    /**
     * Gets the port number
     *
     * @return the int
     */
    public static int getPort(){
        return PORT;
    }

    /**
     * Gets user id.
     *
     * @return the user id
     */
    public static String getUserId() {return USER_ID;}

    /**
     * Sets user id.
     *
     * @param userId the user id
     */
    public static void setUserId(String userId) {
        USER_ID = userId;
    }

    /**
     * Gets run gui.
     *
     * @return the run gui
     */
    public static Boolean getRunGui() {
        return RUN_GUI;
    }

    /**
     * Gets users display name
     *
     * @return the user name
     */
    public static String getUserName() {
        return USER_NAME;
    }
}
