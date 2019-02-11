package Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Lucas, Jasper
 */
public class AgendaModule {
    private List<FestivalDay> festivalDays;

    public AgendaModule() {
        this.festivalDays = new ArrayList<>();
    }

    public void addFestivalDay(FestivalDay ... festivalDays) {
        for (FestivalDay festivalDay : festivalDays){
            this.festivalDays.add(festivalDay);
        }
    }

    public void removeFestivalDay(FestivalDay ... festivalDays) {
        for (FestivalDay festivalDay : festivalDays){
            if (this.festivalDays.contains(festivalDay)) {
                this.festivalDays.remove(festivalDay);
            } else {
                System.out.println("Couldn't find stage to remove");
            }
        }
    }
}