package Data;

import sun.misc.Perf;

import java.util.List;

/**
 * @author Lucas, Jasper
 */
public class Artist {
    private String name; //Full name
    private Enum<Genre> genre;
    private String artistType;
    private String filePathPFP; //PFP= Profile Picture
    private String extraInformation; //Arbitrary text
    private String country;      //Country
    private List<Performance> performances;

    public Artist(String name, Enum<Genre> genre, String artistType, String filePathPFP, String extraInformation, String country) {
        this.name = name;
        this.genre = genre;
        this.artistType = artistType;
        this.filePathPFP = filePathPFP;
        this.extraInformation = extraInformation;
        this.country = country;
    }

    public Artist(String name, Enum<Genre> genre, String artistType, String filePathPFP, String country) {
        this.name = name;
        this.genre = genre;
        this.artistType = artistType;
        this.filePathPFP = filePathPFP;
        this.country = country;
    }

    public void addPerformance(Performance performance){
        if(!this.performances.contains(performance)){
            this.performances.add(performance);
        } else {
            System.out.println("Performance allready in Artist's list!");
        }
    }

    public void removePerformance(Performance performance){
        if(this.performances.contains(performance)){
            this.performances.remove(performance);
        } else {
            System.out.println("Artist's performance list does not contain the given performance");
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

    public Enum<Genre> getGenre() {
        return genre;
    }

    public void setGenre(Enum<Genre> genre) {
        this.genre = genre;
    }

    public String getArtistType() {
        return artistType;
    }

    public void setArtistType(String artistType) {
        this.artistType = artistType;
    }

    public String getFilePathPFP() {
        return filePathPFP;
    }

    public void setFilePathPFP(String filePathPFP) {
        this.filePathPFP = filePathPFP;
    }

    public String getExtraInformation() {
        if (this.extraInformation == null) {
            return "No extra information available";
        }
        return extraInformation;
    }

    public void setExtraInformation(String extraInformation) {
        this.extraInformation = extraInformation;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}

