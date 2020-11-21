package controllers;

import models.Library;
import models.Player;
import models.Song;
import models.State;
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
		Integer row = PlayerController.getIndexOfCurrentSong();
		playerView.refreshSelected(row);
		System.out.println("New row is " + row);
	}
	protected static void skipBack() {
		System.out.println("Skip back button pressed.");
		player.previous();
		Integer row = PlayerController.getIndexOfCurrentSong();
		playerView.refreshSelected(row);
		System.out.println("New row is " + row);
	}
	protected static void add() {
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
	protected static void remove() {
		Integer row = PlayerView.getRow();
		Song selectedSong = player.getCollection().getSongsInCollection().get(row);

		CollectionController.remove(selectedSong,row);
		playerView.repaint();
		System.out.println("Remove button pressed.");
	}
	
	public static class contextListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			switch(e.getActionCommand()) {
				case "play":
					playSelected();
					break;
				case "add":
					add();
					break;
				case "remove":
					removeSelected();
					break;
			}
		}
	}
	
	protected static void playSelected() {
		int index = playerView.getSelectedIndex();
		System.out.println("Play selected song: Index "+Integer.toString(index)+".");
		player.play(getSongFromIndex(index));
	}

	protected static void removeSelected() {
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
	
	public static class LibraryDrop extends DropTarget {
		public void drop (DropTargetDropEvent evt) {
			try {
				evt.acceptDrop(DnDConstants.ACTION_COPY);
				
				//TODO Figure out what type this returns
				List result = (List) evt.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
				
				for(Object o : result) {
					System.out.println("Dropped file: "+o.toString());
					CollectionController.add(o.toString());
					//Can't write specifics since how you set things up hasn't been pushed to the git.
				}
			}
			catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
}
