package player;

import controllers.PlayerController;
import javazoom.jlgui.basicplayer.BasicPlayer;
import models.Library;
import models.Player;
import views.PlayerView;

public class Main {

    public static void main(String[] args) {
    	
    	
    	
    	PlayerView view = new PlayerView();
    	//change to instanciate?
    	PlayerController.pv = view;
    	
    	
        //Player player = new Player();
        //Library lib = player.getLibrary();

        //lib.addSongToLibrary("default-songs\\11.mp3");
        //lib.addSongToLibrary("default-songs\\21st_Century_Schizoid_Man.mp3");
        //lib.addSongToLibrary("default-songs\\Let_It_Be.mp3");
        //lib.addSongToLibrary("default-songs\\So_What.mp3");

        //System.out.println(lib.getSongs());

        //player.play(lib.getSongs().get(0));
        /**
        for (int i = 0; i < 750000; i++)   {
            System.out.println("idle");
        }

        player.pause();

        for (int i = 0; i < 750000; i++)   {
            System.out.println("idle");
        }

        player.play();

        for (int i = 0; i < 750000; i++)   {
            System.out.println("idle");
        }

        player.restart();

        for (int i = 0; i < 750000; i++)   {
            System.out.println("idle");
        }

        player.next();

        for (int i = 0; i < 750000; i++)   {
            System.out.println("idle");
        }

        player.next();

        for (int i = 0; i < 750000; i++)   {
            System.out.println("idle");
        }

        player.previous();

        for (int i = 0; i < 750000; i++)   {
            System.out.println("idle");
        }
         **/
    }
}
