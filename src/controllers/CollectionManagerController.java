package controllers;

import models.CollectionManager;
import models.Playlist;
import models.Song;
import models.SongCollection;
import views.MainPlayerView;
import views.PlayerView;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class CollectionManagerController {
    CollectionManager collectionManager = CollectionManager.getCollectionManager();
    static ArrayList<PlayerView> openWindows = new ArrayList<PlayerView>();

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

    public void createPlaylist()    {
        String response = JOptionPane.showInputDialog("Please enter the new Playlist name:");
        collectionManager.createPlaylist(response);
        refreshWindows();
    }

    // PLACEHOLDER
    // This will be the function that gets called when we try to delete a playlist
    public void deletePlaylist()    {
        // collectionManager.deletePlaylist();
        refreshWindows();
    }

    // PLACEHOLDER
    public void addSongToPlaylist(Song song, String playlistName) {
        // get the playlist by name
        Playlist playlist = collectionManager.getPlaylistByName(playlistName);
        // get the song object that was selected
        playlist.addSongToCollection(song);
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

    public void getPlaylistContexts(JMenu contextAddPlaylist, ActionListener listener)  {
        ArrayList<Playlist> playlists = collectionManager.getAllPlaylists();
        for (Playlist playlist : playlists) {
            JMenuItem newItem = new JMenuItem(playlist.getName());
            newItem.addActionListener(listener);
            newItem.setActionCommand("addToPlaylist");
            contextAddPlaylist.add(newItem);
        }
    }
}
