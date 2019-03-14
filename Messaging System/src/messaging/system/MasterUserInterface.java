package messaging.system;

public interface MasterUserInterface {
    /**
     * Prints a console message
     * @param consoleMessage Text of the message
     */
    public void printConsole(String consoleMessage);

    /**
     * Prints an error message
     * @param title of the error
     * @param body of the error
     */
    public void displayError(String title, String body);
}
