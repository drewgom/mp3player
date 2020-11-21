package player;

import models.Library;
import models.Player;
import views.MainPlayerView;
import views.PlaylistView;

public class Main {

    public static void main(String[] args) {
        MainPlayerView view = new MainPlayerView(new Player(Library.getLibrary()));
    	
    	view.setNowPlaying("test text");
        view.display();
        
        MainPlayerView view2 = new MainPlayerView(new Player(Library.getLibrary()));
    	
        view2.setNowPlaying("test text");
        view2.display();
    }
}