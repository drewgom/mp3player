package models;

import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import utils.DataAccessLayer;
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
        DataAccessLayer dal = DataAccessLayer.getDAL();
        songs = dal.getAllSongsFromSongTable();
    }

    public void addSongToCollection(Song song)  {
        DataAccessLayer dal = DataAccessLayer.getDAL();
        dal.addSongToSongTable(song.getTitle(),song.getAlbum(),song.getYear(),song.getComments(), song.getGenre(), song.getPath(), song.getArtists());
        loadSongsFromDB();
    }

    public void deleteSongFromCollection(Song song) {
        DataAccessLayer dal = DataAccessLayer.getDAL();
        dal.deleteSongFromSongTable(song.getPk());
        loadSongsFromDB();
    }

    public ArrayList<Song> getSongsInCollection() {
        return songs;
    }
}
