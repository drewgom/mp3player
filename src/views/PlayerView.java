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

public class PlayerView{
	private JFrame mainWindow = null;
	private String title = "Austin & Drew's MP3 Player";
	private JTable Librarytable = null;
	private JTable LibraryTable;
	private ActionListener listener = new PlayerController.buttonListener();
	
	public PlayerView(){
		this.mainWindow = new JFrame(title + " - Now Playing: Nothing");
		mainWindow.setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\Svona\\Desktop\\Git\\mp3player\\res\\window-icon.png"));
		mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		mainWindow.getContentPane().setLayout(gridBagLayout);
		
		JLabel NowPlaying = new JLabel("Now Playing - Nothing");
		GridBagConstraints gbc_NowPlaying = new GridBagConstraints();
		gbc_NowPlaying.gridheight = 2;
		gbc_NowPlaying.weightx = 10.0;
		gbc_NowPlaying.insets = new Insets(0, 0, 5, 5);
		gbc_NowPlaying.gridx = 0;
		gbc_NowPlaying.gridy = 0;
		mainWindow.getContentPane().add(NowPlaying, gbc_NowPlaying);
		
		JPanel Interface = new JPanel();
		GridBagConstraints gbc_Interface = new GridBagConstraints();
		gbc_Interface.gridx = 1;
		gbc_Interface.fill = GridBagConstraints.VERTICAL;
		mainWindow.getContentPane().add(Interface, gbc_Interface);
		
		LibraryTable = new JTable();
		LibraryTable.setPreferredSize(new Dimension(200, 200));
		LibraryTable.setFillsViewportHeight(true);
		
		JPanel LibraryControls = new JPanel();
		LibraryControls.setSize(new Dimension(200, 70));
		
		JButton Add = new JButton();
		Add.addActionListener(this.listener);
		LibraryControls.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		Add.setActionCommand("add");
		Add.setPreferredSize(new Dimension(50, 50));
		Add.setIcon(iconDownscale(new ImageIcon("res/plus-button.png")));
		LibraryControls.add(Add);
		
		JButton Remove = new JButton();
		Remove.addActionListener(this.listener);
		Remove.setActionCommand("remove");
		Remove.setPreferredSize(new Dimension(50, 50));
		Remove.setIcon(iconDownscale(new ImageIcon("res/minus-button.png")));
		LibraryControls.add(Remove);
		
		JPanel PlaybackControls = new JPanel();
		PlaybackControls.setMinimumSize(new Dimension(300, 70));
		PlaybackControls.setMaximumSize(new Dimension(32767, 70));
		PlaybackControls.setPreferredSize(new Dimension(300, 70));
		
		JButton SkipBack = new JButton();
		SkipBack.addActionListener(this.listener);
		SkipBack.setActionCommand("skipBack");
		SkipBack.setPreferredSize(new Dimension(50, 50));
		SkipBack.setIcon(iconDownscale(new ImageIcon("res/previous-button.png")));
		
		JButton Rewind = new JButton();
		Rewind.addActionListener(this.listener);
		Rewind.setActionCommand("rewind");
		Rewind.setPreferredSize(new Dimension(50, 50));
		Rewind.setIcon(iconDownscale(new ImageIcon("res/rewind-button.png")));
		
		JButton PlayPause = new JButton();
		PlayPause.addActionListener(this.listener);
		PlayPause.setActionCommand("play");
		PlayPause.setPreferredSize(new Dimension(50, 50));
		PlayPause.setIcon(iconDownscale(new ImageIcon("res/play-button.png")));
		
		JButton FastForward = new JButton();
		FastForward.addActionListener(this.listener);
		FastForward.setActionCommand("fastForward");
		FastForward.setPreferredSize(new Dimension(50, 50));
		FastForward.setIcon(iconDownscale(new ImageIcon("res/fast-forward-button.png")));
		
		JButton SkipForward = new JButton();
		SkipForward.addActionListener(this.listener);
		SkipForward.setActionCommand("skipForward");
		SkipForward.setPreferredSize(new Dimension(50, 50));
		SkipForward.setIcon(iconDownscale(new ImageIcon("res/next-button.png")));
		GroupLayout gl_PlaybackControls = new GroupLayout(PlaybackControls);
		gl_PlaybackControls.setHorizontalGroup(
			gl_PlaybackControls.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_PlaybackControls.createSequentialGroup()
					.addContainerGap(5, Short.MAX_VALUE)
					.addComponent(SkipBack, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
					.addGap(5)
					.addComponent(Rewind, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(5)
					.addComponent(PlayPause, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(5)
					.addComponent(FastForward, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(5)
					.addComponent(SkipForward, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(20))
		);
		gl_PlaybackControls.setVerticalGroup(
			gl_PlaybackControls.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_PlaybackControls.createSequentialGroup()
					.addGap(5)
					.addGroup(gl_PlaybackControls.createParallelGroup(Alignment.LEADING)
						.addComponent(FastForward, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(SkipBack, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(Rewind, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(PlayPause, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(SkipForward, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(5))
		);
		PlaybackControls.setLayout(gl_PlaybackControls);
		GroupLayout gl_Interface = new GroupLayout(Interface);
		gl_Interface.setHorizontalGroup(
			gl_Interface.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_Interface.createSequentialGroup()
					.addGap(5)
					.addGroup(gl_Interface.createParallelGroup(Alignment.TRAILING, false)
						.addGroup(gl_Interface.createSequentialGroup()
							.addComponent(LibraryTable, GroupLayout.PREFERRED_SIZE, 280, GroupLayout.PREFERRED_SIZE)
							.addGap(5))
						.addComponent(LibraryControls, GroupLayout.PREFERRED_SIZE, 280, GroupLayout.PREFERRED_SIZE)
						.addComponent(PlaybackControls, GroupLayout.PREFERRED_SIZE, 280, GroupLayout.PREFERRED_SIZE)))
		);
		gl_Interface.setVerticalGroup(
			gl_Interface.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_Interface.createSequentialGroup()
					.addComponent(LibraryTable, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
					.addGap(5)
					.addComponent(LibraryControls, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
					.addGap(5)
					.addComponent(PlaybackControls, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
					.addGap(0, 0, Short.MAX_VALUE))
		);
		Interface.setLayout(gl_Interface);
		
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
