package GUI.ManageTables;

import Data.FestivalDay;
import javafx.application.Application;
import javafx.stage.Stage;

import java.time.LocalDate;

public class TestManagers extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        FestivalDay festivalDay = new FestivalDay(LocalDate.of(2000, 8, 28));;


        new ArtistManager(festivalDay);
    }
}
