package player;

import controllers.PlayerController;
import javazoom.jlgui.basicplayer.BasicPlayer;
import models.Library;
import models.Player;
import views.PlayerView;

public class Main {

    public static void main(String[] args) {
        PlayerView view = PlayerView.getPlayerView();
        view.display();
    }
}
