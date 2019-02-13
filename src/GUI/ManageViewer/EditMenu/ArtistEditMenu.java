package GUI.ManageViewer.EditMenu;

import Data.Artist;
import Data.FestivalDay;
import Data.Genre;
import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class ArtistEditMenu extends EditMenu<Artist> {
    private Artist artist = null;

    private String name;
    private Genre genre;
    private String artistType;
    private String filePathProfilePic;
    private TextArea extraInfo;
    private String country;
    private FestivalDay festivalDay;

    public ArtistEditMenu(Artist object, FestivalDay festivalDay) {
        super();
        this.festivalDay = festivalDay;
        this.artist = object;
        super.initScene();
    }

    public ArtistEditMenu(FestivalDay festivalDay) {
        super();
        this.festivalDay = festivalDay;
        super.initScene();
    }

    @Override
    public List<String> getVariableTypes() {
        ArrayList<String> varTypes = new ArrayList<>();
        varTypes.add("Name");
        varTypes.add("Genre");
        varTypes.add("Artist Type");
        varTypes.add("ProfilePic");
        varTypes.add("Extra Info*");
        varTypes.add("Country of Origin");
        return varTypes;
    }

    @Override
    public List<Node> getInputMethod() {
        ArrayList<Node> inputMethod = new ArrayList<>();

        TextField name = new TextField();
        name.textProperty().addListener(e -> {
            this.name = name.getCharacters().toString();
        });


        ChoiceBox<Genre> genre = new ChoiceBox<>(FXCollections.observableArrayList(Genre.values() ));
        genre.setOnAction(e -> {
            this.genre = genre.getValue();
        });

        TextField artistType = new TextField();
        artistType.textProperty().addListener(event -> {
            this.artistType = artistType.getText();
        });

        Button profilePicture = new Button("Select Profile Picture");
        FileChooser fileChooser = new FileChooser();
        profilePicture.setOnAction(e -> {
            this.filePathProfilePic = fileChooser.showOpenDialog(new Stage()).getPath();
        });

        this.extraInfo = new TextArea();

        TextField country = new TextField();
        country.textProperty().addListener(e -> {
            this.country = country.getText();
        });

        if (!(this.artist == null)) {
            name.textProperty().setValue(this.artist.getName());
            genre.setValue(Genre.valueOf(this.artist.getGenre().toString()));
            artistType.textProperty().setValue(this.artist.getArtistType());
            this.filePathProfilePic = this.artist.getFilePathProfilePicture();
            country.textProperty().setValue(this.artist.getCountry());
            System.out.println("I did da thing");
        }


        inputMethod.add(name);
        inputMethod.add(genre);
        inputMethod.add(artistType);
        inputMethod.add(profilePicture);
        inputMethod.add(this.extraInfo);
        inputMethod.add(country);


        return inputMethod;
    }

    @Override
    public Button getDoneButton() {
        Button done = new Button("Done");
        done.setOnAction(e -> {
            System.out.println(this.name + "\n" +
                    this.genre.toString() + "\n" +
                    this.artistType + "\n" +
                    this.filePathProfilePic + "\n" +
                    this.extraInfo.getText() +"\n" +
                    this.country
                );
        });
        return done;
    }
}
