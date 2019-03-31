package Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FestivalDay implements Serializable {

    /**
     * @author Lucas, Jasper
     */
    private List<Podium> podia;
    private List<Performance> performances;
    private List<Artist> artists;
    private LocalDate date;
    private AgendaModule agendaModule;

    public FestivalDay(LocalDate date) {
        this.podia = new ArrayList<>();
        this.performances = new ArrayList<>();
        this.artists = new ArrayList<>();
        this.date = date;
    }

    public AgendaModule getAgendaModule(){
        return agendaModule;
    }

    public void setAgendaModule(AgendaModule agendaModule){
        this.agendaModule = agendaModule;
    }

    public void addArtist(Artist artist) {
        if (!this.artists.contains(artist)) {
            this.artists.add(artist);
            for (Performance performance : artist.getPerformances()){
                this.addPerformance(performance);
            }
        } else {
            System.out.println("Artist allready in FestivalDay's list!");
        }
    }

    public void removeArtist(Artist artist) {
        if (this.artists.contains(artist)) {
            this.artists.remove(artist);
            for (int i = 0; i < artist.getPerformances().size(); i++) {
                Performance performance = artist.getPerformances().get(i);
                if (performance.getArtists().contains(artist)){
                    performance.removeArtist(artist);
                    i -= 1;
                }
                if (performance.getArtists().size() == 0){
                    this.removePerformance(performance);
                }
            }

        } else {
            System.out.println("Artist does not exist in FestivalDay's list!");
        }
    }

    /**
     * Adds a performance, it's podium and artists to this festivalDay
     * @param performance the performance to add
     */
    public void addPerformance(Performance performance) {
        if (!this.performances.contains(performance)) {
            this.performances.add(performance);
            for (Artist artist : performance.getArtists()){
                this.addArtist(artist);
            }
            this.addPodium(performance.getPodium());
        } else {
            System.out.println("Performance allready in FestivalDay's list!");
        }
    }

    /**
     * Removes the given performance from the festival day, from it's podium and from it's artist
     * @param performance The performance to remove
     */
    public void removePerformance(Performance performance){
        if(this.performances.contains(performance)){
            this.performances.remove(performance);
            for(Artist artist : performance.getArtists()){
                artist.removePerformance(performance);
            }
            performance.getPodium().removePerformance(performance);
        } else {
            System.out.println("Performance does not exist in FestivalDay's list!");
        }
    }

    public void addPodium(Podium podium) {
        if (!this.podia.contains(podium)) {
            this.podia.add(podium);
            for (Performance performance : podium.getPerformances()){
                this.addPerformance(performance);
            }
        } else {
            System.out.println("podium already in FestivalDay's list!");
        }
    }

    public void removePodium(Podium podium){
        if(this.podia.contains(podium)){
            this.podia.remove(podium);
			List<Performance> toDelete = new ArrayList<>(podium.getPerformances());
			for (Performance performance : toDelete){
				removePerformance(performance);
			}
        } else {
            System.out.println("Podium does not exist in FestivalDay's list!");
        }
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List<Podium> getPodia() {
        return this.podia;
    }

    public void setPodia(List<Podium> podia) {
        this.podia = podia;
    }

    public List<Performance> getPerformances() {
        return performances;
    }

    public void setPerformances(List<Performance> performances) {
        this.performances = performances;
    }

    public List<Artist> getArtists() {
        return artists;
    }

    public void setArtists(List<Artist> artists) {
        this.artists = artists;
    }

    public Podium getPodiumViaName(String podiumName) {
        for (Podium podium : this.podia) {
            if (podium.getName().equals(podiumName)) {
                return podium;
            }
        }
        return null;
    }

    public Artist getArtistViaName(String artistName) {
        for (Artist artist : this.artists) {
            if (artist.getName().equals(artistName)) {
                return artist;
            }
        }
        return null;
    }

}
