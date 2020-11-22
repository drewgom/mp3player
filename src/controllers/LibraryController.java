package controllers;


import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import models.Library;
import models.Player;
import models.Song;
import models.SongCollection;
import utils.DataAccessLayer;
import views.MainPlayerView;
import views.PlayerView;

import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class LibraryController {
    public static SongCollection lib = Library.getLibrary();
    public static DefaultTableModel tableModel;

    protected static void add(String path) {
        try {
            Mp3File newSongFile = new Mp3File(path);
            // get the metadata from the song's tags
            if (newSongFile.hasId3v2Tag()) {
                ID3v2 id3v2Tag = newSongFile.getId3v2Tag();
                Song song = new Song(id3v2Tag.getTitle(), id3v2Tag.getGenreDescription(), id3v2Tag.getAlbum(), id3v2Tag.getDate(), id3v2Tag.getComment(), id3v2Tag.getArtist(), path);

                lib.addSongToCollection(song);
            } else {
                throw new InvalidDataException("The mp3 that was loaded is not tagged");
            }
        } catch (Exception e)    {
            // if the song cannot be added, throw the corresponding exception
            System.out.println("Failed for some reason");
            System.out.println(e);
        }
        System.out.println("Add button pressed.");
    }
    protected static void remove(Song song, Integer rowNumber) {
        lib.deleteSongFromCollection(song);
        System.out.println("Remove button pressed.");
    }
}
