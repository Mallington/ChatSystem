package messaging.system;

import javafx.stage.Stage;

import java.util.List;

public class ParameterWindow extends  StageRunnable<ParameterWindowController>{
    private String title =null;
    private String[] cmdArgs =null;

    private ParameterWindowController controller =null;

    public ParameterWindow(String title, String[] args) {
        this.title = title;
        this.cmdArgs = args;
    }
    private void showWindow(String args[]){
        StageLoader<ParameterWindowController> st = new StageLoader();
        controller = st.open(args, this);
    }

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

    @Override
    Resource<ParameterWindowController> setupStage(Stage stage) {
        Resource<ParameterWindowController> r = new Resource<ParameterWindowController>("ParameterWindow.fxml");
        stage.setMinWidth(358);
        stage.setMinHeight(258);
        stage.setTitle(title);
        return r;
    }


}
