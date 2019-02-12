package GUI.ManageViewer;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setAlignment(Pos.CENTER);
        initScene();
    }

    private void initScene() {
        List<String> varTypes = getVariableTypes();
        for (int i = 0; i < varTypes.size(); i++) {
            gridPane.addColumn(i, new Label(varTypes.get(i)));
        }
        loadObjects();
    }

    private void loadObjects() {
        List<String> variables = getVariables();
        for (int i = 0; i < variables.size(); i++) {
            String[] variable = variables.get(i).split("#");
            gridPane.addRow(i+1, new Label(variable[0]));
            for (int j = 1; j < variable.length; j++){
               gridPane.add(new Label(variable[j]), j, i+1);
            }
            gridPane.add(getDELButton(), variable.length, i+1);
            gridPane.add(getEDITButton(), variable.length+1, i+1);
        }
        gridPane.addRow(variables.size()+1, getADDButton());
        gridPane.add(getBackButton(), variables.get(0).split("#").length+1, variables.size()+1);

    }


    public List<T> getManageAbeles() {
        return manageAbeles;
    }

    private Button getDELButton() {
        Button deleteButton = new Button("DEL");
        deleteButton.setOnAction(e -> {

        });
        return deleteButton;
    }

    private Button getEDITButton() {
        Button EDitButton = new Button("EDIT");
        EDitButton.setOnAction(e -> {

        });
        return EDitButton;
    }

    private Button getADDButton() {
        Button ADDButton = new Button("ADD");
        ADDButton.setOnAction(e -> {

        });
        return ADDButton;
    }

    private Button getBackButton() {
        Button button = new Button("Back");
        button.setOnAction(e -> {
            Platform.exit();
        });
        return button;
    }

    public void setManageAbeles(List<T> manageAbeles) {
        this.manageAbeles = manageAbeles;
    }

    public Scene getScene() {
        return scene;
    }
}
