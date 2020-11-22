package controllers;

import models.CollectionManager;
import views.PlayerView;

import java.util.ArrayList;

public class CollectionManagerController {
    CollectionManager collectionManager = CollectionManager.getCollectionManager();
    static ArrayList<PlayerView> openWindows;

    public static void attachWindow(PlayerView pv)  {
        openWindows.add(pv);
    }

    public static void detachWindow(PlayerView pv)  {
        openWindows.remove(pv);
    }

    public void refreshWindows()    {
        for(PlayerView pv : openWindows)    {
            pv.repaint();
        }
    }

    // PLACEHOLDER
    // This will be the function that gets called when we try to make a new playlist
    public void createPlaylist()    {
        // collectionManager.createPlaylist();
        refreshWindows();
    }

    // PLACEHOLDER
    // This will be the function that gets called when we try to delete a playlist
    public void deletePlaylist()    {
        // collectionManager.deletePlaylist();
        refreshWindows();
    }

    // PLACEHOLDER
    public void addSongToPlaylist() {
        refreshWindows();
    }
}
