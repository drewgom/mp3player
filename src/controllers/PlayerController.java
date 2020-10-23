package controllers;

import models.Library;
import models.Player;
import models.Song;
import models.State;
import views.PlayerView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import views.PlayerView;

public class PlayerController {
	private static PlayerView playerView = PlayerView.getPlayerView();
	private static Player player = Player.getPlayer();

	public static class buttonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			switch (e.getActionCommand()) {
				case "play":
					play();
					break;
				case "pause":
					pause();
					break;
				case "stop":
					stop();
					break;
				case "skipForward":
					skipForward();
					break;
				case "skipBack":
					skipBack();
					break;
				case "add":
					add();
					break;
				case "remove":
					remove();
					break;
			}
		}
	};
	
	protected static void play() {
		System.out.println("Play button pressed.");
		playerView.setPause();

		Integer row = PlayerView.getRow();
		Song selectedSong = player.getLibrary().getSongs().get(row);

		if (player.getState() == State.IDLE) {
			System.out.println("Play button pressed and is idle");
			player.play(selectedSong);
		} else if (player.getCurrentSong() != selectedSong)	{
			player.play(selectedSong);
		} else	{
			System.out.println("Play button pressed and is not idle");
			player.play();
		}
	}

	protected static void pause() {
		System.out.println("Pause button pressed.");
		playerView.setPlay();
		player.pause();
	}

	protected static void stop() {
		System.out.println("Stop button pressed.");
		player.stop();
	}
	protected static void skipForward() {
		System.out.println("Skip forward button pressed.");
		player.next();
	}
	protected static void skipBack() {
		System.out.println("Skip back button pressed.");
		player.previous();
	}
	protected static void add() {
		System.out.println("Add button pressed.");
	}
	protected static void remove() {
		Integer row = PlayerView.getRow();
		Song selectedSong = player.getLibrary().getSongs().get(row);

		LibraryController.remove(selectedSong,row);
		playerView.repaint();
		System.out.println("Remove button pressed.");
	}
}
