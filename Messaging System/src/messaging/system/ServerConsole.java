package messaging.system;

public class ServerConsole extends ConsoleUtils implements ServerUserInterface{

    @Override
    public void displayPort(int port) {
        this.printConsole("Listening on port: "+port+".");
    }
}
