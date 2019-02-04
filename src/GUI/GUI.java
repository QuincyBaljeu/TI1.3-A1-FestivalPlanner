package GUI;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class GUI extends Application{

    private ObservableList<String> Times =
            FXCollections.observableArrayList(
                    "10:00","11:00","12:00","13:00","14:00","15:00","16:00","17:00","18:00","19:00","20:00"
            );

    @Override
    public void start(Stage Stage) throws Exception {

        BorderPane menuBorderPane = new BorderPane();
        BorderPane editBorderPane = new BorderPane();
        TabPane tabPane = new TabPane();

        Tab Simulation = new Tab("Simulation");
        Tab Map = new Tab("Map");
        Tab Edit = new Tab("Edit mode");

        tabPane.getTabs().addAll(Simulation,Map,Edit);
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

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

        TableView table = new TableView();
        table.setEditable(true);

        TableColumn Time = new TableColumn("Time");
        TableColumn Mainstage = new TableColumn("Main Stage");
        table.getColumns().addAll(Time,Mainstage);
        table.setItems(Times);

        /**zet de tabel in de de borderpane**/
        editBorderPane.setCenter(table);
        editBorderPane.setTop(menuBar);
        /**zet de borderpane(tabel) in de tabpane**/
        Edit.setContent(editBorderPane);
        /**zet de tabpane in de eind borderpane**/
        menuBorderPane.setCenter(tabPane);

        Stage.setTitle("Festival Planner");
        Stage.setWidth(500);
        Stage.setHeight(500);
        Scene scene = new Scene(menuBorderPane);
        Stage.setScene(scene);
        Stage.show();

    }

    public static void main(String[] args) {
        launch(GUI.class);
    }
}
