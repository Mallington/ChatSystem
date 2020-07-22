package messaging.system;

import javafx.stage.Stage;

import java.util.List;

/**
 * The purpose of this interface is to provide a graphical interface in which the user is able to
 * modify parameters before the main application is launched
 */
public class ParameterWindow extends  StageRunnable<ParameterWindowController>{
    /**
     * Main text that appears inside the window
     */
    private String title =null;

    /**
     * Main commandline arguments when instantiating JavaFX instance
     */
    private String[] cmdArgs =null;

    /**
     * Where the controller for the ParameterWindow.fxml document is stored
     */
    private ParameterWindowController controller =null;


    /**
     * Instantiates a new Parameter window.
     *
     * @param title the main text for the window
     * @param args  the commandline arguments
     */
    public ParameterWindow(String title, String[] args) {
        this.title = title;
        this.cmdArgs = args;
    }

    /**
     * Loads the FXML, instantiates a new stage and shows the main window
     * @param args
     */
    private void showWindow(String args[]){
        StageLoader<ParameterWindowController> st = new StageLoader();
        controller = st.open(args, this);
    }

    /**
     * Launches a window listing all the parmaters to be modified, when the user hits submit,
     * the window closes and the parameters are returned
     *
     * @param defaultString default value
     * @param params Parameters
     * @return List of all the parameters and values
     */
    public List<Parameter> getUserParameters(String defaultString, String... params){
        showWindow(cmdArgs);

        controller.mainText.setText(title);

        if(controller !=null) {
            for (String p : params) {
                Parameter pm = new Parameter(p, false);
                pm.setValue(defaultString);
                controller.addParameter(pm);
            }


            while (!controller.isAlreadySubmitted()) {
                try {
                    Thread.sleep((int) (100 * Math.random()));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            List<Parameter> returnedParams = controller.getParameters();
            controller.closeApplication();
            return returnedParams;
        }
        else{
            return null;
        }
    }

    /**
     * Loads the FXML for the parameter window
     * @param stage stage to modify
     * @return Resource file containing controller and node
     */
    @Override
    Resource<ParameterWindowController> setupStage(Stage stage) {
        Resource<ParameterWindowController> r = new Resource<ParameterWindowController>("/ParameterWindow.fxml");
        stage.setMinWidth(358);
        stage.setMinHeight(258);
        stage.setTitle(title);
        return r;
    }


}
