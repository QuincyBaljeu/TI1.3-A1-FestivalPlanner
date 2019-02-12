package GUI.ManageViewer.EditMenu;

import javafx.scene.Scene;
import javafx.scene.layout.GridPane;

public abstract class EditMenu<T> implements EditMenuInterface {

    private Scene scene;
    private GridPane gridPane;
    private T object;

    public EditMenu(T object) {
        this.object = object;
        this.scene = new Scene(this.gridPane = new GridPane());
        initScene();
    }

    private void initScene() {

    }

}
