package utils;

import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import models.Playlist;
import models.Song;

import java.sql.*;
import java.util.ArrayList;

public class DataAccessLayer {
    static DataAccessLayer dal;
    private DataAccessLayer()  { }

    public static DataAccessLayer getDAL() {
        if (dal == null) {
            dal = new DataAccessLayer();
        }

        return dal;
    }


    public ArrayList<Song> getAllSongsFromSongTable()   {
        ArrayList<Song> songs = new ArrayList<Song>();
        try {
            PlayerDB db = PlayerDB.getDb();
            Connection connection = db.connect();

            PreparedStatement newSongInsertStatement = connection.prepareStatement("select id, title, album, yr, comments, genre, pth, artist from song order by title desc");
            ResultSet outcome = newSongInsertStatement.executeQuery();

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
            System.out.println("An error occurred in getAllSongsFromSongTable");
            System.out.println(e.getMessage());
        } finally   {
            PlayerDB db = PlayerDB.getDb();
            db.close();
        }
        return songs;
    }

    public void addSongToSongTable(String title, String album, String yr, String comments, String genre, String pth, String artist)  {
        try {
            // try to add the song the DB
            PlayerDB db = PlayerDB.getDb();
            Connection connection = db.connect();

            PreparedStatement newSongInsertStatement = connection.prepareStatement("insert into song(title, album, yr, comments, genre, pth, artist) values (?,?,?,?,?,?,?)");
            newSongInsertStatement.setString(1,title);
            newSongInsertStatement.setString(2,album);
            newSongInsertStatement.setString(3,yr);
            newSongInsertStatement.setString(4,comments);
            newSongInsertStatement.setString(5,genre);
            newSongInsertStatement.setString(6,pth);
            newSongInsertStatement.setString(7,artist);
            newSongInsertStatement.executeUpdate();
        } catch(SQLIntegrityConstraintViolationException e)   {
            System.out.println("Contstraint violation: Song likely already added");
            System.out.println(e);
        } catch (Exception e) {
            // if the song cannot be added, throw the corresponding exception
            System.out.println("Failed for some reason");
            System.out.println(e);
        } finally   {
            PlayerDB db = PlayerDB.getDb();
            db.close();
        }
    }

    public void deleteSongFromSongTable(Integer pk)  {
        try {
            PlayerDB db = PlayerDB.getDb();
            Connection connection = db.connect();
            PreparedStatement deleteSongStatement = connection.prepareStatement("delete from song where id in (?)");
            deleteSongStatement.setInt(1, pk);
            deleteSongStatement.executeUpdate();

        } catch (Exception e) {
            System.out.println("Failed for some reason");
            System.out.println(e);
        } finally   {
            PlayerDB db = PlayerDB.getDb();
            db.close();
        }
    }

    public ArrayList<Playlist> getAllPlaylistsFromTable()  {
        ArrayList<Playlist> playlists = new ArrayList<Playlist>();
        try {
            PlayerDB db = PlayerDB.getDb();
            Connection connection = db.connect();

            PreparedStatement newSongInsertStatement = connection.prepareStatement("select nme, id from playlist order by nme desc");
            ResultSet outcome = newSongInsertStatement.executeQuery();

            while (outcome.next())  {
                playlists.add( new Playlist(
                                outcome.getString("nme"),
                                outcome.getInt("id")
                        )
                );
            }
        } catch (Exception e)   {
            System.out.println("An error occurred in getAllPlaylistsFromTable");
            System.out.println(e.getMessage());
        } finally   {
            PlayerDB db = PlayerDB.getDb();
            db.close();
        }
        return playlists;
    }


    public ArrayList<Song> getSongsForAPlaylist(Integer pk)    {
        ArrayList<Song> songs = new ArrayList<Song>();
        try {
            PlayerDB db = PlayerDB.getDb();
            Connection connection = db.connect();

            PreparedStatement newSongInsertStatement = connection.prepareStatement("select id, title, album, yr, comments, genre, pth, artist from song where id in (select song_id from playlist_song where playlist_id in (?)) order by title desc");
            newSongInsertStatement.setInt(1,pk);
            ResultSet outcome = newSongInsertStatement.executeQuery();

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
            System.out.println("An error occurred in getSongsForAPlaylist");
            System.out.println(e.getMessage());
        } finally   {
            PlayerDB db = PlayerDB.getDb();
            db.close();
        }
        return songs;
    }

    public void addSongToPlaylist(Integer song_pk, Integer playlist_pk)   {
        try {
            // try to add the song the DB
            PlayerDB db = PlayerDB.getDb();
            Connection connection = db.connect();

            PreparedStatement newSongInsertStatement = connection.prepareStatement("insert into playlist_song(song_id, playlist_id) values (?,?)");
            newSongInsertStatement.setInt(1,song_pk);
            newSongInsertStatement.setInt(2,playlist_pk);
            newSongInsertStatement.executeUpdate();
        } catch(SQLIntegrityConstraintViolationException e)   {
            System.out.println("Song already added for playlist");
        } catch (Exception e) {
            // if the song cannot be added, throw the corresponding exception
            System.out.println("Failed for some reason");
            System.out.println(e);
        } finally   {
            PlayerDB db = PlayerDB.getDb();
            db.close();
        }
    }

    public void deleteSongFromPlaylist(Integer song_pk, Integer playlist_pk)    {
        try {
            PlayerDB db = PlayerDB.getDb();
            Connection connection = db.connect();
            PreparedStatement deleteSongStatement = connection.prepareStatement("delete from playlist_song where song_id in (?) and playlist_id in (?)");
            deleteSongStatement.setInt(1, song_pk);
            deleteSongStatement.setInt(2, playlist_pk);
            deleteSongStatement.executeUpdate();

        } catch (Exception e) {
            System.out.println("Failed for some reason");
            System.out.println(e);
        } finally   {
            PlayerDB db = PlayerDB.getDb();
            db.close();
        }
    }

    public void createPlaylist(String name)    {
        try {
            // try to add the song the DB
            PlayerDB db = PlayerDB.getDb();
            Connection connection = db.connect();

            PreparedStatement newPlaylistInsertStatement = connection.prepareStatement("insert into playlist(nme) values (?)");
            newPlaylistInsertStatement.setString(1, name);
            newPlaylistInsertStatement.executeUpdate();
        } catch(SQLIntegrityConstraintViolationException e)   {
            System.out.println("Contstraint violation: Playlist by that name likely already exists");
            System.out.println(e);
        } catch (Exception e) {
            System.out.println("Failed for some reason");
            System.out.println(e);
        } finally   {
            PlayerDB db = PlayerDB.getDb();
            db.close();
        }

    }

    public void deletePlaylist(Integer pk)    {
        try {
            PlayerDB db = PlayerDB.getDb();
            Connection connection = db.connect();

            PreparedStatement deleteSongStatement = connection.prepareStatement("delete from playlist_song where playlist_id in (?)");
            deleteSongStatement.setInt(1, pk);
            deleteSongStatement.executeUpdate();

            PreparedStatement deletePlaylistStatement = connection.prepareStatement("delete from playlist where id in (?)");
            deletePlaylistStatement.setInt(1, pk);
            deletePlaylistStatement.executeUpdate();

        } catch (Exception e) {
            System.out.println("Failed for some reason");
            System.out.println(e);
        } finally   {
            PlayerDB db = PlayerDB.getDb();
            db.close();
        }
    }
}
