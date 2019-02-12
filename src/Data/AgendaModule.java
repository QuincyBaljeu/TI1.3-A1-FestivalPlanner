package Data;

import Data.FileIO.Storable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Lucas, Jasper
 */
public class AgendaModule extends Storable {
    private List<FestivalDay> festivalDays;

    public AgendaModule(String filePath) {
        super(filePath);
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
                System.out.println("Couldn't find podium to remove");
            }
        }
    }
}
