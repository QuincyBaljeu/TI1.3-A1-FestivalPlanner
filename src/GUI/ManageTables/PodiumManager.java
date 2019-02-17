package GUI.ManageTables;

import Data.Artist;
import Data.FestivalDay;
import Data.Performance;
import Data.Podium;
import GUI.GUI;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * The graphical user interface to manage podium instances in a FestivalDay Object
 */
public class PodiumManager extends DataManager {

    private Stage stage;
    private Scene scene;
    private BorderPane borderPane;
    private TableView tableView = new TableView<Podium>();

    public PodiumManager(FestivalDay festivalDay, GUI parent) {
        super(festivalDay, parent);
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

    private DataManager getSuperClassInstance(){
        return super.getThis();
    }

    private void InitializeScene() {
        tableView.setEditable(true);
        TableColumn<Podium, String> name = new TableColumn("name");
        TableColumn<Podium, Boolean> delete = new TableColumn<>("Delete");
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

            processChanges();
        });
        // begin delete

        delete.setCellValueFactory(new PropertyValueFactory<>("DUMMY"));

        Callback<TableColumn<Podium, Boolean>, TableCell<Podium, Boolean>> cellFactorydel
                =
                new Callback<TableColumn<Podium, Boolean>, TableCell<Podium, Boolean>>() {
                    @Override
                    public TableCell call(final TableColumn<Podium, Boolean> param) {
                        final TableCell<Podium, Boolean> cell = new TableCell<Podium, Boolean>() {

                            final Button button = new Button("Del");

                            @Override
                            public void updateItem(Boolean item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                    setText(null);
                                } else {
                                    button.setOnAction(event -> {
                                        Podium podium = getTableView().getItems().get(getIndex());
                                        FestivalDay festivalDay = getSuperClassInstance().getFestivalDay();
                                        festivalDay.removePodium(podium);
                                        tableView.setItems(FXCollections.observableList(festivalDay.getPodia()));
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


        //add menu
        HBox bot = new HBox();
        TextField nameField = new TextField();
        Label nameLabel = new Label("Name");
        VBox nameSet = new VBox(nameLabel, nameField);

        Button add = new Button("Add");
        Label addLabel = new Label("Add Podium");
        VBox addSet = new VBox(addLabel, add);

        add.setOnAction(e -> {
            if (!nameField.getCharacters().toString().isEmpty()) {
                Podium newPodium = new Podium(nameField.getCharacters().toString(), super.getFestivalDay());
                super.getFestivalDay().addPodium(newPodium);
                this.tableView.setItems(FXCollections.observableList(super.getFestivalDay().getPodia()));

                nameField.clear();

                processChanges();
            }

        });

        bot.setSpacing(10);

        bot.getChildren().addAll(nameSet, addSet);

        this.borderPane.setBottom(bot);




        this.tableView.getColumns().addAll(name, delete);

        this.tableView.setItems(FXCollections.observableList(super.getFestivalDay().getPodia()));

        this.stage.setResizable(true);
        this.stage.setTitle("Podium List");
        this.stage.setScene(this.scene);
        this.stage.show();
    }
}
