package controllers;

import models.Artist;
import models.Library;
import models.Player;
import models.Song;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class LibraryController {
    public static Library lib = Library.getLibrary();

    /*public static class buttonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            switch (e.getActionCommand()) {
                case "add":
                    add();
                    break;
                case "remove":
                    remove();
                    break;
            }
        }
    }

    protected static void add(String path) {
        lib.addSongToLibrary(path);
        System.out.println("Add button pressed.");
    }
    protected static void remove(Song song) {
        lib.deleteSongFromLibrary(song);
        System.out.println("Remove button pressed.");
    }*/

    public Object[][] getTableObjects()    {
        Integer numDataFields = getTableColumnNames().length;
        Integer songCount = lib.getSongs().size();
        Object[][] data = new Object[songCount][numDataFields];
        ArrayList<Song> songs = lib.getSongs();

        for (int i = 0; i < songCount; i++) {
            Object[] currentSongData = new Object[numDataFields];
            Song currentSong = songs.get(i);
            String artistList = "";
            ArrayList<Artist> artistObjects = currentSong.getArtists();
            for (int j = 0; j < artistObjects.size(); j++) {
                artistList = artistList + artistObjects.get(j).getName();
                if (j != artistObjects.size()-1)    {
                    artistList = artistList + ", ";
                }
            }
            currentSongData[0] = currentSong.getTitle();
            currentSongData[1] = currentSong.getLength();
            currentSongData[2] = currentSong.getGenre();
            currentSongData[3] = currentSong.getAlbum();
            currentSongData[4] = artistList;

            data[i] = currentSongData;
        }

        return data;
    }

    public String[] getTableColumnNames()    {
        return new String[]{"Title", "Length", "Genre", "Album", "Artist"};
    }
}
