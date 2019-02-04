package Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FestivalDay {

    private List<Stage> stages;
    private List<Performance> performances;
    private List<Artist> artists;
    private LocalDate date;

    public FestivalDay(LocalDate date) {
        this.stages = new ArrayList<>();
        this.performances = new ArrayList<>();
        this.artists = new ArrayList<>();
        this.date = date;
    }

    public void addArtist(Artist artist){
        if(!this.artists.contains(artist)){
            this.artists.add(artist);
        } else {
            System.out.println("Artist allready in FestivalDay's list!");
        }
    }

    public void removeArtist(Artist artist){
        if(this.artists.contains(artist)){
            this.artists.remove(artist);
        } else {
            System.out.println("Artist does not exist in FestivalDay's list!");
        }
    }

    public void addPerformance(Performance performance){
        if(!this.performances.contains(performance)){
            this.performances.add(performance);
        } else {
            System.out.println("Performance allready in FestivalDay's list!");
        }
    }

    public void removePerformance(Performance performance){
        if(this.performances.contains(performance)){
            this.performances.remove(performance);
        } else {
            System.out.println("Performance does not exist in FestivalDay's list!");
        }
    }

    public void addStage(Stage stage){
        if(!this.stages.contains(stage)){
            this.stages.add(stage);
        } else {
            System.out.println("Stage allready in FestivalDay's list!");
        }
    }

    public void removeStage(Stage stage){
        if(this.stages.contains(stage)){
            this.stages.remove(stage);
        } else {
            System.out.println("Stage does not exist in FestivalDay's list!");
        }
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List<Stage> getStages() {
        return stages;
    }

    public List<Performance> getPerformances() {
        return performances;
    }

    public List<Artist> getArtists() {
        return artists;
    }
}
