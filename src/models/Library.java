package models;

import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Library {

    private static ArrayList<Song> songs;

    private static Integer pkcounter;
    private static Library lib;

    private Library()    {
        // Loads the library in from the database
        pkcounter = 1;
        loadSongsFromDB();
    }

    public static  Library getLibrary()    {
        if (lib == null)    {
            lib = new Library();
        }

        return lib;
    }

    public void loadSongsFromDB()   {
        //PlayerDB db = PlayerDB.getDb();
        //Connection connection = db.connect();
        // Uses the database connection to query the database for the columns in 'Song' on the song DB table

        // Iterates over the result set, and turns each entry in to a song by getting the artist and then making the song object

        // replaces the 'songs' array list with the new array list

        // sort the array list alphabetically by title
        //db.close();
        songs = new ArrayList<Song>();
        addSongToLibrary("default-songs/11.mp3");
        addSongToLibrary("default-songs/21st_Century_Schizoid_Man.mp3");
        addSongToLibrary("default-songs/Let_It_Be.mp3");
        addSongToLibrary("default-songs/So_What.mp3");
    }

    public void addSongToLibrary(String path)  {
        try {
            // try to add the song the DB

            // first, get the file from the path
            Mp3File newSong = new Mp3File(path);
            // get the metadata from the song's tags
            if (newSong.hasId3v2Tag()) {
                ID3v2 id3v2Tag = newSong.getId3v2Tag();
                ArrayList<String> artistName = new ArrayList<>(Arrays.asList(id3v2Tag.getArtist().split(",")));
                String title = id3v2Tag.getTitle();
                String album = id3v2Tag.getAlbum();
                Long length = newSong.getLengthInSeconds();
                String genre = id3v2Tag.getGenreDescription();

                // create the artist if needed

                // create the song if needed

                // create a song object out of the metadata, and add the song to the array list of songs


                ArrayList<Artist> artists = new ArrayList<>();
                for (int i = 0; i < artistName.size(); i++) {
                    artists.add(new Artist(artistName.get(i)));
                }

                Song song = new Song (pkcounter, title, length, genre, album, artists, path);
                songs.add(song);
                pkcounter++;
                // sort the array list alphabetically by title
                Collections.sort(songs);
            } else {
                throw new InvalidDataException("The mp3 that was loaded is not tagged");
            }
        } catch (Exception e) {
            // if the song cannot be added, throw the corresponding exception
            System.out.println("Failed for some reason");
            System.out.println(e);
        }
    }

    public void deleteSongFromLibrary(Song song)  {
        // query the DB to delete the song by primary key

        // delete the song from the array list that has the songs stored

        for (int i = 0; i < songs.size(); i++) {
            if (songs.get(i).getPk() == song.getPk())   {
                songs.remove(i);
                i--;
            }
        }

        // if the song cannot be deleted, throw the corresponding exception
    }

    public ArrayList<Song> getSongs() {
        return songs;
    }
}
