package GUI;

import Data.*;

import GUI.ManageTables.ArtistManager;
import GUI.ManageTables.PerformanceManager;
import GUI.ManageTables.PodiumManager;
import javafx.application.Application;
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

        //

        Menu stagesMenu = new Menu("Manage Stages");
        Menu performanceMenu = new Menu("Manage Performances");
        Menu artistsMenu = new Menu("Manage Artists");


/*
        MenuItem AddPerformance = new MenuItem("Add Performance");
        performanceMenu.getItems().add(AddPerformance);
        MenuItem RemovePerformance = new MenuItem("Remove Performance");
        performanceMenu.getItems().add(RemovePerformance);
        MenuItem EditPerformance = new MenuItem("Edit Performance");
        performanceMenu.getItems().add(EditPerformance);
/*
        MenuItem AddStage = new MenuItem("Add Stage");
        stagesMenu.getItems().add(AddStage);
        MenuItem RemoveStage = new MenuItem("Remove Stage");
        stagesMenu.getItems().add(RemoveStage);
        MenuItem EditStage = new MenuItem("Edit Stage");
        stagesMenu.getItems().add(EditStage);
/*

        MenuItem AddArtist = new MenuItem("Add Artist");
        artistsMenu.getItems().add(AddArtist);
        MenuItem RemoveArtist = new MenuItem("Remove Artist");
        artistsMenu.getItems().add(RemoveArtist);
        MenuItem EditArtist = new MenuItem("Edit Artist");
        artistsMenu.getItems().add(EditArtist);
        /**/

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
        TableView<AgendaTable> edittable = new TableView<>();
        TableView<AgendaTable> viewtable = new TableView<>();
        //edittable.setEditable(true);

        TableColumn<AgendaTable, String> Time = new TableColumn<>("Time");
        TableColumn<AgendaTable, String> Mainstage = new TableColumn<>("Main Stage");
        Time.setCellValueFactory(new PropertyValueFactory<>("time"));
        Mainstage.setCellValueFactory(new PropertyValueFactory<>("name"));

        //Edit mode table content
        edittable.setItems(getAgendaTable());
        edittable.getColumns().add(Time);
        for (Podium podium : this.getFestivalDay().getPodia()){
            TableColumn stageColumn = new TableColumn(podium.getName());
            edittable.getColumns().add(stageColumn);
            for (Performance performance : podium.getPerformances()) {
               // stageColumn.setCellFactory();
            }
        }

        //View mode table content
        viewtable.setItems(getAgendaTable());
        viewtable.getColumns().add(Time);
        for(Podium podium : this.getFestivalDay().getPodia()){
            viewtable.getColumns().add(new TableColumn(podium.getName()));
        }

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

    private ObservableList<AgendaTable> getAgendaTable() {
        ObservableList<AgendaTable> list = FXCollections.observableArrayList();
        for(int i = 0; i < Times.size(); i++) {
            AgendaTable agendaTable = new AgendaTable(Times.get(i), Mainstage.get(i));
            list.add(agendaTable);
        }
        return list;
    }
}
