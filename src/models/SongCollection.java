package models;

import java.util.ArrayList;

public abstract class SongCollection {
    public abstract void deleteSongFromCollection(Song song);
    public abstract ArrayList<Song> getSongsInCollection();
    public abstract void addSongToCollection(Song song);
}