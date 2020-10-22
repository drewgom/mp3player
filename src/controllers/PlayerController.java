package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import views.PlayerView;

public class PlayerController {
	
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
				case "fastForward":
					fastForward();
					break;
				case "rewind":
					rewind();
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
	
	//change to instanciate when there's time
	public static PlayerView pv = null;
	
	protected static void play() {
		System.out.println("Play button pressed.");
		pv.setPause();
	}
	protected static void pause() {
		System.out.println("Pause button pressed.");
		pv.setPlay();
	}
	protected static void fastForward() {
		System.out.println("Fast forward button pressed.");
	}
	protected static void rewind() {
		System.out.println("Rewind button pressed.");
	}
	protected static void skipForward() {
		System.out.println("Skip forward button pressed.");
	}
	protected static void skipBack() {
		System.out.println("Skip back button pressed.");
	}
	protected static void add() {
		System.out.println("Add button pressed.");
	}
	protected static void remove() {
		System.out.println("Remove button pressed.");
	}
}
