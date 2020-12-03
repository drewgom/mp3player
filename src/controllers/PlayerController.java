package controllers;

import models.*;
import views.PlayerView;

import javax.swing.table.DefaultTableModel;
import java.io.File;
import java.util.ArrayList;

public class PlayerController {
	private PlayerView playerView;
	private Player player;

	public PlayerController(PlayerView playerView, Player player)	{
		this.playerView = playerView;
		this.player = player;
	}

	public void play() {
		System.out.println("Play button pressed.");
		playerView.setPause();

		Integer row = playerView.getTableRow();
		System.out.println("Row is" + row);

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

	public void pause() {
		System.out.println("Pause button pressed.");
		playerView.setPlay();
		player.pause();
	}

	public void stop() {
		System.out.println("Stop button pressed.");
		player.stop();
	}

	public void skipForward() {
		System.out.println("Skip forward button pressed.");
		player.next();
		Integer row = getIndexOfCurrentSong();
		playerView.refreshSelected(row);
		System.out.println("New row is " + row);
	}

	public void skipBack() {
		System.out.println("Skip back button pressed.");
		player.previous();
		Integer row = getIndexOfCurrentSong();
		playerView.refreshSelected(row);
		System.out.println("New row is " + row);
	}
	public void addViaPopup() {
		System.out.println("Add button pressed.");
		
		File newSong = playerView.addPopup();
		if(newSong != null) {
			System.out.println("Selected file: "+newSong.getAbsolutePath());
			LibraryController.add(newSong.getAbsolutePath());
			//Can't write specifics since how you set things up hasn't been pushed to the git.
			playerView.repaint();
		}
		else {
			System.out.println("Didn't select a file.");
		}
	}

	public void addViaPath(String path)	{
		LibraryController.add(path);
		System.out.println("About to repaint");
		playerView.repaint();
		System.out.println("Repainted");
	}
	public void remove() {
		Integer row = playerView.getTableRow();
		Song selectedSong = player.getCollection().getSongsInCollection().get(row);

		player.getCollection().deleteSongFromCollection(selectedSong);
		playerView.repaint();
		System.out.println("Remove button pressed.");
	}
	
	public void playSelected() {
		int index = playerView.getSelectedIndex();
		System.out.println("Play selected song: Index "+Integer.toString(index)+".");
		player.play(getSongFromIndex(index));
	}

	public void removeSelected() {
		int index = playerView.getSelectedIndex();
		Song selectedSong = player.getCollection().getSongsInCollection().get(index);
		System.out.println("Remove selected song: Index "+Integer.toString(index)+".");
		player.getCollection().deleteSongFromCollection(selectedSong);
	}

	public Integer getIndexOfCurrentSong()	{
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

	public Song getSongFromIndex(Integer index)	{
		ArrayList<Song> songs = player.getCollection().getSongsInCollection();
		return songs.get(index);
	}

	public void updateVolume(Double gain)	{
		player.updateVolume(gain);
	}

	public Object[] getTableColumnNames()    {
		return new String[]{"Title", "Artist", "Genre", "Year", "Comment"};
	}

	public DefaultTableModel getTableModelOfData()    {
		DefaultTableModel tableModel = new DefaultTableModel();
		tableModel.setColumnIdentifiers(getTableColumnNames());
                
		ArrayList<Song> songs = player.getCollection().getSongsInCollection();
                
		for (int i = 0; i < songs.size(); i++) {
			Object[] currentSongData = new Object[getTableColumnNames().length];
			Song currentSong = songs.get(i);
			currentSongData[0] = currentSong.getTitle();
			currentSongData[1] = currentSong.getArtists();
			currentSongData[2] = currentSong.getGenre();
			currentSongData[3] = currentSong.getYear();
			currentSongData[4] = currentSong.getComments();

			tableModel.addRow(currentSongData);
		}

		return tableModel;
	}

	public void swtichCollectionForPlayer(String collectionName) {
		if (collectionName == "Library")	{
			player.setCollection(Library.getLibrary());
		} else {
			ArrayList<Playlist> playlists = CollectionManager.getCollectionManager().getAllPlaylists();
			for (int i = 0; i < playlists.size(); i++) {
				System.out.println(playlists.get(i).getName());
			}

			for (int i = 0; i < playlists.size(); i++) {
				if(playlists.get(i).getName() == collectionName)	{
					player.setCollection(playlists.get(i));
				}
			}
		}
		playerView.repaint();
	}

	public void droppedOnToTable(String path) {
		System.out.println("path is" + path);
		addViaPath(path);
		System.out.println(player.getCollection());
		if (player.getCollection() instanceof Playlist)	{
			System.out.println("is playlist");
			Playlist playlist = (Playlist) player.getCollection();
			ArrayList<Song> songs = LibraryController.lib.getSongsInCollection();

			for (Song song : songs)	{
				System.out.println(song.getPath());
				if (song.getPath().equals(path))	{
					System.out.println("same path");
					playlist.addSongToCollection(song);
				}
			}
		}
	}
}
