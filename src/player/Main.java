package player;

import views.MainPlayerView;

public class Main {

    public static void main(String[] args) {
        MainPlayerView view = MainPlayerView.getPlayerView();
    	
    	view.setNowPlaying("test text");
        view.display();
    }
}
