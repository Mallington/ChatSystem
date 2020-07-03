package messaging.system;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        ParameterParser parser = new ParameterParser()
                .add(new Parameter("nodetype", true));
        if(parser.parseParameters(args)){
            switch (parser.getID("nodetype").getValue()){
                case "SERVER":
                    ChatServer.main(args);
                    break;
                case "CLIENT":
                    ChatClient.main(args);
                    break;
                default:
                    System.out.println("Unrecognised node type: "+parser.getID("nodetype").getValue());
                    break;
            }
        }
        else{
            System.out.println("Please specify the parameter '-nodetype' to either SERVER or CLIENT.");
        }
    }
}
