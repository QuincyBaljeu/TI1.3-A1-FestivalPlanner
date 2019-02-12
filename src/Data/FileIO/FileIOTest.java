package Data.FileIO;

import Data.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class FileIOTest extends Application {
    public static void main(String[] args){
        launch(FileIOTest.class);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setScene(
            new Scene(
                new GridPane()
            )
        );
        FileChooser fileChooser =
            new FileChooser();
        fileChooser.getExtensionFilters().add(
            new FileChooser.ExtensionFilter(
                "binary files (*.bin)", "*.bin"
            )
        );
        File saveFile = fileChooser.showSaveDialog(primaryStage);
        AgendaModule dummyData = this.getDummyData(saveFile.getPath());
        dummyData.save();
    }

    private AgendaModule getDummyData(String filepath){
        FestivalDay festivalDay = new FestivalDay(LocalDate.now());

        festivalDay.addPerformance(
            new Performance(
                LocalDateTime.now(),
                LocalDateTime.now(),
                5,
                festivalDay,
                new Podium(
                    "Mainpodium",
                    festivalDay),
                new Artist(
                    "Henk",
                    Genre.COUNTRY,
                    festivalDay,
                    "Dead",
                    "",
                    "Likes his mom",
                    "South west new maryland"
                )
            )
        );

        AgendaModule agendaModule = new AgendaModule(filepath);
        agendaModule.addFestivalDay(festivalDay);
        return agendaModule;
    }
}
