package GUI.ManageTables;

import Data.*;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

public class PerformanceManager {

    private Stage stage;
    private Scene scene;
    private BorderPane borderPane;
    private FestivalDay festivalDay;
    private TableView tableView = new TableView<Podium>();

    private ObservableList<LocalTime> Times;
    private ObservableList<Integer> popularity;

    public PerformanceManager(FestivalDay festivalDay) {

        this.festivalDay = festivalDay;
        this.scene = new Scene(this.borderPane = new BorderPane());
        this.stage = new Stage();
        HBox top = new HBox();
        Label title = new Label("Performance List");
        top.getChildren().add(title);
        top.setAlignment(Pos.CENTER);
        title.setFont(new Font("Arial", 40));
        this.borderPane.setTop(top);
        this.borderPane.setCenter(this.tableView);
        Times = FXCollections.observableArrayList(
                LocalTime.of(12, 0), LocalTime.of(13,0), LocalTime.of(14,0), LocalTime.of(15,0),
                LocalTime.of(16,0), LocalTime.of(17,0), LocalTime.of(18,0),
                LocalTime.of(19,0), LocalTime.of(20,0), LocalTime.of(21,0),
                LocalTime.of(22,0), LocalTime.of(23,0), LocalTime.of(0, 0)
        );
        popularity = FXCollections.observableArrayList(
                0,1,2,3,4,5,6,7,8,9,10
        );

        InitializeScene();
    }

    private void InitializeScene() {
        tableView.setEditable(true);

        TableColumn<Performance, LocalTime> beginTime = new TableColumn("beginTime");
        TableColumn<Performance, LocalTime> endTime = new TableColumn("endTime");
        TableColumn<Performance, Integer> populatiry = new TableColumn("popularity");
        TableColumn<Performance, String> podium = new TableColumn("podium");
        TableColumn<Performance, String> artist = new TableColumn<>("artists");
        TableColumn<Performance, Boolean> delete = new TableColumn<>("Delete");


// Begin Time
        beginTime.setCellValueFactory(param -> {
            Performance performance = param.getValue();

            LocalTime newTime = performance.getStartTime();
            return new SimpleObjectProperty<>(newTime);
        });

        beginTime.setCellFactory(ComboBoxTableCell.forTableColumn(Times));

        beginTime.setOnEditCommit((TableColumn.CellEditEvent<Performance, LocalTime> event) -> {
            TablePosition<Performance, LocalTime> pos = event.getTablePosition();

            LocalTime newTime = event.getNewValue();

            int row = pos.getRow();
            Performance performance = event.getTableView().getItems().get(row);

            performance.setStartTime(newTime);

            try {
                this.festivalDay.getAgendaModule().save();
            } catch (Exception x){
                x.printStackTrace();
            }
        });

        endTime.setCellValueFactory(param -> {
            Performance performance = param.getValue();

            LocalTime newTime = performance.getEndTime();
            return new SimpleObjectProperty<>(newTime);
        });

        endTime.setCellFactory(ComboBoxTableCell.forTableColumn(Times));

        endTime.setOnEditCommit((TableColumn.CellEditEvent<Performance, LocalTime> event) -> {
            TablePosition<Performance, LocalTime> pos = event.getTablePosition();

            LocalTime endtime = event.getNewValue();

            int row = pos.getRow();
            Performance performance = event.getTableView().getItems().get(row);

            performance.setEndTime(endtime);

            try {
                this.festivalDay.getAgendaModule().save();
            } catch (Exception x){
                x.printStackTrace();
            }
        });

        // End Time
        //Popularity

        populatiry.setCellValueFactory(param -> {
            Performance performance = param.getValue();

            int newPopularity = performance.getPopularity();
            return new SimpleObjectProperty<>(newPopularity);
        });

        populatiry.setCellFactory(ComboBoxTableCell.forTableColumn(this.popularity));

        populatiry.setOnEditCommit((TableColumn.CellEditEvent<Performance, Integer> event) -> {
            TablePosition<Performance, Integer> pos = event.getTablePosition();

            int newPopularity = event.getNewValue();

            int row = pos.getRow();
            Performance performance = event.getTableView().getItems().get(row);

            performance.setPopularity(newPopularity);

            try {
                this.festivalDay.getAgendaModule().save();
            } catch (Exception x){
                x.printStackTrace();
            }
        });
        //end Popularity
        //start Podium
        podium.setCellValueFactory(param -> {
            Performance performance = param.getValue();

            String newPodium = performance.getPodium().getName();
            return new SimpleObjectProperty<String>(newPodium);
        });
        ArrayList<String> podiumNameList = new ArrayList<>();
        for (Podium podium1 : this.festivalDay.getPodia()) {
            podiumNameList.add(podium1.getName());
        }
        podium.setCellFactory(ComboBoxTableCell.forTableColumn(FXCollections.observableArrayList(podiumNameList)));

        podium.setOnEditCommit((TableColumn.CellEditEvent<Performance, String> event) -> {
            TablePosition<Performance, String> pos = event.getTablePosition();

            String podium1 = event.getNewValue();

            int row = pos.getRow();
            Performance performance = event.getTableView().getItems().get(row);

            performance.setPodium(this.festivalDay.getPodiumViaName(podium1));

            try {
                this.festivalDay.getAgendaModule().save();
            } catch (Exception x){
                x.printStackTrace();
            }
        });

        //end Podium

        // begin delete

        delete.setCellValueFactory(new PropertyValueFactory<>("DUMMY"));

        Callback<TableColumn<Performance, Boolean>, TableCell<Performance, Boolean>> cellFactorydel
                =
                new Callback<TableColumn<Performance, Boolean>, TableCell<Performance, Boolean>>() {
                    @Override
                    public TableCell call(final TableColumn<Performance, Boolean> param) {
                        final TableCell<Performance, Boolean> cell = new TableCell<Performance, Boolean>() {

                            final Button button = new Button("Del");

                            @Override
                            public void updateItem(Boolean item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                    setText(null);
                                } else {
                                    button.setOnAction(event -> {
                                        Performance performance = getTableView().getItems().get(getIndex());
                                        festivalDay.removePerformance(performance);
                                        tableView.setItems(FXCollections.observableList(festivalDay.getPerformances()));
                                        try {
                                            festivalDay.getAgendaModule().save();
                                        } catch (Exception x){
                                            x.printStackTrace();
                                        }
                                    });
                                    setGraphic(button);
                                    setText(null);
                                }
                            }
                        };
                        return cell;
                    }
                };

        delete.setCellFactory(cellFactorydel);
        //begin Artists
        artist.setCellValueFactory(new PropertyValueFactory<>("DUMMY"));

        Callback<TableColumn<Performance, String>, TableCell<Performance, String>> cellFactory
                =
                new Callback<TableColumn<Performance, String>, TableCell<Performance, String>>() {
                    @Override
                    public TableCell call(final TableColumn<Performance, String> param) {
                        final TableCell<Performance, String> cell = new TableCell<Performance, String>() {

                            final Button button = new Button("Select");

                            @Override
                            public void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                    setText(null);
                                } else {
                                    button.setOnAction(event -> {
                                        Performance performance = getTableView().getItems().get(getIndex());
                                        ArtistList artistList = new ArtistList(performance, festivalDay);



                                        System.out.println("Saved List: " + performance.getArtists().toString());
                                        tableView.setItems(FXCollections.observableList(festivalDay.getPerformances()));
                                        try {
                                            festivalDay.getAgendaModule().save();
                                        } catch (Exception x){
                                            x.printStackTrace();
                                        }
                                    });
                                    setGraphic(button);
                                    setText(null);
                                }
                            }
                        };
                        return cell;
                    }
                };

        artist.setCellFactory(cellFactory);
        //





        this.tableView.getColumns().addAll(beginTime, endTime, populatiry, podium, artist);

        this.tableView.setItems(FXCollections.observableList(this.festivalDay.getPerformances()));

        this.stage.setResizable(true);
        this.stage.setTitle("Performance List");
        this.stage.setScene(this.scene);
        this.stage.show();
    }
}
