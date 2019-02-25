package GUI.ManageTables;

import Data.Artist;
import Data.FestivalDay;
import Data.Genre;
import GUI.GUI;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.util.concurrent.atomic.AtomicReference;

/**
 * The graphical user interface to manage artist instances in a FestivalDay Object
 */
public class ArtistManager extends DataManager {

    private Stage stage;
    private Scene scene;
    private BorderPane borderPane;
    private TableView tableView = new TableView<Artist>();

    public ArtistManager(FestivalDay festivalDay, GUI parent) {
        super(festivalDay, parent);
        this.scene = new Scene(this.borderPane = new BorderPane());
        this.stage = new Stage();
        HBox top = new HBox();
        Label title = new Label("Artist List");
        top.getChildren().add(title);
        top.setAlignment(Pos.CENTER);
        title.setFont(new Font("Arial", 40));
        this.borderPane.setTop(top);
        this.borderPane.setCenter(this.tableView);
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
        //adding the columns
        tableView.setEditable(true);
        TableColumn<Artist, String> name = new TableColumn("name");
        TableColumn<Artist, Genre> genre = new TableColumn("Genre");
        TableColumn<Artist, String> artistType = new TableColumn("artistType");
        TableColumn<Artist, String> filePathProfilePic = new TableColumn("ProfilePicture");
        TableColumn<Artist, String> countryOfOrigin = new TableColumn("country");
        TableColumn<Artist, String> extraInfo = new TableColumn<>("extraInformation");
        TableColumn<Artist, Boolean> delete = new TableColumn<>("delete");


        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        name.setCellFactory(TextFieldTableCell.forTableColumn());
        name.setMinWidth(100);

        name.setOnEditCommit((TableColumn.CellEditEvent<Artist, String> event) -> {
            TablePosition<Artist, String> pos = event.getTablePosition();
            String newName = event.getNewValue();

            int row = pos.getRow();
            Artist artist = event.getTableView().getItems().get(row);

            artist.setName(newName);

			super.processChanges();
        });
        // genre
        ObservableList<Genre> genreList = FXCollections.observableArrayList(Genre.values());

        genre.setCellValueFactory(param -> {
            if (param.getValue() != null) {
                Artist artist = param.getValue();
                if (artist.getGenre() != null) {
                    Genre gernre = Genre.getByCode(artist.getGenre().toString());
                    return new SimpleObjectProperty<>(gernre);
                }
            }
            return new SimpleObjectProperty<>(null);
        });

        genre.setCellFactory(ComboBoxTableCell.forTableColumn(genreList));

        genre.setOnEditCommit((TableColumn.CellEditEvent<Artist, Genre> event) -> {
            TablePosition<Artist, Genre> pos = event.getTablePosition();

            Genre newGenre = event.getNewValue();

            int row = pos.getRow();
            Artist artist = event.getTableView().getItems().get(row);

            artist.setGenre(newGenre);

			super.processChanges();
        });


        genre.setMinWidth(150);

        // end genre


        artistType.setCellValueFactory(new PropertyValueFactory<>("artistType"));
        artistType.setCellFactory(TextFieldTableCell.forTableColumn());
        artistType.setMinWidth(100);

        artistType.setOnEditCommit((TableColumn.CellEditEvent<Artist, String> event) -> {
            TablePosition<Artist, String> pos = event.getTablePosition();
            String newArtistType = event.getNewValue();

            int row = pos.getRow();
            Artist artist = event.getTableView().getItems().get(row);

            artist.setArtistType(newArtistType);

			super.processChanges();
        });

        countryOfOrigin.setCellValueFactory(new PropertyValueFactory<>("country"));
        countryOfOrigin.setCellFactory(TextFieldTableCell.<Artist> forTableColumn());
        countryOfOrigin.setMinWidth(100);

        countryOfOrigin.setOnEditCommit((TableColumn.CellEditEvent<Artist, String> event) -> {
            TablePosition<Artist, String> pos = event.getTablePosition();
            String newCountry = event.getNewValue();

            int row = pos.getRow();
            Artist artist = event.getTableView().getItems().get(row);

            artist.setCountry(newCountry);

			super.processChanges();
        });

        extraInfo.setCellValueFactory(new PropertyValueFactory<>("extraInformation"));
        extraInfo.setCellFactory(TextFieldTableCell.<Artist> forTableColumn());
        extraInfo.setMinWidth(300);

        extraInfo.setOnEditCommit((TableColumn.CellEditEvent<Artist, String> event) -> {
            TablePosition<Artist, String> pos = event.getTablePosition();
            String newInfo = event.getNewValue();

            int row = pos.getRow();
            Artist artist = event.getTableView().getItems().get(row);

            artist.setExtraInformation(newInfo);

			super.processChanges();
        });


        //button
        filePathProfilePic.setCellValueFactory(new PropertyValueFactory<>("DUMMY"));

        Callback<TableColumn<Artist, String>, TableCell<Artist, String>> cellFactory
                =
                new Callback<TableColumn<Artist, String>, TableCell<Artist, String>>() {
                    @Override
                    public TableCell call(final TableColumn<Artist, String> param) {
                        final TableCell<Artist, String> cell = new TableCell<Artist, String>() {

                            final Button button = new Button("Choose Picture");

                            @Override
                            public void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                    setText(null);
                                } else {
                                    button.setOnAction(event -> {
                                        Artist artist = getTableView().getItems().get(getIndex());
                                        FileChooser fileChooser = new FileChooser();
                                        artist.setFilePathProfilePicture(fileChooser.showOpenDialog(new Stage()).getPath());

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

        filePathProfilePic.setCellFactory(cellFactory);
        //end button
        // begin delete

        delete.setCellValueFactory(new PropertyValueFactory<>("DUMMY"));

        Callback<TableColumn<Artist, Boolean>, TableCell<Artist, Boolean>> cellFactorydel
                =
                new Callback<TableColumn<Artist, Boolean>, TableCell<Artist, Boolean>>() {
                    @Override
                    public TableCell call(final TableColumn<Artist, Boolean> param) {
                        final TableCell<Artist, Boolean> cell = new TableCell<Artist, Boolean>() {

                            final Button button = new Button("Del");

                            @Override
                            public void updateItem(Boolean item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                    setText(null);
                                } else {
                                    button.setOnAction(event -> {
                                        Artist artist = getTableView().getItems().get(getIndex());
                                        getFestivalDay().removeArtist(artist);
                                        tableView.setItems(FXCollections.observableList(getFestivalDay().getArtists()));

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

        //end Delete



        HBox bot = new HBox();

        TextField nameField = new TextField();
        Label nameLabel = new Label("Name");
        VBox nameSet = new VBox(nameLabel, nameField);

        ComboBox<Genre> genreField = new ComboBox<>();
        genreField.setItems(FXCollections.observableArrayList(Genre.values()));
        Label genreLabel = new Label("Genre");
        VBox genreSet = new VBox(genreLabel, genreField);

        TextField artistTypeField = new TextField();
        Label artistTypeLabel = new Label("Artist Type");
        VBox artistTypeSet = new VBox(artistTypeLabel, artistTypeField);

        Button filePathField = new Button("Choose Picture");
        Label filePathLaber = new Label("Profile Picture");
        VBox filePathSet = new VBox(filePathLaber, filePathField);
        AtomicReference<String> tempFilePath = new AtomicReference<>("");
        filePathField.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            tempFilePath.set(fileChooser.showOpenDialog(new Stage()).getPath());
        });

        TextField countryField = new TextField();
        Label countryLabel = new Label("Country");
        VBox countrySet = new VBox(countryLabel, countryField);

        Button add = new Button("Add");
        Label addLabel = new Label("Add Artist");
        VBox addSet = new VBox(addLabel, add);

        add.setOnAction(e -> {
            if (!nameField.getCharacters().toString().isEmpty() && !genreField.getSelectionModel().isEmpty() && !artistTypeField.getCharacters().toString().isEmpty() && !tempFilePath.toString().isEmpty() && !countryField.getCharacters().toString().isEmpty()) {
                Artist newArtist = new Artist(nameField.getCharacters().toString(), genreField.getSelectionModel().getSelectedItem(), this.getFestivalDay(), artistTypeField.getCharacters().toString(), tempFilePath.toString(), countryField.getCharacters().toString());
                if (!checkExistence(newArtist)) {
                    super.getFestivalDay().addArtist(newArtist);
                } else {
                    System.out.println("Oh no's it already existst ;v;");
                    launchPopup();
                }

                this.tableView.setItems(FXCollections.observableList(this.getFestivalDay().getArtists()));
                nameField.clear();
                genreField.getSelectionModel().clearSelection();
                artistTypeField.clear();
                countryField.clear();

				super.processChanges();
            } else {
                System.out.println("Not all Fields have been filled");
            }
        });



bot.setSpacing(10);
        bot.getChildren().addAll(nameSet, genreSet, artistTypeSet, filePathSet, countrySet, addSet);
        this.borderPane.setBottom(bot);

        this.tableView.getColumns().addAll(name, genre, artistType, filePathProfilePic, countryOfOrigin, extraInfo, delete);

        this.tableView.setItems(FXCollections.observableList(this.getFestivalDay().getArtists()));

        this.stage.setResizable(true);
        this.stage.setTitle("Artist List");
        this.stage.setScene(this.scene);
        this.stage.show();
    }

    private boolean checkExistence(Artist newArtist) {
        for (int i = 0; i < super.getFestivalDay().getArtists().size() ; i++) {
            if (super.getFestivalDay().getArtists().get(i).getName().equals(newArtist.getName())){
                return true;
            }
        }
        return false;
    }

    private void launchPopup() {
        Stage popup = new Stage();
        VBox vBox = new VBox();
        Label label = new Label("An artist by this name already exists.");
        Button button = new Button("OK");

        button.setMinWidth(40);
        button.setMinHeight(40);
        button.setOnAction(event -> popup.close());
        label.setFont(new Font("Arial", 40));
        vBox.getChildren().addAll(label, button);
        popup.setScene(new Scene(vBox));
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(10);
        popup.setTitle("ERROR Double name found");
        popup.showAndWait();

    }


}
