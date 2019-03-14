package messaging.system;

import javafx.stage.Stage;

/**
 * Purely a means to an end, a way of implementing any changes an external class wants to make to the stage
 */
public abstract class StageRunnable<Controller>{
    /**
     * Changes to make to the stage
     * @param stage stage to modify
     */
    abstract Resource<Controller> setupStage(Stage stage);
}
