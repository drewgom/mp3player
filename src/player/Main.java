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

        MainPlayerView view = new MainPlayerView(new Player(Library.getLibrary()));
        // PlaylistView view2 = new PlaylistView(new Player(Library.getLibrary()));
    	
    	view.setNowPlaying("test text");
        view.display();
        //view2.display();
    }
}