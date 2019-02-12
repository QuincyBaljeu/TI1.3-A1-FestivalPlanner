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
        this.startTime = startTime;
        this.endTime = endTime;
        this.popularity = popularity;
        this.podium = podium;
        this.artists = new ArrayList<>();
        this.podium.addPerformance(this);
        for (Artist artist : artists){
            this.artists.add(artist);
            artist.addPerformance(this);
        }
        this.festivalDay = festivalDay;
    }

    public void addArtist(Artist artist) {
        this.artists.add(artist);
        artist.addPerformance(this);
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
        this.podium.removePerfomance(this);
        this.podium = podium;
        this.podium.addPerformance(this);
    }
}
