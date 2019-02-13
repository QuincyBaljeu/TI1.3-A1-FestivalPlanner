package GUI.ManageTables;

import Data.Artist;
import Data.FestivalDay;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class ArtistManager {

    private Stage stage;
    private Scene scene;
    private BorderPane borderPane;
    private FestivalDay festivalDay;
    private TableView tableView = new TableView();

    public ArtistManager(FestivalDay festivalDay) {
        this.festivalDay = festivalDay;
        this.scene = new Scene(this.borderPane = new BorderPane());
        this.stage = new Stage();
        final Label top = new Label("Artist List");
        top.setFont(new Font("Arial", 20));
        this.borderPane.setTop(top);
        this.borderPane.setCenter(this.tableView);
        InitializeScene();
    }

    private void InitializeScene() {
        tableView.setEditable(true);
        TableColumn name = new TableColumn("Name");
        TableColumn genre = new TableColumn("Genre");
        TableColumn artistType = new TableColumn("artistType");
        TableColumn filePathProfilePic = new TableColumn("ProfilePicture");
        TableColumn countryOfOrigin = new TableColumn("Country");
        this.tableView.getColumns().addAll(name, genre, artistType, filePathProfilePic, countryOfOrigin);

        this.stage.setWidth(300);
        this.stage.setHeight(500);
        this.stage.setTitle("Table View Sample");
        this.stage.setScene(this.scene);
        this.stage.show();
    }


}
