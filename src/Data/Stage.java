package Data;

import java.util.List;

/**
 * @author Lucas, Jasper
 */
public class Stage {
    private String name;
    private List<Performance> performances;

    public Stage(String name, List<Performance> performances) {
        this.name = name;
        this.performances = performances;
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

    public void setPerformances(List<Performance> performances) {
        this.performances = performances;
    }
}
