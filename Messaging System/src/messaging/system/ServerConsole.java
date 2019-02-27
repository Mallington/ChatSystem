package messaging.system;

public abstract class ServerConsole extends ConsoleUtils implements ServerUserInterface{

    @Override
    public void displayPort(int port) {
        this.printConsole("Listening on port: "+port+".");
    }
}
