package messaging.system;

/**
 * Console implementation for the server
 */
public abstract class ServerConsole extends ConsoleUtils implements ServerUserInterface{

    /**
     * Displays port that is currenly being listened on
     * @param port
     */
    @Override
    public void displayPort(int port) {
        this.printConsole("Listening on port: "+port+".");
    }
}
