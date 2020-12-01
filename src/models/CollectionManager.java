package models;

import utils.DataAccessLayer;
import utils.PlayerDB;
import views.PlayerView;

import java.util.ArrayList;

public class CollectionManager {
    ArrayList<Playlist> playlists;
    private static CollectionManager collectionManager;

    private CollectionManager()  {
        loadPlaylistsFromDB();
    }

    public static CollectionManager getCollectionManager() {
        if (collectionManager == null) {
            collectionManager = new CollectionManager();
        }

        return collectionManager;
    }

    public void loadPlaylistsFromDB()  {
        DataAccessLayer dal = DataAccessLayer.getDAL();
        playlists = dal.getAllPlaylistsFromTable();
    }

    public void createPlaylist(String name)    {
        DataAccessLayer dal = DataAccessLayer.getDAL();
        dal.createPlaylist(name);
        loadPlaylistsFromDB();
    }

    public void deletePlaylist(Integer pk)    {
        DataAccessLayer dal = DataAccessLayer.getDAL();
        dal.deletePlaylist(pk);
        loadPlaylistsFromDB();
    }

    public ArrayList<Playlist> getAllPlaylists()    { return playlists; }
}