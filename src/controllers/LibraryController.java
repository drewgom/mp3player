package controllers;


import models.Library;
import models.Player;
import models.Song;
import views.PlayerView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class LibraryController {
    private static Player player = Player.getPlayer();
    public static Library lib = Library.getLibrary();
    public static DefaultTableModel tableModel;

    protected static void add(String path) {
        lib.addSongToLibrary(path);
        System.out.println("Add button pressed.");
    }
    protected static void remove(Song song, Integer rowNumber) {
        lib.deleteSongFromLibrary(song);
        tableModel.removeRow(rowNumber);
        System.out.println("Remove button pressed.");
    }

    public Object[] getTableColumnNames()    {
        return new String[]{"Title", "Artist", "Genre", "Year", "Comment"};
    }

    public DefaultTableModel getTableModelOfData()    {
        tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(getTableColumnNames());

        ArrayList<Song> songs = lib.getSongs();

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
