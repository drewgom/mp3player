package models;

import java.util.ArrayList;

public class Song implements Comparable<Song>{
    private Integer pk;
    private String title;
    private String genre;
    private String artists;
    private String album;
    private String year;
    private String comments;
    private String path;

    public Song(Integer pk,
                String title,
                String genre,
                String album,
                String year,
                String comments,
                String artists,
                String path
    ) {
         this.pk = pk;
         this.title = title;
         this.genre = genre;
         this.album = album;
         this.year = year;
         this.comments = comments;
         this.artists = artists;
         this.path = path;
    }

    public String getGenre() {
        return genre;
    }

    public String getTitle() {
        return title;
    }

    public Integer getPk() {
        return pk;
    }

    public String getArtists() {
        return artists;
    }

    public String getAlbum() {
        return album;
    }

    public String getYear() {
        return year;
    }

    public String getComments() {
        return comments;
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
        return "Title: " + title + " Genre: " + genre + " Artists: " + artists + " Album: " + album + " Year: " + year + " Comments: " + comments;
    }
}
