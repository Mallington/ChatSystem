package messaging.system;

import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ParameterWindowController implements Initializable {
    @FXML
    Text mainText;
    @FXML
    TableView<Parameter> parameterTable;

    private TableColumn<Parameter,String> parameterColumn;
    private TableColumn<Parameter, String> valueColumn;

    private boolean alreadySubmitted = false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupTable();
        addTableListeners();
    }

    private void setupTable(){
        parameterColumn = new TableColumn<Parameter,String>("Parameter");
        parameterColumn.setCellValueFactory(param ->{
            SimpleObjectProperty<String> property = new SimpleObjectProperty<String>();
            property.setValue(param.getValue().getID());
            return property;
        });
        parameterColumn.setPrefWidth(177);

        valueColumn = new TableColumn<Parameter,String>("Value");

        valueColumn.setCellValueFactory(param ->{
            SimpleObjectProperty<String> property = new SimpleObjectProperty<String>();
            property.setValue(param.getValue().getValue());
            return property;
        });
        valueColumn.setPrefWidth(177);
        valueColumn.setEditable(true);

        parameterTable.getColumns().addAll(parameterColumn, valueColumn);
        parameterTable.setEditable(true);
        parameterTable.getSelectionModel().cellSelectionEnabledProperty().set(true);
    }
    private void addTableListeners(){
        parameterTable.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
            if(parameterTable.getItems().size()>0) {
                if (event.getText().length() > 0) {
                    appendFocusedCell(event.getText());

                } else if (event.getCode().equals(KeyCode.BACK_SPACE)) {
                    backSpaceFocusedCell();
                } else if (event.getCode().equals(KeyCode.ENTER)) {

                    submit();

                }
            }
            }
        });


        valueColumn.setOnEditCommit(event -> {
            final String value = event.getNewValue() != null
                    ? event.getNewValue() : event.getOldValue();
            System.out.println("editing");

            ((Parameter) event.getTableView().getItems()
                    .get(event.getTablePosition().getRow())).setValue(value);
            parameterTable.refresh();
        });
        valueColumn.setEditable(true);
    }

    public List<Parameter> getParameters(){
        List<Parameter> parameters = new ArrayList<>();
        for(Parameter p :parameterTable.getItems()) {
            parameters.add(p);
        }

        return parameters;
    }

    public void submit() {
        if(!alreadySubmitted){
            alreadySubmitted = true;
        }
    }
    public void closeApplication(){
        Platform.runLater(()->mainText.getScene().getWindow().hide());
    }

    public synchronized boolean isAlreadySubmitted() {
        return alreadySubmitted;
    }

    public void addParameter(Parameter p){

        Platform.runLater(()-> {
            parameterTable.getItems().add(p);
            parameterTable.refresh();
        });
    }

    @SuppressWarnings("unchecked")

    private void appendFocusedCell(String append){
        final TablePosition<Parameter, ?> focusedCell = parameterTable
                .focusModelProperty().get().focusedCellProperty().get();

        Parameter p = parameterTable.getItems().get(focusedCell.getRow());
        if(p.getValue() ==null) p.setValue("");
        p.setValue(p.getValue()+append);
        parameterTable.refresh();
    }

    @SuppressWarnings("unchecked")
    private void backSpaceFocusedCell(){
        final TablePosition<Parameter, ?> focusedCell = parameterTable
                .focusModelProperty().get().focusedCellProperty().get();

        Parameter p = parameterTable.getItems().get(focusedCell.getRow());

        if(p.getValue() != null &&p.getValue().length()>0){
            p.setValue(p.getValue().substring(0,p.getValue().length()-1));
        }
        parameterTable.refresh();
    }

}