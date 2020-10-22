package views;

import javax.swing.*;

import controllers.PlayerController;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.FlowLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.Component;
import java.awt.BorderLayout;
import java.awt.CardLayout;

public class PlayerView{
	private JFrame mainWindow = null;
	private String title = "Austin & Drew's MP3 Player";
	private JTable LibraryTable = null;
	private JPanel PlayPauseFlip = null;
	private CardLayout PlayPauseCard = null;
	private ActionListener listener = new PlayerController.buttonListener();
	
	public PlayerView(){
		this.mainWindow = new JFrame(title + " - Now Playing: Nothing");
		mainWindow.setMinimumSize(new Dimension(480, 400));
		mainWindow.setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\Svona\\Desktop\\Git\\mp3player\\res\\window-icon.png"));
		mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainWindow.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JLabel NowPlaying = new JLabel("Now Playing - Nothing");
		NowPlaying.setHorizontalAlignment(SwingConstants.CENTER);
		mainWindow.getContentPane().add(NowPlaying, BorderLayout.CENTER);
		
		JPanel Interface = new JPanel();
		mainWindow.getContentPane().add(Interface, BorderLayout.EAST);
		
		LibraryTable = new JTable();
		LibraryTable.setPreferredSize(new Dimension(200, 200));
		LibraryTable.setFillsViewportHeight(true);
		
		JPanel LibraryControls = new JPanel();
		
		JButton Add = new JButton();
		Add.setAlignmentX(Component.CENTER_ALIGNMENT);
		Add.addActionListener(this.listener);
		LibraryControls.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		Add.setActionCommand("add");
		Add.setPreferredSize(new Dimension(50, 50));
		Add.setIcon(iconDownscale(new ImageIcon("res/plus-button.png")));
		LibraryControls.add(Add);
		
		JButton Remove = new JButton();
		Remove.setAlignmentX(Component.CENTER_ALIGNMENT);
		Remove.addActionListener(this.listener);
		Remove.setActionCommand("remove");
		Remove.setPreferredSize(new Dimension(50, 50));
		Remove.setIcon(iconDownscale(new ImageIcon("res/minus-button.png")));
		LibraryControls.add(Remove);
		Interface.setLayout(new BorderLayout(0, 0));
		Interface.add(LibraryTable, BorderLayout.CENTER);
		Interface.add(LibraryControls, BorderLayout.NORTH);
		
		JPanel SongControls = new JPanel();
		Interface.add(SongControls, BorderLayout.SOUTH);
		SongControls.setLayout(new BorderLayout(0, 0));
		
		JPanel PlaybackControls = new JPanel();
		SongControls.add(PlaybackControls, BorderLayout.SOUTH);
		PlaybackControls.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JButton SkipBackButton = new JButton();
		SkipBackButton.setIcon(iconDownscale(new ImageIcon("res/previous-button.png")));
		SkipBackButton.setPreferredSize(new Dimension(50, 50));
		SkipBackButton.setAlignmentX(0.5f);
		SkipBackButton.setActionCommand("skipBack");
		SkipBackButton.addActionListener(this.listener);
		PlaybackControls.add(SkipBackButton);
		
		JButton RewindButton = new JButton();
		RewindButton.setIcon(iconDownscale(new ImageIcon("res/rewind-button.png")));
		RewindButton.setPreferredSize(new Dimension(50, 50));
		RewindButton.setAlignmentX(0.5f);
		RewindButton.setActionCommand("rewind");
		RewindButton.addActionListener(this.listener);
		PlaybackControls.add(RewindButton);
		
		PlayPauseFlip = new JPanel();
		PlayPauseFlip.setPreferredSize(new Dimension(50, 50));
		PlaybackControls.add(PlayPauseFlip);
		PlayPauseCard = new CardLayout(0, 0);
		PlayPauseFlip.setLayout(PlayPauseCard);
		
		JButton PlayButton = new JButton();
		PlayButton.setIcon(iconDownscale(new ImageIcon("res/play-button.png")));
		PlayButton.setPreferredSize(new Dimension(50, 50));
		PlayButton.setAlignmentX(0.5f);
		PlayButton.setActionCommand("play");
		PlayButton.addActionListener(this.listener);
		PlayPauseFlip.add(PlayButton, "play");
		
		JButton PauseButton = new JButton();
		PauseButton.setIcon(iconDownscale(new ImageIcon("res/pause-button.png")));
		PauseButton.setPreferredSize(new Dimension(50, 50));
		PauseButton.setAlignmentX(0.5f);
		PauseButton.setActionCommand("pause");
		PauseButton.addActionListener(this.listener);
		PlayPauseFlip.add(PauseButton, "pause");
		
		JButton FastForwardButton = new JButton();
		FastForwardButton.setIcon(iconDownscale(new ImageIcon("res/fast-forward-button.png")));
		FastForwardButton.setPreferredSize(new Dimension(50, 50));
		FastForwardButton.setAlignmentX(0.5f);
		FastForwardButton.setActionCommand("fastForward");
		FastForwardButton.addActionListener(this.listener);
		PlaybackControls.add(FastForwardButton);
		
		JButton SkipForwardButton = new JButton();
		SkipForwardButton.setIcon(iconDownscale(new ImageIcon("res/next-button.png")));
		SkipForwardButton.setPreferredSize(new Dimension(50, 50));
		SkipForwardButton.setAlignmentX(0.5f);
		SkipForwardButton.setActionCommand("skipForward");
		SkipForwardButton.addActionListener(this.listener);
		PlaybackControls.add(SkipForwardButton);
		
		JSlider VolumeSlider = new JSlider();
		SongControls.add(VolumeSlider, BorderLayout.NORTH);
		
		this.mainWindow.pack();
		
		JMenuBar menuBar = new JMenuBar();
		mainWindow.setJMenuBar(menuBar);
		
		JMenu PlaybackMenu = new JMenu("Playback");
		menuBar.add(PlaybackMenu);
		
		JMenuItem menuPlay = new JMenuItem("Play");
		menuPlay.addActionListener(this.listener);
		menuPlay.setActionCommand("play");
		PlaybackMenu.add(menuPlay);
		
		JMenuItem menuPause = new JMenuItem("Pause");
		menuPause.addActionListener(this.listener);
		menuPause.setActionCommand("pause");
		PlaybackMenu.add(menuPause);
		
		JMenuItem menuFastForward = new JMenuItem("Fast Forward");
		menuFastForward.addActionListener(this.listener);
		menuFastForward.setActionCommand("fastForward");
		PlaybackMenu.add(menuFastForward);
		
		JMenuItem menuRewind = new JMenuItem("Rewind");
		menuRewind.addActionListener(this.listener);
		menuRewind.setActionCommand("rewind");
		PlaybackMenu.add(menuRewind);
		
		JMenuItem menuSkipForward = new JMenuItem("Skip Forward");
		menuSkipForward.addActionListener(this.listener);
		menuSkipForward.setActionCommand("skipForward");
		PlaybackMenu.add(menuSkipForward);
		
		JMenuItem menuSkipBack = new JMenuItem("Skip Back");
		menuSkipBack.addActionListener(this.listener);
		menuSkipBack.setActionCommand("skipBack");
		PlaybackMenu.add(menuSkipBack);
		
		JMenu LibraryMenu = new JMenu("Library");
		menuBar.add(LibraryMenu);
		
		JMenuItem menuAdd = new JMenuItem("Add");
		menuAdd.addActionListener(this.listener);
		menuAdd.setActionCommand("add");
		LibraryMenu.add(menuAdd);
		
		JMenuItem menuRemove = new JMenuItem("Remove");
		menuRemove.addActionListener(this.listener);
		menuRemove.setActionCommand("remove");
		LibraryMenu.add(menuRemove);
		
		JMenu OptionsMenu = new JMenu("Options");
		menuBar.add(OptionsMenu);
		this.mainWindow.setVisible(true);
	}
	
	public void setPause() {
		this.PlayPauseCard.last(PlayPauseFlip);
	}
	
	public void setPlay() {
		this.PlayPauseCard.first(PlayPauseFlip);
	}
	
	public void setGray() {
		;
	}
	
	public void setNormal() {
		;
	}
	
	private static ImageIcon iconDownscale(ImageIcon imageIcon) {
		return new ImageIcon(imageIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
	}
}
