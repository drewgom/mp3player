package views;

import javax.swing.*;

import controllers.LibraryController;
import controllers.PlayerController;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Dimension;
import java.awt.event.*;
import java.awt.FlowLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.Component;
import java.awt.BorderLayout;
import java.awt.CardLayout;

public class PlayerView{
	private JFrame mainWindow = null;
	private String title = "Austin & Drew's MP3 Player";

	private JTable LibraryTable = null;
	private JPanel PlayPauseFlip = null;
	private CardLayout PlayPauseCard = null;
	private ActionListener playerListener = new PlayerController.buttonListener();
	private LibraryController libraryController = new LibraryController();
	public static Integer row = null;
	private static PlayerView playerView;
	
	private JButton [] playBackButtons = new JButton[5];

	public static PlayerView getPlayerView()	{
		if (playerView == null)	{
			playerView = new PlayerView();
		}

		return playerView;
	}
	private PlayerView()	{
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
		LibraryTable.setModel(libraryController.getTableModelOfData());
		LibraryTable.setPreferredSize(new Dimension(500, 200));
		LibraryTable.setFillsViewportHeight(true);

		MouseListener mouseListener = new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				row = LibraryTable.getSelectedRow();
				System.out.println("Selected index = " + row);
			}
		};

		LibraryTable.addMouseListener(mouseListener);
		
		JPanel LibraryControls = new JPanel();
		
		JButton Add = new JButton();
		Add.setAlignmentX(Component.CENTER_ALIGNMENT);
		Add.addActionListener(this.playerListener);
		LibraryControls.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		Add.setActionCommand("add");
		Add.setPreferredSize(new Dimension(50, 50));
		Add.setIcon(iconDownscale(new ImageIcon("res/plus-button.png")));
		LibraryControls.add(Add);
		
		JButton Remove = new JButton();
		Remove.setAlignmentX(Component.CENTER_ALIGNMENT);
		Remove.addActionListener(this.playerListener);
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
		SongControls.add(PlaybackControls, BorderLayout.CENTER);
		PlaybackControls.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JButton SkipBackButton = new JButton();
		SkipBackButton.setIcon(iconDownscale(new ImageIcon("res/previous-button.png")));
		SkipBackButton.setPreferredSize(new Dimension(50, 50));
		SkipBackButton.setAlignmentX(0.5f);
		SkipBackButton.setActionCommand("skipBack");
		SkipBackButton.addActionListener(this.playerListener);
		PlaybackControls.add(SkipBackButton);
		
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
		PlayButton.addActionListener(this.playerListener);
		PlayPauseFlip.add(PlayButton, "play");
		
		JButton PauseButton = new JButton();
		PauseButton.setIcon(iconDownscale(new ImageIcon("res/pause-button.png")));
		PauseButton.setPreferredSize(new Dimension(50, 50));
		PauseButton.setAlignmentX(0.5f);
		PauseButton.setActionCommand("pause");
		PauseButton.addActionListener(this.playerListener);
		PlayPauseFlip.add(PauseButton, "pause");
		
		JButton StopButton = new JButton();
		StopButton.setIcon(iconDownscale(new ImageIcon("res/stop-button.png")));
		StopButton.setPreferredSize(new Dimension(50, 50));
		StopButton.setAlignmentX(0.5f);
		StopButton.setActionCommand("stop");
		StopButton.addActionListener(this.playerListener);
		PlaybackControls.add(StopButton);
		
		JButton SkipForwardButton = new JButton();
		SkipForwardButton.setIcon(iconDownscale(new ImageIcon("res/next-button.png")));
		SkipForwardButton.setPreferredSize(new Dimension(50, 50));
		SkipForwardButton.setAlignmentX(0.5f);
		SkipForwardButton.setActionCommand("skipForward");
		SkipForwardButton.addActionListener(this.playerListener);
		PlaybackControls.add(SkipForwardButton);
		
		JSlider VolumeSlider = new JSlider();
		VolumeSlider.setPreferredSize(new Dimension(20, 60));
		VolumeSlider.setOrientation(SwingConstants.VERTICAL);
		SongControls.add(VolumeSlider, BorderLayout.EAST);
		
		this.mainWindow.pack();

		JMenuBar menuBar = new JMenuBar();
		mainWindow.setJMenuBar(menuBar);
		
		JMenu PlaybackMenu = new JMenu("Playback");
		menuBar.add(PlaybackMenu);

		JMenuItem menuPlay = new JMenuItem("Play");
		menuPlay.addActionListener(this.playerListener);
		menuPlay.setActionCommand("play");
		PlaybackMenu.add(menuPlay);
		
		JMenuItem menuPause = new JMenuItem("Pause");
		menuPause.addActionListener(this.playerListener);
		menuPause.setActionCommand("pause");
		PlaybackMenu.add(menuPause);
		
		JMenuItem menuRewind = new JMenuItem("Skip Forward");
		menuRewind.addActionListener(this.playerListener);
		menuRewind.setActionCommand("skipForward");
		PlaybackMenu.add(menuRewind);
		
		JMenuItem menuSkipBack = new JMenuItem("Skip Back");
		menuSkipBack.addActionListener(this.playerListener);
		menuSkipBack.setActionCommand("skipBack");
		PlaybackMenu.add(menuSkipBack);

		JMenuItem menuStop = new JMenuItem("Stop");
		menuStop.addActionListener(this.playerListener);
		menuStop.setActionCommand("stop");
		PlaybackMenu.add(menuStop);

		JMenu LibraryMenu = new JMenu("Library");
		menuBar.add(LibraryMenu);
		
		JMenuItem menuAdd = new JMenuItem("Add");
		menuAdd.addActionListener(this.playerListener);
		menuAdd.setActionCommand("add");
		LibraryMenu.add(menuAdd);
		
		JMenuItem menuRemove = new JMenuItem("Remove");
		menuRemove.addActionListener(this.playerListener);
		menuRemove.setActionCommand("remove");
		LibraryMenu.add(menuRemove);
		
		JMenu OptionsMenu = new JMenu("Options");
		menuBar.add(OptionsMenu);
		
		this.playBackButtons[0] = SkipBackButton;
		this.playBackButtons[1] = SkipForwardButton;
		this.playBackButtons[2] = StopButton;
		this.playBackButtons[3] = PauseButton;
		this.playBackButtons[4] = PlayButton;
		
		JSlider Timebar = new JSlider();
		Timebar.setValue(0);
		Timebar.setPaintLabels(true);
		SongControls.add(Timebar, BorderLayout.NORTH);
		
		this.mainWindow.setVisible(true);
	}
	
	public void setPause() {
		this.PlayPauseCard.last(PlayPauseFlip);
	}
	
	public void setPlay() {
		this.PlayPauseCard.first(PlayPauseFlip);
	}
	
	public void setGray() {
		for(int i = 0; i < 5; i++) {
			this.playBackButtons[i].setEnabled(false);
		}
	}
	
	public void setNormal() {
		for(int i = 0; i < 5; i++) {
			this.playBackButtons[i].setEnabled(true);
		}
	}
	
	private static ImageIcon iconDownscale(ImageIcon imageIcon) {
		return new ImageIcon(imageIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
	}

	public static Integer getRow()	{
		return row;
	}

	public static void display()	{
		playerView.mainWindow.setVisible(true);
	}

	public void repaint()	{
		//DefaultTableModel model = (DefaultTableModel) LibraryTable.getModel();
		//model.fireTableDataChanged();
		//LibraryTable.repaint();
	}
}