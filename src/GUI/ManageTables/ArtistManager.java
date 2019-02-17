package GUI.ManageTables;

import Data.Artist;
import Data.FestivalDay;
import Data.Genre;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.util.concurrent.atomic.AtomicReference;

public class ArtistManager {

    private Stage stage;
    private Scene scene;
    private BorderPane borderPane;
    private FestivalDay festivalDay;
    private TableView tableView = new TableView<Artist>();

    public ArtistManager(FestivalDay festivalDay) {
        this.festivalDay = festivalDay;
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

            try {
                this.festivalDay.getAgendaModule().save();
            } catch (Exception x){
                x.printStackTrace();
            }
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

            try {
                this.festivalDay.getAgendaModule().save();
            } catch (Exception x){
                x.printStackTrace();
            }
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

            try {
                this.festivalDay.getAgendaModule().save();
            } catch (Exception x){
                x.printStackTrace();
            }
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

            try {
                this.festivalDay.getAgendaModule().save();
            } catch (Exception x){
                x.printStackTrace();
            }
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

            try {
                this.festivalDay.getAgendaModule().save();
            } catch (Exception x){
                x.printStackTrace();
            }
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
                                        festivalDay.removeArtist(artist);
                                        tableView.setItems(FXCollections.observableList(festivalDay.getArtists()));
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
                Artist newArtist = new Artist(nameField.getCharacters().toString(), genreField.getSelectionModel().getSelectedItem(), this.festivalDay, artistTypeField.getCharacters().toString(), tempFilePath.toString(), countryField.getCharacters().toString());
                this.festivalDay.addArtist(newArtist);
                this.tableView.setItems(FXCollections.observableList(this.festivalDay.getArtists()));
                nameField.clear();
                genreField.getSelectionModel().clearSelection();
                artistTypeField.clear();
                countryField.clear();
                try {
                    festivalDay.getAgendaModule().save();
                } catch (Exception x){
                    x.printStackTrace();
                }

            } else {
                System.out.println("Not all Fields have been filled");
            }
        });



bot.setSpacing(10);
        bot.getChildren().addAll(nameSet, genreSet, artistTypeSet, filePathSet, countrySet, addSet);
        this.borderPane.setBottom(bot);

        this.tableView.getColumns().addAll(name, genre, artistType, filePathProfilePic, countryOfOrigin, extraInfo, delete);

        this.tableView.setItems(FXCollections.observableList(this.festivalDay.getArtists()));

        this.stage.setResizable(true);
        this.stage.setTitle("Artist List");
        this.stage.setScene(this.scene);
        this.stage.show();
    }


}
