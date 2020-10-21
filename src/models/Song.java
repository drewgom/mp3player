package models;

import java.util.ArrayList;

public class Song implements Comparable<Song>{
    private Integer pk;
    private String title;
    private Long length;
    private String genre;
    private String album;
    private String path;
    private ArrayList<Artist> artists;

    public Song(Integer pk, String title, Long length, String genre, String album, ArrayList<Artist> artists, String path) {
        this.pk = pk;
        this.title = title;
        this.length = length;
        this.genre = genre;
        this.artists = artists;
        this.album = album;
        this.path = path;
    }

    public String getGenre() {
        return genre;
    }

    public Long getLength() {
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

    public String getAlbum() {
        return album;
    }

    public String getPath() {
        return path;
    }

    @Override
    public int compareTo(Song s) {
        return this.title.compareTo(s.title);
    }

    @Override
    public String toString() {
        return "Title: " + title + " Length: " + length + " Genre: " + genre + " Artists: " + artists + " Album: " + album;
    }
}
