package GUI.ManageTables;

import Data.*;
import javafx.application.Application;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class TestManagers extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        FestivalDay festivalDay =
                new FestivalDay(
                        LocalDate.now()
                );
        festivalDay.addPerformance(
                new Performance(
                        LocalDateTime.now(),
                        LocalDateTime.now(),
                        3,
                        festivalDay,
                        new Podium("new york", festivalDay),
                        new Artist(
                                "Henry",
                                Genre.CHRISTIAN_RAP,
                                festivalDay,
                                "French",
                                "",
                                "Amazeballs",
                                "Russia"
                        )
                )
        );
        new ArtistManager(festivalDay);
    }
}
