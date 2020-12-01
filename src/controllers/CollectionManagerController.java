package controllers;

import models.CollectionManager;
import models.Playlist;
import views.PlayerView;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
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

    public DefaultTreeModel getTreeOfPlaylists()   {
        DefaultMutableTreeNode rt = new DefaultMutableTreeNode("r");
        DefaultMutableTreeNode libraryNode = new DefaultMutableTreeNode("Library");
        DefaultMutableTreeNode playlistNode = new DefaultMutableTreeNode("Playlists");
        ArrayList<Playlist> playlists = collectionManager.getAllPlaylists();
        for (Playlist playlist : playlists) {
            playlistNode.add(new DefaultMutableTreeNode(playlist.getName()));
        }

        rt.add(libraryNode);
        rt.add(playlistNode);
        DefaultTreeModel treeModel = new DefaultTreeModel(rt);
        return treeModel;
    }
}
