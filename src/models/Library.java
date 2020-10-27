package models;

import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import utils.PlayerDB;

import javax.swing.text.StyledEditorKit;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Library {

    private static ArrayList<Song> songs;

    private static Library lib;

    private Library()    {
        // Loads the library in from the database
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
        Collections.sort(songs);
    }

    public void addSongToLibrary(String path)  {
        try {
            // try to add the song the DB
            PlayerDB db = PlayerDB.getDb();
            Connection connection = db.connect();

            // first, get the file from the path
            Mp3File newSong = new Mp3File(path);
            // get the metadata from the song's tags
            if (newSong.hasId3v2Tag()) {
                ID3v2 id3v2Tag = newSong.getId3v2Tag();
                Integer songPk;
                ArrayList<Integer> artistPks = new ArrayList<>();
                // create the artist if needed

                PreparedStatement checkArtistStatement = connection.prepareStatement("select id, name from artist where name in ?;");
                checkArtistStatement.setString(1, id3v2Tag.getArtist());
                ResultSet artistsFromDB = checkArtistStatement.executeQuery();

                while (artistsFromDB.next())   {
                    artistPks.add(artistsFromDB.getInt("id"));
                }

                for(String name : new ArrayList<>(Arrays.asList(id3v2Tag.getArtist().split(","))))  {
                    Boolean shouldBeAdded = true;
                    while (artistsFromDB.next())   {
                        if (artistsFromDB.getString("name") == name)    {
                            shouldBeAdded = false;
                        }
                    }

                    if (shouldBeAdded)  {
                        PreparedStatement newArtistInsertStatement = connection.prepareStatement("insert into artist(name) values (?);");
                        newArtistInsertStatement.setString(1, name);
                        ResultSet outcome = newArtistInsertStatement.executeQuery();
                        artistPks.add(outcome.getInt("id"));
                    }
                }

                // create the song if needed
                PreparedStatement newSongInsertStatement = connection.prepareStatement("insert into song(title, album, yr, comments, genre, pth) values (?,?,?,?,?,?);");
                newSongInsertStatement.setString(1,id3v2Tag.getTitle());
                newSongInsertStatement.setString(2,id3v2Tag.getAlbum());
                newSongInsertStatement.setString(3,id3v2Tag.getYear());
                newSongInsertStatement.setString(4,id3v2Tag.getComment());
                newSongInsertStatement.setString(5,id3v2Tag.getGenreDescription());
                newSongInsertStatement.setString(6,path);

                ResultSet outcome = newSongInsertStatement.executeQuery();
                songPk = outcome.getInt("id");

                // add to the song_artist DB table
                for (Integer artist : artistPks)    {
                    PreparedStatement newSongArtistInsertStatement = connection.prepareStatement("insert into song_artist(song_pk, artist_pk) values (?,?);");
                    newSongArtistInsertStatement.setInt(1, songPk);
                    newSongArtistInsertStatement.setInt(2, artist);
                    newSongArtistInsertStatement.executeQuery();
                }
                db.close();
                loadSongsFromDB();
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
