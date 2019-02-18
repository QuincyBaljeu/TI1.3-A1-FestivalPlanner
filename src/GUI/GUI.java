package GUI;

import Data.FestivalDay;
import Data.Performance;
import Data.Podium;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.beans.EventHandler;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class GUI extends Application{

    public ObservableList<String> Times =
            FXCollections.observableArrayList(
                    "12:00","13:00","14:00","15:00","16:00","17:00","18:00","19:00","20:00","21:00","22:00", "23:00", "24:00"
            );

    public ObservableList<String> Mainstage =
            FXCollections.observableArrayList(
                    "","","Brennan Heart","","Hardwell","Hardwell","Hardwell","Hardwell","","Da Tweekaz","Da Tweekaz","",""
            );

    @Override
    public void start(Stage Stage) throws Exception {

        FestivalDay festivalDay = new FestivalDay(LocalDate.of(12,12,12));
        Podium mainStage = new Podium("Mainstage", festivalDay);
        Podium jupilerStage = new Podium("Jupilerstage", festivalDay);
        Performance performance = new Performance(LocalDateTime.now(), LocalDateTime.now(), 5, festivalDay, mainStage);
        Performance performance1 = new Performance(LocalDateTime.now(), LocalDateTime.now(), 7, festivalDay, mainStage);
        Performance performance2 = new Performance(LocalDateTime.now(), LocalDateTime.now(), 7, festivalDay, jupilerStage);
        ArrayList<Performance> performances = new ArrayList<>();
        performances.add(performance);
        performances.add(performance1);
        performances.add(performance2);
        festivalDay.addPodium(mainStage);
        festivalDay.addPerformance(performance);

        //Panes
        BorderPane menuBorderPane = new BorderPane();
        BorderPane editBorderPane = new BorderPane();
        BorderPane viewBorderPane = new BorderPane();
        BorderPane testBorderpane = new BorderPane();
        TabPane tabPane = new TabPane();

        //Menu tabs
        Tab Simulation = new Tab("Simulation");
        Tab View = new Tab("View mode");
        Tab Edit = new Tab("Edit mode");
        Tab Test = new Tab("Test");

        tabPane.getTabs().addAll(View,Simulation,Edit,Test);
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        //
        Menu performanceMenu = new Menu("Manage Performance");
        Menu stagesMenu = new Menu("Manage Stages");
        Menu artistsMenu = new Menu("Manage Artists");

        MenuItem AddPerformance = new MenuItem("Add Performance");
        performanceMenu.getItems().add(AddPerformance);
        MenuItem RemovePerformance = new MenuItem("Remove Performance");
        performanceMenu.getItems().add(RemovePerformance);
        MenuItem EditPerformance = new MenuItem("Edit Performance");
        performanceMenu.getItems().add(EditPerformance);

        MenuItem AddStage = new MenuItem("Add Stage");
        stagesMenu.getItems().add(AddStage);
        MenuItem RemoveStage = new MenuItem("Remove Stage");
        stagesMenu.getItems().add(RemoveStage);
        MenuItem EditStage = new MenuItem("Edit Stage");
        stagesMenu.getItems().add(EditStage);

        MenuItem AddArtist = new MenuItem("Add Artist");
        artistsMenu.getItems().add(AddArtist);
        MenuItem RemoveArtist = new MenuItem("Remove Artist");
        artistsMenu.getItems().add(RemoveArtist);
        MenuItem EditArtist = new MenuItem("Edit Artist");
        artistsMenu.getItems().add(EditArtist);

        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(performanceMenu,stagesMenu,artistsMenu);

        TextArea textArea = new TextArea();
        Button test = new Button("Test");
        test.setOnAction(event -> textArea.setText(festivalDay.performancePerStage(performances)));

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
        for (Podium podium : festivalDay.getPodia()){
            edittable.getColumns().add(new TableColumn(podium.getName()));
        }

        //View mode table content
        viewtable.setItems(getAgendaTable());
        viewtable.getColumns().add(Time);
        for(Podium podium : festivalDay.getPodia()){
            viewtable.getColumns().add(new TableColumn(podium.getName()));
        }

        //Adds table to borderpane
        editBorderPane.setCenter(edittable);
        editBorderPane.setTop(menuBar);
        viewBorderPane.setTop(viewtable);
        testBorderpane.setTop(test);
        testBorderpane.setCenter(textArea);
        //Adds borderpane to tabpane
        Edit.setContent(editBorderPane);
        View.setContent(viewtable);
        Test.setContent(testBorderpane);
        //Adds tabpane to final borderpane
        menuBorderPane.setCenter(tabPane);



        Stage.setTitle("Festival Planner");
        Stage.setWidth(500);
        Stage.setHeight(500);
        Scene scene = new Scene(menuBorderPane);
        Stage.setScene(scene);
        Stage.show();

    }

    private ObservableList<AgendaTable> getAgendaTable() {
        ObservableList<AgendaTable> list = FXCollections.observableArrayList();
        for(int i = 0; i < Times.size(); i++) {
            AgendaTable agendaTable = new AgendaTable(Times.get(i), Mainstage.get(i));
            list.add(agendaTable);
        }
        return list;
    }

    public static void main(String[] args) {
        launch(GUI.class);
    }
}
