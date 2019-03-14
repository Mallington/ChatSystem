package messaging.system;

/**
 * Console implementation for the server
 */
public abstract class ServerConsole extends ConsoleUtils implements ServerUserInterface{
    private int lastServerPopulation =-1;
    /**
     * Displays port that is currenly being listened on
     * @param port
     */
    @Override
    public void displayPort(int port) {

        this.printConsole("Listening on port: "+port+".");
    }

    @Override
    public void displaysUserPopulation(int population){
        if(lastServerPopulation!=population){
            lastServerPopulation = population;
            printConsole("Users Online:\n"+population);
        }
    }
}
