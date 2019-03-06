package messaging.system;

import javafx.collections.ObservableList;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

import java.io.IOException;

class CustomListCellController <Component extends Resource>extends ListCell<Component> {

    private ListView<Component> listView;
    private CustomListCellController<Component> inst;

    public CustomListCellController(ListView<Component> listView) {
        this.listView = listView;
        inst = this;
        init();
    }
    private CustomListCellController(){}

    private void init(){
        listView.setCellFactory(param -> {
            return (inst = new CustomListCellController<Component>());
        });
    }

    public void add(Component comp){
        ObservableList<Component> list = listView.getItems();
        list.add(comp);
        listView.setItems(list);
        listView.refresh();
    }

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
            System.out.println("Failed to load message node");
        }

    }
}