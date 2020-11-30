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

        PlaylistView view = new PlaylistView(new Player(Library.getLibrary()));
    	
    	// view.setNowPlaying("test text");
        view.display();
    }
}