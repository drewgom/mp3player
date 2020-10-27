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
    	
    	view.setNowPlaying("test text");
    	
    	String[][] data = new String[][] {new String[]{"Song1","Data1","Data2"},new String[]{"Song2","Data1","Data2"},new String[]{"Song1","Data1","Data2"}};
    	String[] columnNames = new String[] {"Song Title", "Data One", "Data Two"};
    	
    	view.setTable(data, columnNames);
    }
}
