package messaging.system;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.stage.Popup;

/**
 * This class provides a way of loading a FXML page and getting its attached
 * controller so that the instigating class has a way of interacting with the
 * GUI it has just loaded
 *
 * Note: This class is taken from a previous project of mine
 * @author mathew allington
 */
public class Resource<ControllerType> {

    private FXMLLoader LOADER;
    private String RESOURCE;

    /**
     * Upon initialisation it stores the resource and creates a new FXML loader
     *
     * @param res FXML page to be loaded
     */
    public Resource(String res) {

        System.out.println("Path to resource " + getClass().getResource(res).getPath());
        LOADER = new FXMLLoader();
        // LOADER.setRoot(null);
        // LOADER.setLocation(getClass().getResource(res));
        RESOURCE = res;
    }

    /**
     * Loads the resource file an outputs as a node to be loaded into a Stage/
     * Window
     *
     * @return Node to be loaded
     * @throws IOException
     */
    public Parent getNode() throws IOException {
        return LOADER.load(getClass().getResource(RESOURCE).openStream());
    }

    /**
     * Returns the controller instance attached to the loaded node
     *
     * @return Controller instance
     * @throws IOException
     */
    public ControllerType loadController() throws IOException {

        ControllerType controller = LOADER.getController();

        return controller;
    }

}