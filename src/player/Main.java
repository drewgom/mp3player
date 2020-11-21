package player;

import models.Player;
import views.MainPlayerView;

public class Main {

    public static void main(String[] args) {
        MainPlayerView view = new MainPlayerView(Player.getPlayer());
    	
    	view.setNowPlaying("test text");
        view.display();
    }
}