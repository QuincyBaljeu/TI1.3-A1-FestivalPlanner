package GUI.ManageViewer;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.util.List;

public abstract class ManageView<T> implements ManageViewInterface{

    private List<T> manageAbeles;
    private GridPane gridPane;
    private Scene scene;

    public ManageView(List<T> manageAbeles) {
        this.manageAbeles = manageAbeles;
        this.scene = new Scene(this.gridPane = new GridPane());
        initScene();
        loadObjects();
    }

    private void initScene() {
        List<String> varTypes = getVariableTypes();
        for (int i = 0; i < varTypes.size(); i++) {
            gridPane.addColumn(i, new Label(varTypes.get(i)));
        }
    }

    private void loadObjects() {
        List<String> variables = getVariables();
        for (int i = 0; i < variables.size(); i++) {
            String[] variable = variables.get(i).split("#");
            gridPane.addRow(i+1, new Label(variable[0]));
            for (int j = 1; j < variable.length; j++){
               gridPane.add(new Label(variable[j]), j, i+1);
            }
        }
    }


    public List<T> getManageAbeles() {
        return manageAbeles;
    }

    public void setManageAbeles(List<T> manageAbeles) {
        this.manageAbeles = manageAbeles;
    }

    public Scene getScene() {
        return scene;
    }
}
