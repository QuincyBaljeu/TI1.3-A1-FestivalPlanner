package GUI.ManageViewer;

import Data.Artist;
import Data.Performance;
import Data.Podium;
import GUI.ManageViewer.EditMenu.ArtistEditMenu;
import GUI.ManageViewer.EditMenu.PerformanceEditMenu;
import GUI.ManageViewer.EditMenu.StageEditMenue;
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
            gridPane.add(getDELButton(this.manageAbeles.get(i)), variable.length, i+1);
            gridPane.add(getEDITButton(this.manageAbeles.get(i)), variable.length+1, i+1);
        }
        gridPane.addRow(variables.size()+1, getADDButton(this.manageAbeles.get(0)));
        gridPane.add(getBackButton(), variables.get(0).split("#").length+1, variables.size()+1);

    }


    public List<T> getManageAbeles() {
        return manageAbeles;
    }

    private Button getDELButton(T object) {
        Button deleteButton = new Button("DEL");
        deleteButton.setOnAction(e -> {

        });
        return deleteButton;
    }

    private Button getEDITButton(T object) {
        Button EDitButton = new Button("EDIT");
        EDitButton.setOnAction(e -> {
            javafx.stage.Stage stage = new javafx.stage.Stage();
            if (object.getClass().equals(Artist.class)) {
                stage.setScene(new ArtistEditMenu((Artist) object).getScene());
            } else if (object.getClass().equals(Performance.class)) {
                stage.setScene(new PerformanceEditMenu((Performance) object).getScene());
            } else if (object.getClass().equals(Podium.class)) {
                stage.setScene(new StageEditMenue((Podium) object).getScene());
            } else {
                System.out.println("### ERROR ###" +
                        "Unknown class given");
            }
            stage.show();
        });
        return EDitButton;
    }

    private Button getADDButton(T object) {
        Button ADDButton = new Button("ADD");
        ADDButton.setOnAction(e -> {
            javafx.stage.Stage stage = new javafx.stage.Stage();
            if (object.getClass().equals(Artist.class)) {
                stage.setScene(new ArtistEditMenu().getScene());
            } else if (object.getClass().equals(Performance.class)) {
                stage.setScene(new PerformanceEditMenu().getScene());
            } else if (object.getClass().equals(Podium.class)) {
                stage.setScene(new StageEditMenue().getScene());
            } else {
                System.out.println("### ERROR ###" +
                        "Unknown class given");
            }
            stage.show();
        });
        return ADDButton;
    }

    private Button getBackButton() {
        Button button = new Button("Back");
        button.setOnAction(e -> {

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
