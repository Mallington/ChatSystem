package messaging.system;

import javafx.collections.ObservableList;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

import java.io.IOException;
import java.util.List;

/**
 * This class is a custom ListCell controller which creates a neat interface for customising
 * the visual elements of the list cell, allowing for custom graphics to be added to the cells
 * by overriding the core methods of the ListCell class in the JavaFX API.
 * This means components such as peopleOnline boxes can be displayed in the list cell.
 *
 * @param <Component> component must be a Resource type and is the component to be added
 */
class CustomListCellController <Component extends Resource>extends ListCell<Component> {

    /**
     * The list view to be controlled
     */
    private ListView<Component> listView;

    /**
     * Instance of this class that utilises the custom updateItem methods
     */
    private CustomListCellController<Component> inst;

    /**
     * This is the only entry point for this class
     * @param listView
     */
    public CustomListCellController(ListView<Component> listView) {
        this.listView = listView;
        init();
    }

    /**
     * Private instantiation for class use only
     */
    private CustomListCellController(){}

    /**
     * This creates a new version of this class and adds it as the list views custom cell factory
     */
    private void init(){
        listView.setCellFactory(param -> {
            return (inst = new CustomListCellController<Component>());
        });
    }

    /**
     * This adds a new component to the listview
     * @param comp the component to add to the list view
     */
    public void add(Component comp){
        ObservableList<Component> list = listView.getItems();
        list.add(comp);
        listView.setItems(list);
        listView.refresh();
    }

    /**
     * This is an overridden method which is only run by the javafx super class
     * This particular implementation overrides the graphics of the specific cell
     * and sets it to the graphics of the custom node
     * @param component to be written to the cell
     * @param empty whether or not this component is empty
     */

    @Override
    public void updateItem(Component component, boolean empty) {
        super.updateItem(component, empty);
        try {
            if (empty) {
                setText(null);
                setGraphic(null);
            } else {
                setGraphic(component.getNode());
                component.setRoot(this);
            } } catch (IOException e) {
            System.out.println("Failed to load peopleOnline node");
        }

    }
}