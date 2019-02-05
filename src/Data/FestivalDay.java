package Data;

import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FestivalDay {

    private List<Stage> stages;
    private LocalDate date;


    public FestivalDay(LocalDate date) {
        this.date = date;
        this.stages = new ArrayList<>();
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
