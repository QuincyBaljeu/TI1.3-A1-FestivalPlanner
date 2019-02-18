package GUI.ManageTables;

import Data.*;
import GUI.GUI;
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
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import sun.misc.Perf;
import sun.security.util.PermissionFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * The graphical user interface to manage performance instances in a FestivalDay Object
 */
public class PerformanceManager extends DataManager {

    private Stage stage;
    private Scene scene;
    private BorderPane borderPane;
    private TableView tableView = new TableView<Podium>();

    private ObservableList<LocalTime> Times;
    private ObservableList<Integer> popularity;

    public PerformanceManager(FestivalDay festivalDay, GUI parent) {
        super(festivalDay, parent);
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
                LocalTime.of(12, 0), LocalTime.of(13, 0), LocalTime.of(14, 0), LocalTime.of(15, 0),
                LocalTime.of(16, 0), LocalTime.of(17, 0), LocalTime.of(18, 0),
                LocalTime.of(19, 0), LocalTime.of(20, 0), LocalTime.of(21, 0),
                LocalTime.of(22, 0), LocalTime.of(23, 0), LocalTime.of(0, 0)
        );
        popularity = FXCollections.observableArrayList(
                0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10
        );

        InitializeScene();
    }

    @Override
    public FestivalDay getFestivalDay(){
        return super.getFestivalDay();
    }

    @Override
    public GUI getParent(){
        return super.getParent();
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

            super.processChanges();
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

			super.processChanges();
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

			super.processChanges();
        });
        //end Popularity
        //start Podium
        podium.setCellValueFactory(param -> {
            Performance performance = param.getValue();

            String newPodium = performance.getPodium().getName();
            return new SimpleObjectProperty<String>(newPodium);
        });
        ArrayList<String> podiumNameList = new ArrayList<>();
        for (Podium podium1 : this.getFestivalDay().getPodia()) {
            podiumNameList.add(podium1.getName());
        }
        podium.setCellFactory(ComboBoxTableCell.forTableColumn(FXCollections.observableArrayList(podiumNameList)));

        podium.setOnEditCommit((TableColumn.CellEditEvent<Performance, String> event) -> {
            TablePosition<Performance, String> pos = event.getTablePosition();

            String podium1 = event.getNewValue();

            int row = pos.getRow();
            Performance performance = event.getTableView().getItems().get(row);

            performance.setPodium(this.getFestivalDay().getPodiumViaName(podium1));

			super.processChanges();
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
                                        getFestivalDay().removePerformance(performance);
                                        tableView.setItems(FXCollections.observableList(getFestivalDay().getPerformances()));

										processChanges();
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
                                        new ArtistList(performance, getFestivalDay());
                                        System.out.println("Saved List: " + performance.getArtists().toString());
                                        tableView.setItems(FXCollections.observableList(getFestivalDay().getPerformances()));

										processChanges();
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

        // begin adding bar
        HBox bot = new HBox();

        ComboBox<LocalTime> startTimeField = new ComboBox<>();
        startTimeField.setItems(Times);
        Label startTimeLabel = new Label("Start Time");
        VBox startTimeSet = new VBox(startTimeLabel, startTimeField);

        ComboBox<LocalTime> endTimeField = new ComboBox<>();
        endTimeField.setItems(Times);
        Label endTimeLabel = new Label("End Time");
        VBox endTimeSet = new VBox(endTimeLabel, endTimeField);

        ComboBox<Integer> popularityField = new ComboBox<>();
        popularityField.setItems(popularity);
        Label popularityLabel = new Label("Popularity");
        VBox popularitySet = new VBox(popularityLabel, popularityField);

        ComboBox<String> podiumField = new ComboBox<>();
        List<String> podiaNames = new ArrayList<>();
        for (Podium podium1 : this.getFestivalDay().getPodia()) {
            podiaNames.add(podium1.getName());
        }
        podiumField.setItems(FXCollections.observableArrayList(podiaNames));
        Label podiumLabel = new Label("Podium");
        VBox podiumSet = new VBox(podiumLabel, podiumField);

        Button add = new Button("Add");
        Label addLabel = new Label("Add Performance");
        VBox addSet = new VBox(addLabel, add);

        ComboBox<String> artistField = new ComboBox<>();
        List<String> artistNames = new ArrayList<>();
        for (Artist artist1 : this.getFestivalDay().getArtists()) {
            artistNames.add(artist1.getName());
        }
        artistField.setItems(FXCollections.observableList(artistNames));
        Label artistLabel = new Label("First Artist");
        VBox artistSet = new VBox(artistLabel, artistField);

        add.setOnAction(e -> {

            Performance newPerformance = new Performance(startTimeField.getSelectionModel().getSelectedItem(),
                    endTimeField.getSelectionModel().getSelectedItem(), popularityField.getSelectionModel().getSelectedIndex(),
                    this.getFestivalDay(), this.getFestivalDay().getPodiumViaName(podiumField.getSelectionModel().getSelectedItem()),
                    this.getFestivalDay().getArtistViaName(artistField.getSelectionModel().getSelectedItem()));

            if (checkAvailability(newPerformance) && chechArtistAvailible(newPerformance)) {
                System.out.println("Added the thing");
                this.getFestivalDay().addPerformance(newPerformance);
            } else {
                System.out.println("ur mum a gay");
            }

            this.tableView.setItems(FXCollections.observableList(this.getFestivalDay().getPerformances()));

			super.processChanges();
        });

        bot.setSpacing(10);
        bot.setAlignment(Pos.CENTER);
        bot.getChildren().addAll(startTimeSet, endTimeSet, popularitySet, podiumSet, artistSet, addSet);


        this.tableView.getColumns().addAll(beginTime, endTime, populatiry, podium, artist, delete);

        this.tableView.setItems(FXCollections.observableList(this.getFestivalDay().getPerformances()));

        this.borderPane.setBottom(bot);

        this.stage.setResizable(true);
        this.stage.setTitle("Performance List");
        this.stage.setScene(this.scene);
        this.stage.show();
    }

    private boolean checkAvailability(Performance performance){
        for (Performance performanceOld : super.getFestivalDay().getPerformances()) {
            if (performanceOld.getPodium().equals(performance.getPodium())) {
                if (performanceOld.getStartTime() == performance.getStartTime()
                        || (performance.getStartTime().isBefore(performanceOld.getEndTime()) && performance.getStartTime().isAfter(performanceOld.getStartTime()))
                        || (performance.getEndTime().isBefore(performanceOld.getEndTime()) && performance.getEndTime().isAfter(performanceOld.getStartTime()))) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean chechArtistAvailible(Performance performance) {
        for (Performance performanceOld : super.getFestivalDay().getPerformances()) {
            if (!performance.getPodium().equals(performanceOld.getPodium())) {
                for (int i = 0; i < performanceOld.getArtists().size() ; i++) {
                    if (performance.getArtists().contains(performanceOld.getArtists().get(i))){
                        if (performanceOld.getStartTime() == performance.getStartTime()
                                || (performance.getStartTime().isBefore(performanceOld.getEndTime()) && performance.getStartTime().isAfter(performanceOld.getStartTime()))
                                || (performance.getEndTime().isBefore(performanceOld.getEndTime()) && performance.getEndTime().isAfter(performanceOld.getStartTime()))) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }
}
