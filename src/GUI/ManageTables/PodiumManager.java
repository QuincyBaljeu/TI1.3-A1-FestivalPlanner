package GUI.ManageTables;

import Data.Artist;
import Data.FestivalDay;
import Data.Podium;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;

public class PodiumManager {

    private Stage stage;
    private Scene scene;
    private BorderPane borderPane;
    private FestivalDay festivalDay;
    private TableView tableView = new TableView<Podium>();

    public PodiumManager(FestivalDay festivalDay) {
        this.festivalDay = festivalDay;
        this.scene = new Scene(this.borderPane = new BorderPane());
        this.stage = new Stage();
        HBox top = new HBox();
        Label title = new Label("Podium List");
        top.getChildren().add(title);
        top.setAlignment(Pos.CENTER);
        title.setFont(new Font("Arial", 40));
        this.borderPane.setTop(top);
        this.borderPane.setCenter(this.tableView);
        InitializeScene();
    }

    private void InitializeScene() {
        tableView.setEditable(true);
        TableColumn<Podium, String> name = new TableColumn("name");
        //TableColumn<Podium, Boolean> performance = new TableColumn("performances");


        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        name.setCellFactory(TextFieldTableCell.forTableColumn());
        name.setMinWidth(100);

        name.setOnEditCommit((TableColumn.CellEditEvent<Podium, String> event) -> {
            TablePosition<Podium, String> pos = event.getTablePosition();
            String newName = event.getNewValue();

            int row = pos.getRow();
            Podium podium = event.getTableView().getItems().get(row);

            podium.setName(newName);

            try {
                this.festivalDay.getAgendaModule().save();
            } catch (Exception x){
                x.printStackTrace();
            }
        });




        this.tableView.getColumns().addAll(name);

        this.tableView.setItems(FXCollections.observableList(this.festivalDay.getPodia()));

        this.stage.setResizable(true);
        this.stage.setTitle("Podium List");
        this.stage.setScene(this.scene);
        this.stage.show();
    }
}
