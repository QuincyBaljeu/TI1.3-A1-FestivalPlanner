package GUI.ManageTables;

import Data.Artist;
import Data.FestivalDay;
import Data.Performance;
import Data.Podium;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.util.ArrayList;
import java.util.List;

public class ArtistList {

    private TableView tableView = new TableView();
    private FestivalDay festivalDay;
    private Performance performance;
    private Scene scene;
    private Stage stage;
    private BorderPane borderPane;
    private List<Artist> selectedArtists = new ArrayList<>();


    public ArtistList(Performance performance, FestivalDay festivalDay) {
        this.performance = performance;
        this.festivalDay = festivalDay;
        this.scene = new Scene(this.borderPane = new BorderPane());
        this.stage = new Stage();
        HBox top = new HBox();
        Label title = new Label("Selected artist List");
        top.getChildren().add(title);
        top.setAlignment(Pos.CENTER);
        title.setFont(new Font("Arial", 40));
        this.borderPane.setTop(top);
        this.borderPane.setCenter(this.tableView);
        InitializeScene();
    }

    private void InitializeScene() {
        TableColumn<Artist, String> name = new TableColumn<>("Name Artist");
        TableColumn<Artist, Boolean> selected = new TableColumn<>("Select");

        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        name.setCellFactory(TextFieldTableCell.forTableColumn());
        this.selectedArtists = performance.getArtists();
        selected.setCellValueFactory(param -> new SimpleBooleanProperty());

        Callback<TableColumn<Artist, Boolean>, TableCell<Artist, Boolean>> cellFactory
                =
                new Callback<TableColumn<Artist, Boolean>, TableCell<Artist, Boolean>>() {
                    @Override
                    public TableCell call(final TableColumn<Artist, Boolean> param) {
                        final TableCell<Artist, Boolean> cell = new TableCell<Artist, Boolean>() {

                            final CheckBox select = new CheckBox();

                            @Override
                            public void updateItem(Boolean item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                    setText(null);
                                } else {
                                    if (selectedArtists.contains(getTableView().getItems().get(getIndex()))) {
                                        if (!select.isSelected()) {
                                            select.fire();
                                        }
                                    }
                                    select.setOnAction(e -> {
                                        Artist artist = getTableView().getItems().get(getIndex());
                                        if (select.isSelected()) {
                                            if (!selectedArtists.contains(artist)) {
                                                selectedArtists.add(artist);
                                            }
                                        } else {
                                            selectedArtists.remove(artist);
                                        }
                                    });
                                    setGraphic(select);
                                    setText(null);
                                }
                            }
                        };
                        return cell;
                    }
                };

        selected.setCellFactory(cellFactory);

        Button save = new Button("Save Selected Artists");
        save.setOnAction(e -> {
            this.performance.setArtists(this.selectedArtists);
            stage.close();
        });

        HBox bot = new HBox(save);
        bot.setAlignment(Pos.CENTER);
        this.borderPane.setBottom(bot);

        this.tableView.setItems(FXCollections.observableList(this.festivalDay.getArtists()));
        this.tableView.getColumns().addAll(name, selected);
        this.stage.setScene(this.scene);
        stage.setAlwaysOnTop(true);
        stage.showAndWait();

    }
}
