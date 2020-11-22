package models;

import utils.DataAccessLayer;

import java.util.ArrayList;

public class Playlist extends SongCollection{
    String name;
    Integer pk;
    ArrayList<Song> songs;

    public Playlist(String name, Integer pk)    {
        this.name = name;
        this.pk = pk;
    }

    public void loadSongsFromDB()   {
        DataAccessLayer dal = DataAccessLayer.getDAL();
        songs = dal.getSongsForAPlaylist(pk);
    }

    public void addSongToCollection(Song song)  {
        DataAccessLayer dal = DataAccessLayer.getDAL();
        if (song.getPk() != 0) {
            dal.addSongToPlaylist(song.getPk(), pk);
        } else  {
            System.out.println("Song is not currently in the DB");
        }
        loadSongsFromDB();
    }

    public void deleteSongFromCollection(Song song) {
        DataAccessLayer dal = DataAccessLayer.getDAL();
        dal.deleteSongFromPlaylist(song.getPk(), pk);
        loadSongsFromDB();
    }

    public ArrayList<Song> getSongsInCollection() {
        return songs;
    }

    public String getName() {
        return name;
    }

    public Integer getPk() {
        return pk;
    }
}
