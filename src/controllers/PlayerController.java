package controllers;

import models.Library;
import models.Player;
import models.Song;
import models.State;
import views.MainPlayerView;
import views.PlayerView;

import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PlayerController {
	private static PlayerView playerView;
	private static Player player;

	public PlayerController(PlayerView playerView, Player player)	{
		this.playerView = playerView;
		this.player = player;
	}

	public static void play() {
		System.out.println("Play button pressed.");
		playerView.setPause();

		Integer row = MainPlayerView.getRow();
		Song selectedSong = player.getCollection().getSongsInCollection().get(row);

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

	public static void pause() {
		System.out.println("Pause button pressed.");
		playerView.setPlay();
		player.pause();
	}

	public static void stop() {
		System.out.println("Stop button pressed.");
		player.stop();
	}
	public static void skipForward() {
		System.out.println("Skip forward button pressed.");
		player.next();
		Integer row = PlayerController.getIndexOfCurrentSong();
		playerView.refreshSelected(row);
		System.out.println("New row is " + row);
	}
	public static void skipBack() {
		System.out.println("Skip back button pressed.");
		player.previous();
		Integer row = PlayerController.getIndexOfCurrentSong();
		playerView.refreshSelected(row);
		System.out.println("New row is " + row);
	}
	public static void addViaPopup() {
		System.out.println("Add button pressed.");
		
		File newSong = playerView.addPopup();
		if(newSong != null) {
			System.out.println("Selected file: "+newSong.getAbsolutePath());
			CollectionController.add(newSong.getAbsolutePath());
			//Can't write specifics since how you set things up hasn't been pushed to the git.
		}
		else {
			System.out.println("Didn't select a file.");
		}
	}

	public static void addViaPath(String path)	{
		CollectionController.add(path);
	}
	public static void remove() {
		Integer row = MainPlayerView.getRow();
		Song selectedSong = player.getCollection().getSongsInCollection().get(row);

		CollectionController.remove(selectedSong,row);
		playerView.repaint();
		System.out.println("Remove button pressed.");
	}
	
	public static void playSelected() {
		int index = playerView.getSelectedIndex();
		System.out.println("Play selected song: Index "+Integer.toString(index)+".");
		player.play(getSongFromIndex(index));
	}

	public static void removeSelected() {
		int index = playerView.getSelectedIndex();
		System.out.println("Remove selected song: Index "+Integer.toString(index)+".");
		CollectionController.remove(getSongFromIndex(index),index);
	}

	public static Integer getIndexOfCurrentSong()	{
		ArrayList<Song> songs = Library.getLibrary().getSongsInCollection();
		Song currSong = player.getCurrentSong();
		Integer row = null;
		for (int i = 0; i < songs.size(); i++)	{
			if (currSong.getPk() == songs.get(i).getPk())	{
				row = i;
			}
		}
		return row;
	}

	public static Song getSongFromIndex(Integer index)	{
		ArrayList<Song> songs = Library.getLibrary().getSongsInCollection();
		return songs.get(index);
	}
}
