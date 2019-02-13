package Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Lucas, Jasper
 */
public class Performance implements Serializable {

    private List<Artist> artists;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int popularity;
    private Podium podium;
    private FestivalDay festivalDay;

    public Performance(LocalDateTime startTime, LocalDateTime endTime, int popularity, FestivalDay festivalDay, Podium podium, Artist ... artists) {
        this.festivalDay = festivalDay;
        this.startTime = startTime;
        this.endTime = endTime;
        this.popularity = popularity;
        this.podium = podium;
        this.artists = new ArrayList<>();
        for (Artist artist : artists){
            this.addArtist(artist);
            artist.addPerformance(this);
        }
        this.podium.addPerformance(this);
    }

    public void addArtist(Artist artist) {
        this.artists.add(artist);
        artist.addPerformance(this);
        this.festivalDay.addArtist(artist);
    }

    public void removeArtist(Artist artist) {
        if (this.artists.contains(artist)) {
            this.artists.remove(artist);
            artist.removePerformance(this);
        } else {
            System.out.println("Artist not found in list!");
        }
    }
    
    public void setPodium(Podium podium) {
        this.podium.removePerformance(this);
        this.podium = podium;
        this.podium.addPerformance(this);
    }

    public List<Artist> getArtists() {
        return artists;
    }

    public Podium getPodium() {
        return podium;
    }

    public void setArtists(List<Artist> artists) {
        this.artists = artists;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public int getPopularity() {
        return popularity;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }

    public FestivalDay getFestivalDay() {
        return festivalDay;
    }

    public void setFestivalDay(FestivalDay festivalDay) {
        this.festivalDay = festivalDay;
    }


}
