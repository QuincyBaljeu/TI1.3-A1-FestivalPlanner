import Data.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        FestivalDay festivalDay = new FestivalDay(new LocalDate(2019,2,20));
        Stage stage = new Stage("Stage naam");
        Performance performance = new Performance(new LocalDateTime(festivalDay,12), new LocalDateTime(festivalDay,14), 100, stage);
        performance.addArtist(new Artist("Bob Marley", Genre.PUNK, "Singer", "C://Stuff", "Likes nuts","Jamaica"));
    }
}
