package Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Lucas, Jasper
 */
public class Stage {
    private String name;
    private List<Performance> performances;

    public Stage(String name) {
        this.name = name;
        this.performances = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Performance> getPerformances() {
        return performances;
    }

    public void addPerformance(Performance performance) {
        this.performances.add(performance);
    }

    public void removePerfomance(Performance performance) {
        if (this.performances.contains(performance)) {
            this.performances.remove(performance);
        } else {
            System.out.println("Performance not found in list!");
        }
    }
}
