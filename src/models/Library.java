package models;

import java.util.ArrayList;

public class Library {
    private ArrayList<Song> songs;

    public Library()    {
        // Acquires the database connection

        // Loads the library in from the database
    }

    public void loadSongsFromDB()   {
        // Uses the database connection to query the database for the columns in 'Song' on the song DB table

        // Iterates over the result set, and turns each entry in to a song by getting the artist and then making the song object

        // replaces the 'songs' array list with the new array list

        // sort the array list alphabetically by title
    }

    public void addSongToLibrary(String path)  {
        // try to add the song the DB

        // first, get the file from the path

        // get the metadata from the song's tags

        // create the artist if needed

        // create the song if needed

        // create a song object out of the metadata, and add the song to the array list of songs

        // sort the array list alphabetically by title

        // if the song cannot be added, throw the corresponding exception
    }

    public void deleteSongFromLibrary(Song song)  {
        // query the DB to delete the song by primary key

        // delete the song from the array list that has the songs stored

        // if the song cannot be added, throw the corresponding exception
    }

    public ArrayList<Song> getLibrary() {
        return new ArrayList<Song>();
    }
}
