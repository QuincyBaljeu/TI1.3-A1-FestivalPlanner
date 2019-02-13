package GUI.ManageViewer.EditMenu;

import Data.Artist;
import Data.Genre;
import javafx.application.Application;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


public class TestEditMenue extends Application
{

    @Override
    public void start(Stage primaryStage) throws Exception {

        EditMenu editMenu = new ArtistEditMenu(new Artist("Lucas", Genre.COUNTRY, "Singer", "Cool", "Netherlands"));

        primaryStage.setScene(editMenu.getScene());
        primaryStage.show();
    }
}
