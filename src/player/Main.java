package player;

import models.Library;
import models.Player;
import models.Playlist;
import models.Song;
import utils.DataAccessLayer;
import views.MainPlayerView;
import views.PlaylistView;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        DataAccessLayer dal = DataAccessLayer.getDAL();

        // get the songs from the library
        ArrayList<Song> songs = dal.getAllSongsFromSongTable();

        // create a playlist
        dal.createPlaylist("testlist");
        ArrayList<Playlist> playlists = dal.getAllPlaylistsFromTable();
        Playlist playlist = playlists.get(0);

        // add three songs to the playlist
        playlist.addSongToCollection(songs.get(0));
        playlist.addSongToCollection(songs.get(1));
        playlist.addSongToCollection(songs.get(2));


        MainPlayerView view = new MainPlayerView(new Player(playlist));
    	
    	view.setNowPlaying("test text");
        view.display();
    }
}