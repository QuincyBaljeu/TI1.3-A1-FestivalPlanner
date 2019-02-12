package Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Lucas, Jasper
 */
public class Podium implements Serializable {
    private String name;
    private List<Performance> performances;
    private FestivalDay festivalDay;

    public Podium(String name, FestivalDay festivalDay) {
        this.name = name;
        this.performances = new ArrayList<>();
        this.festivalDay = festivalDay;
    }

    public void addPerformance(Performance performance) {
        if (!this.performances.contains(performance)) {
            this.performances.add(performance);
            this.festivalDay.addPerformance(performance);
        } else {
            System.out.println("Performance allready in Podium's list");
        }
    }

    public void removePerfomance(Performance performance) {
        if (this.performances.contains(performance)) {
            this.performances.remove(performance);
        } else {
            System.out.println("Performance not found Podium's list in list!");
        }
    }

    public List<Performance> getPerformances() {
        return performances;
    }
}
