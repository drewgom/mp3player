package models;

import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import utils.PlayerDB;

import javax.swing.text.StyledEditorKit;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Library extends SongCollection{

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
        try {
            PlayerDB db = PlayerDB.getDb();
            Connection connection = db.connect();

            PreparedStatement newSongInsertStatement = connection.prepareStatement("select id, title, album, yr, comments, genre, pth, artist from song order by title desc");
            ResultSet outcome = newSongInsertStatement.executeQuery();

            songs = new ArrayList<Song>();

            while (outcome.next())  {
                songs.add( new Song(
                        outcome.getInt("id"),
                        outcome.getString("title"),
                        outcome.getString("genre"),
                        outcome.getString("album"),
                        outcome.getString("yr"),
                        outcome.getString("comments"),
                        outcome.getString("artist"),
                        outcome.getString("pth")
                    )
                );
            }
        } catch (Exception e)   {
            System.out.println("An error occurred");
            System.out.println(e.getMessage());
        } finally   {
            PlayerDB db = PlayerDB.getDb();
            db.close();
        }
    }

    public void addSongToCollection(String path)  {
        try {
            // try to add the song the DB
            PlayerDB db = PlayerDB.getDb();
            Connection connection = db.connect();

            // first, get the file from the path
            Mp3File newSong = new Mp3File(path);
            // get the metadata from the song's tags
            if (newSong.hasId3v2Tag()) {
                ID3v2 id3v2Tag = newSong.getId3v2Tag();

                // create the song if needed
                PreparedStatement newSongInsertStatement = connection.prepareStatement("insert into song(title, album, yr, comments, genre, pth, artist) values (?,?,?,?,?,?,?)");
                newSongInsertStatement.setString(1,id3v2Tag.getTitle());
                newSongInsertStatement.setString(2,id3v2Tag.getAlbum());
                newSongInsertStatement.setString(3,id3v2Tag.getDate());
                newSongInsertStatement.setString(4,id3v2Tag.getComment());
                newSongInsertStatement.setString(5,id3v2Tag.getGenreDescription());
                newSongInsertStatement.setString(6,path);
                newSongInsertStatement.setString(7,id3v2Tag.getArtist());
                newSongInsertStatement.executeUpdate();
            } else {
                throw new InvalidDataException("The mp3 that was loaded is not tagged");
            }
        } catch(SQLIntegrityConstraintViolationException e)   {
            System.out.println("Song already added");
        } catch (Exception e) {
            // if the song cannot be added, throw the corresponding exception
            System.out.println("Failed for some reason");
            System.out.println(e);
        } finally   {
            PlayerDB db = PlayerDB.getDb();
            db.close();

            loadSongsFromDB();
        }
    }

    public void deleteSongFromCollection(Song song)  {
        try {
            PlayerDB db = PlayerDB.getDb();
            Connection connection = db.connect();
            PreparedStatement deleteSongStatement = connection.prepareStatement("delete from song where id in (?)");
            deleteSongStatement.setInt(1, song.getPk());
            deleteSongStatement.executeUpdate();

        } catch (Exception e) {
            System.out.println("Failed for some reason");
            System.out.println(e);
        } finally   {
            PlayerDB db = PlayerDB.getDb();
            db.close();

            loadSongsFromDB();
        }
    }

    public ArrayList<Song> getSongsInCollection() {
        return songs;
    }
}
