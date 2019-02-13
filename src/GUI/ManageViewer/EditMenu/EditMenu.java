package GUI.ManageViewer.EditMenu;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.io.ObjectInputStream;
import java.util.List;

public abstract class EditMenu<T> implements EditMenuInterface {

    private Scene scene;
    private GridPane gridPane;

    public EditMenu() {
        this.scene = new Scene(this.gridPane = new GridPane());
        this.gridPane.setVgap(10);
        this.gridPane.setHgap(10);

    }

    public void initScene() {
        List<String> varTypes = getVariableTypes();
        List<Node> inputTypes = getInputMethod();
        gridPane.addRow(0, new Label("Title Piece To be replaced" ));
        for (int i = 0; i < varTypes.size(); i++) {
            gridPane.addRow(i+1, new Label(varTypes.get(i)));
            gridPane.add(inputTypes.get(i), 1, i+1);
        }
        gridPane.add(getDoneButton(), 1, varTypes.size()+1);
    }

    public Scene getScene() {
        return scene;
    }

}
