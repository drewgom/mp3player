package controllers;


import models.Library;
import models.Player;
import models.Song;
import models.SongCollection;
import views.MainPlayerView;
import views.PlayerView;

import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class CollectionController {
    private static Player player = Player.getPlayer();
    public static SongCollection lib = Library.getLibrary();
    public static DefaultTableModel tableModel;

    protected static void add(String path) {
        lib.addSongToCollection(path);
        System.out.println("Add button pressed.");
    }
    protected static void remove(Song song, Integer rowNumber) {
        lib.deleteSongFromCollection(song);
        System.out.println("Remove button pressed.");
    }

    public static Object[] getTableColumnNames()    {
        return new String[]{"Title", "Artist", "Genre", "Year", "Comment"};
    }

    public static DefaultTableModel getTableModelOfData()    {
        tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(getTableColumnNames());

        ArrayList<Song> songs = lib.getSongsInCollection();

        for (int i = 0; i < songs.size(); i++) {
            Object[] currentSongData = new Object[getTableColumnNames().length];
            Song currentSong = songs.get(i);
            currentSongData[0] = currentSong.getTitle();
            currentSongData[1] = currentSong.getArtists();
            currentSongData[2] = currentSong.getGenre();
            currentSongData[3] = currentSong.getYear();
            currentSongData[4] = currentSong.getComments();

            tableModel.addRow(currentSongData);
        }

        return tableModel;
    }
}
