package models;

import java.util.ArrayList;

public class Song {
    private Integer pk;
    private String title;
    private Float length;
    private String genre;
    private Integer year;
    private ArrayList<Artist> artists;

    public Song(Integer pk, String title, Float length, String genre, Integer year, ArrayList<Artist> artists) {
        this.pk = pk;
        this.title = title;
        this.length = length;
        this.genre = genre;
        this.year = year;
        this.artists = artists;
    }

    public String getGenre() {
        return genre;
    }

    public Float getLength() {
        return length;
    }

    public String getTitle() {
        return title;
    }

    public Integer getPk() {
        return pk;
    }

    public ArrayList<Artist> getArtists() {
        return artists;
    }

    public Integer getYear() {
        return year;
    }
}
