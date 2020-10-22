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

public class PlayerView{
	private JFrame mainWindow = null;
	private String title = "Austin & Drew's MP3 Player";
	private JTable Librarytable = null;
	private JTable LibraryTable;
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
		
		JPanel PlaybackControls = new JPanel();
		
		JButton SkipBack = new JButton();
		SkipBack.setAlignmentX(Component.CENTER_ALIGNMENT);
		SkipBack.addActionListener(this.listener);
		SkipBack.setActionCommand("skipBack");
		SkipBack.setPreferredSize(new Dimension(50, 50));
		SkipBack.setIcon(iconDownscale(new ImageIcon("res/previous-button.png")));
		
		JButton Rewind = new JButton();
		Rewind.setAlignmentX(Component.CENTER_ALIGNMENT);
		Rewind.addActionListener(this.listener);
		Rewind.setActionCommand("rewind");
		Rewind.setPreferredSize(new Dimension(50, 50));
		Rewind.setIcon(iconDownscale(new ImageIcon("res/rewind-button.png")));
		
		JButton PlayPause = new JButton();
		PlayPause.setAlignmentX(Component.CENTER_ALIGNMENT);
		PlayPause.addActionListener(this.listener);
		PlayPause.setActionCommand("play");
		PlayPause.setPreferredSize(new Dimension(50, 50));
		PlayPause.setIcon(iconDownscale(new ImageIcon("res/play-button.png")));
		
		JButton FastForward = new JButton();
		FastForward.setAlignmentX(Component.CENTER_ALIGNMENT);
		FastForward.addActionListener(this.listener);
		FastForward.setActionCommand("fastForward");
		FastForward.setPreferredSize(new Dimension(50, 50));
		FastForward.setIcon(iconDownscale(new ImageIcon("res/fast-forward-button.png")));
		
		JButton SkipForward = new JButton();
		SkipForward.setAlignmentX(Component.CENTER_ALIGNMENT);
		SkipForward.addActionListener(this.listener);
		SkipForward.setActionCommand("skipForward");
		SkipForward.setPreferredSize(new Dimension(50, 50));
		SkipForward.setIcon(iconDownscale(new ImageIcon("res/next-button.png")));
		PlaybackControls.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		PlaybackControls.add(SkipBack);
		PlaybackControls.add(Rewind);
		PlaybackControls.add(PlayPause);
		PlaybackControls.add(FastForward);
		PlaybackControls.add(SkipForward);
		Interface.setLayout(new BorderLayout(0, 0));
		Interface.add(LibraryTable, BorderLayout.CENTER);
		Interface.add(LibraryControls, BorderLayout.NORTH);
		Interface.add(PlaybackControls, BorderLayout.SOUTH);
		
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
	
	private static ImageIcon iconDownscale(ImageIcon imageIcon) {
		return new ImageIcon(imageIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
	}
}
