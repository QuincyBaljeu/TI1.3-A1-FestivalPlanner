package GUI.ManageViewer;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.util.List;

public abstract class ManageView<T> {

    private List<T> manageAbeles;
    private VBox vBox;
    private Scene scene;

    public ManageView(List<T> manageAbeles) {
        this.manageAbeles = manageAbeles;
        this.scene = new Scene(this.vBox = new VBox(new Label("Name")));
        initScene();
    }

    private void initScene() {
        System.out.println("###ERROR###" +
                "The initScene hasn't been properly implemented");
    }


    public List<T> getManageAbeles() {
        return manageAbeles;
    }

    public void setManageAbeles(List<T> manageAbeles) {
        this.manageAbeles = manageAbeles;
    }
}
