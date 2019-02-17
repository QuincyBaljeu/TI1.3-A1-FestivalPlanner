package GUI;

import Data.*;

import GUI.ManageTables.ArtistManager;
import GUI.ManageTables.PerformanceManager;
import GUI.ManageTables.PodiumManager;
import javafx.application.Application;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableStringValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.List;

public class GUI extends Application{
    private AgendaModule agendaModule;
    private FestivalDay getFestivalDay(){
        return this.agendaModule.getFestivalDays().get(0);
    }

    public ObservableList<String> Times =
            FXCollections.observableArrayList(
                    "12:00","13:00","14:00","15:00","16:00","17:00","18:00","19:00","20:00","21:00","22:00", "23:00", "24:00"
            );

    public ObservableList<String> Mainstage =
            FXCollections.observableArrayList(
                    "","","Brennan Heart","","Hardwell","Hardwell","Hardwell","Hardwell","","Da Tweekaz","Da Tweekaz","",""
            );

    private void loadAgendaModule(Stage stage){
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("Festival utility files (*.fu)", "*.fu");
        fileChooser.getExtensionFilters().add(extensionFilter);
        File storFile = fileChooser.showOpenDialog(stage);
        if (storFile == null){
            this.agendaModule = TestingDataLib.getDummyAgendaModule(fileChooser.showSaveDialog(stage).getPath());
        }
        else {
            try {
                this.agendaModule = (AgendaModule)AgendaModule.loadFromFile(storFile.getPath());
            }
            catch (Exception x){
                x.printStackTrace();
            }
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        // Load data
        this.loadAgendaModule(stage);

        //Panes
        BorderPane menuBorderPane = new BorderPane();
        BorderPane editBorderPane = new BorderPane();
        BorderPane viewBorderPane = new BorderPane();
        TabPane tabPane = new TabPane();

        //Menu tabs
        Tab Simulation = new Tab("Simulation");
        Tab View = new Tab("View mode");
        Tab Edit = new Tab("Edit mode");

        tabPane.getTabs().addAll(View,Simulation,Edit);
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        // Use the menu as a set of buttons
        Menu stagesMenu = new Menu("Manage Stages");
        Menu performanceMenu = new Menu("Manage Performances");
        Menu artistsMenu = new Menu("Manage Artists");

        MenuItem managePodium = new MenuItem("Manage Podiums");
        managePodium.setOnAction((e)->{
            new PodiumManager(this.getFestivalDay());
        });
        stagesMenu.getItems().add(managePodium);

        MenuItem managePerformance = new MenuItem("Manage Performances");
        managePerformance.setOnAction((e)->{
            new PerformanceManager(this.getFestivalDay());
        });
        performanceMenu.getItems().add(managePerformance);

        MenuItem manageArtist = new MenuItem("Manage Artists");
        manageArtist.setOnAction((e)->{
            new ArtistManager(this.getFestivalDay());
        });
        artistsMenu.getItems().add(manageArtist);

        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(stagesMenu,performanceMenu,artistsMenu);

        //Implements array to tableview
        TableView<Performance> edittable = new TableView<>();
        TableView<Performance> viewtable = new TableView<>();
        // Block the user from editing
        edittable.setEditable(false);

        // Add a column for every hour
        this.addTableColumns(viewtable);
        this.addTableColumns(edittable);

        //Adds table to borderpane
        editBorderPane.setCenter(edittable);
        editBorderPane.setTop(menuBar);
        viewBorderPane.setTop(viewtable);
        //Adds borderpane to tabpane
        Edit.setContent(editBorderPane);
        View.setContent(viewtable);
        //Adds tabpane to final borderpane
        menuBorderPane.setCenter(tabPane);

        stage.setTitle("Festival Planner");
        stage.setWidth(500);
        stage.setHeight(500);
        Scene scene = new Scene(menuBorderPane);
        stage.setScene(scene);
        stage.show();
    }

    private void addTableColumns(TableView table){
        for (int i = 12; i <= 24; i++){
            TableColumn<Performance, String> column = new TableColumn<Performance, String>( i + ":00");
            // prevent the column from beeing resized by it's content
            column.setMaxWidth(100);
            column.setMinWidth(100);

            table.getColumns().add(
                column
            );

            column.setCellValueFactory(
                (cell) -> new AgendaViewerItem(cell.getValue(), this.getFestivalDay()).getTableDisplay(
                    cell.getTableColumn().getText()
                )
            );
        }

        table.setItems(
            FXCollections.observableArrayList(
                this.getFestivalDay().getPerformances()
            )
        );
    }
}
