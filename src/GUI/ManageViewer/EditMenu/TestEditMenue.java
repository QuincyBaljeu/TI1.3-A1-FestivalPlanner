package GUI.ManageViewer.EditMenu;

import javafx.application.Application;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class TestEditMenue extends Application
{

    @Override
    public void start(Stage primaryStage) throws Exception {

        EditMenu editMenu = new ArtistEditMenu();



        primaryStage.setScene(editMenu.getScene());
        primaryStage.show();
    }
}
