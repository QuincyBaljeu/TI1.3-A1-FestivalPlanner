package Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Lucas, Jasper
 */
public class DataList {
    private List<Artist> artists;
    private List<Performance> performances;
    private List<Stage> stages;
    private List<FestivalDay> festivalDays;

    public DataList() {
        this.artists = new ArrayList<>();
        this.performances = new ArrayList<>();
        this.stages = new ArrayList<>();
        this.festivalDays = new ArrayList<>();
    }

    public void addArtist(Artist artist) {
        this.artists.add(artist);
    }

    public void addPerformance(Performance performance) {
        this.performances.add(performance);
    }

    public void addStage(Stage stage) {
        this.stages.add(stage);
    }

    public void addFestivalDay(FestivalDay festivalDay) {
        this.festivalDays.add(festivalDay);
    }

    public void removeArtist(Artist artist) {
        if (this.artists.contains(artist)) {
            this.artists.remove(artist);
        } else {
            System.out.println("Couldn't find artist to remove");
        }
    }

    public void removePerformance(Performance performance) {
        if (this.performances.contains(performance)) {
            this.performances.remove(performance);
        } else {
            System.out.println("Couldn't find performance to remove");
        }
    }

    public void removeStage(Stage stage) {
        if (this.stages.contains(stage)) {
            this.stages.remove(stage);
        } else {
            System.out.println("Couldn't find stage to remove");
        }
    }

    public void removeFestivalDay(FestivalDay festivalDay) {
        if (this.festivalDays.contains(festivalDay)) {
            this.festivalDays.remove(festivalDay);
        } else {
            System.out.println("Couldn't find stage to remove");
        }
    }
}
