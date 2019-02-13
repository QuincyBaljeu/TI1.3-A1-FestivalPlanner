import Data.*;
import GUI.GUI;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static javafx.application.Application.launch;

public class Main {
    public static void main(String[] args) {
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
        GUI.festivalDay = festivalDay;
        launch(GUI.class);
    }
}
