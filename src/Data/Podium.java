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
        } else {
            System.out.println("Performance already in Podium's list");
        }
    }

    public void removePerformance(Performance performance) {
        if (this.performances.contains(performance)) {
            this.performances.remove(performance);
        } else {
            System.out.println("Performance not found Podium's list in list!");
        }
    }

    public List<Performance> getPerformances() {
        return performances;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPerformances(List<Performance> performances) {
        this.performances = performances;
    }

    public FestivalDay getFestivalDay() {
        return festivalDay;
    }

    public void setFestivalDay(FestivalDay festivalDay) {
        this.festivalDay = festivalDay;
    }
}
