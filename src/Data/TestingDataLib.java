package Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class TestingDataLib {
    public static AgendaModule getDummyAgendaModule(String filePath){
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
                ),
                new Artist(
                    "Barack Omama",
                    Genre.CHRISTIAN_RAP,
                    festivalDay,
                    "Italian",
                    "",
                    "I got myself a drone",
                    "CZech Republic"
                )
            )
        );
        AgendaModule agendaModule = new AgendaModule(filePath);
        agendaModule.addFestivalDay(festivalDay);
        return agendaModule;
    }
}
