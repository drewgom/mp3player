package player;

import models.Library;
import models.Player;
import views.MainPlayerView;

public class Main {

    public static void main(String[] args) {
        MainPlayerView view = new MainPlayerView(new Player(Library.getLibrary()));
    	
    	view.setNowPlaying("test text");
        view.display();
    }
}