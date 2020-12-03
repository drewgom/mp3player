package views;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;

import controllers.CollectionManagerController;
import controllers.LibraryController;
import controllers.PlayerController;
import models.Player;
import models.Song;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Dimension;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.*;
import java.awt.event.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.awt.FlowLayout;
import java.awt.Component;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Cursor;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainPlayerView extends PlayerView{
	private JFrame mainWindow = null;
	private String title = "Drew's MP3 Player";

	private JTable LibraryTable = null;
	private JPanel PlayPauseFlip = null;
	private CardLayout PlayPauseCard = null;
	private PlayerController controller;
	private ActionListener listener = new buttonListener();
	private ChangeListener changeListener = new sliderListener();
	private TreeSelectionListener treeListener = new treeListener();
	private LibraryController libraryController = new LibraryController();
	private CollectionManagerController collectionManagerController = new CollectionManagerController();
	private DragSource ds = new DragSource();
	private ActionListener contextListener = new contextListener();
	private Integer tableRow = null;
	private String treeString = null;
	private JLabel NowPlaying = null;
	private JMenu contextAddPlaylist = null;
	JTree tree = null;

	private JFrame confirmationWindow = null;

	private JButton [] playBackButtons = new JButton[5];

	public MainPlayerView(Player player)	{
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch(Exception ex) {
			ex.printStackTrace();
		}

		controller = new PlayerController(this, player);

		this.mainWindow = new JFrame(title + " - Now Playing: Nothing");
		mainWindow.setMinimumSize(new Dimension(480, 400));
		mainWindow.setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\Svona\\Desktop\\Git\\mp3player\\res\\window-icon.png"));
		mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainWindow.getContentPane().setLayout(new BorderLayout(0, 0));

		NowPlaying = new JLabel("Now Playing - Nothing");
		NowPlaying.setHorizontalAlignment(SwingConstants.CENTER);
		// mainWindow.getContentPane().add(NowPlaying, BorderLayout.CENTER);

		tree = new JTree(collectionManagerController.getTreeOfPlaylists());
		tree.setRootVisible(false);
		for (int i = 0; i < tree.getRowCount(); i++) {
			tree.expandRow(i);
		}

		JPopupMenu TreeContext = new JPopupMenu();

		JMenuItem contextDeleteCollection = new JMenuItem("Delete Song Collection");
		contextDeleteCollection.addActionListener(this.contextListener);
		contextDeleteCollection.setActionCommand("deleteCollection");
		TreeContext.add(contextDeleteCollection);

		JMenuItem contextOpenCollection = new JMenuItem("Open Song Collection in New Window");
		contextOpenCollection.addActionListener(this.contextListener);
		contextOpenCollection.setActionCommand("openCollection");

		tree.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				Object[] pathAsArray = tree.getPathForLocation(e.getX(), e.getY()).getPath();
				System.out.println("Hello");
				treeString = pathAsArray[pathAsArray.length-1].toString();
				System.out.println(treeString);
			}
		});

		tree.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if(SwingUtilities.isRightMouseButton(e))	{
					TreeContext.show(tree, e.getX(), e.getY());
				}
			}
		});

		TreeContext.add(contextOpenCollection);
		tree.addTreeSelectionListener(treeListener);
		mainWindow.add(tree);

		JPanel Interface = new JPanel();
		mainWindow.getContentPane().add(Interface, BorderLayout.EAST);

		LibraryTable = new JTable();
		LibraryTable.setModel(controller.getTableModelOfData());
		LibraryTable.setPreferredSize(new Dimension(200, 200));
		LibraryTable.setFillsViewportHeight(true);
		LibraryTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		LibraryTable.setRowSelectionAllowed(true);
		LibraryTable.setShowVerticalLines(false);

		MouseListener mouseListener = new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				tableRow = LibraryTable.getSelectedRow();
				System.out.println("Selected index = " + tableRow);
			}
		};

		LibraryTable.addMouseListener(mouseListener);

		JPopupMenu LibraryContext = new JPopupMenu();

		JMenuItem contextPlay = new JMenuItem("Play");
		contextPlay.addActionListener(this.contextListener);
		contextPlay.setActionCommand("play");
		LibraryContext.add(contextPlay);

		JMenuItem contextAdd = new JMenuItem("Add");
		contextAdd.addActionListener(this.contextListener);
		contextAdd.setActionCommand("add");
		LibraryContext.add(contextAdd);

		JMenuItem contextRemove = new JMenuItem("Remove");
		contextRemove.addActionListener(this.contextListener);
		contextRemove.setActionCommand("remove");
		LibraryContext.add(contextRemove);

		contextAddPlaylist = new JMenu("Add to Playlist");
		collectionManagerController.getPlaylistContexts(contextAddPlaylist, this.contextListener);
		LibraryContext.add(contextAddPlaylist);

		LibraryTable.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if(e.getButton() == e.BUTTON3)
					LibraryContext.show(LibraryTable , e.getX(), e.getY());
			}
		});


		DragGestureListener dragListener = new DragGestureListener() {
			@Override
			public void dragGestureRecognized(DragGestureEvent dge) {
				Cursor cursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);
				JTable jt = (JTable) dge.getComponent();
				jt.setDragEnabled(true);

				dge.startDrag(cursor, new FileTrans(jt.getSelectedRows()));
			}
		};

		LibraryTable.add(LibraryContext);
		LibraryTable.setDropTarget(new LibraryDrop());
		ds.createDefaultDragGestureRecognizer(LibraryTable, DnDConstants.ACTION_COPY, dragListener);

		JScrollPane LibraryScroll = new JScrollPane();
		LibraryScroll.setPreferredSize(new Dimension(300, 200));
		LibraryScroll.setViewportBorder(null);
		LibraryScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		LibraryScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		LibraryScroll.setViewportView(LibraryTable);

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
		Interface.add(LibraryScroll, BorderLayout.CENTER);
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
		SkipBackButton.addActionListener(this.listener);
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
		PlayButton.addActionListener(this.listener);
		PlayPauseFlip.add(PlayButton, "play");

		JButton PauseButton = new JButton();
		PauseButton.setIcon(iconDownscale(new ImageIcon("res/pause-button.png")));
		PauseButton.setPreferredSize(new Dimension(50, 50));
		PauseButton.setAlignmentX(0.5f);
		PauseButton.setActionCommand("pause");
		PauseButton.addActionListener(this.listener);
		PlayPauseFlip.add(PauseButton, "pause");

		JButton StopButton = new JButton();
		StopButton.setIcon(iconDownscale(new ImageIcon("res/stop-button.png")));
		StopButton.setPreferredSize(new Dimension(50, 50));
		StopButton.setAlignmentX(0.5f);
		StopButton.setActionCommand("stop");
		StopButton.addActionListener(this.listener);
		PlaybackControls.add(StopButton);

		JButton SkipForwardButton = new JButton();
		SkipForwardButton.setIcon(iconDownscale(new ImageIcon("res/next-button.png")));
		SkipForwardButton.setPreferredSize(new Dimension(50, 50));
		SkipForwardButton.setAlignmentX(0.5f);
		SkipForwardButton.setActionCommand("skipForward");
		SkipForwardButton.addActionListener(this.listener);
		PlaybackControls.add(SkipForwardButton);

		JSlider VolumeSlider = new JSlider();
		VolumeSlider.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		VolumeSlider.setPreferredSize(new Dimension(20, 60));
		VolumeSlider.setOrientation(SwingConstants.VERTICAL);
		SongControls.add(VolumeSlider, BorderLayout.EAST);
		VolumeSlider.addChangeListener(changeListener);

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

		JMenuItem menuStop = new JMenuItem("Stop");
		menuStop.addActionListener(this.listener);
		menuStop.setActionCommand("stop");
		PlaybackMenu.add(menuStop);

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
		JMenuItem createPlaylist = new JMenuItem("Create Playlist");
		createPlaylist.addActionListener(this.listener);
		createPlaylist.setActionCommand("createPlaylist");
		OptionsMenu.add(createPlaylist);
		menuBar.add(OptionsMenu);

		this.playBackButtons[0] = SkipBackButton;
		this.playBackButtons[1] = SkipForwardButton;
		this.playBackButtons[2] = StopButton;
		this.playBackButtons[3] = PauseButton;
		this.playBackButtons[4] = PlayButton;

		JSlider Timebar = new JSlider();
		Timebar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		Timebar.setValue(0);
		Timebar.setPaintLabels(true);
		SongControls.add(Timebar, BorderLayout.NORTH);
		collectionManagerController.attachWindow(this);
	}
	
	public File addPopup() {
		JFileChooser selector = new JFileChooser("..");
		selector.setName("Please select a song to add to the library.");
		selector.setFileFilter(new FileNameExtensionFilter("MP3 Files", "mp3"));
		selector.setMultiSelectionEnabled(false);
		
		File song = null;
		if(selector.showOpenDialog(mainWindow) == JFileChooser.APPROVE_OPTION) {
			song = selector.getSelectedFile();
		}
		
		return song;
	}
	
	/**
	public void showConfirmationWindow(String title, String message) {
		
		confirmationWindow.setVisible(true);
	}
	
	public void hideConfirmationWindow() {
		confirmationWindow.setVisible(false);
	}
	**/
	
	public void setNowPlaying(String text) {
		mainWindow.setTitle(title + " - " + text);
		NowPlaying.setText(text);
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

	public Integer getTableRow()	{
		return tableRow;
	}

	public void display()	{
		mainWindow.setVisible(true);
	}

	public void repaint()	{
		DefaultTableModel model = controller.getTableModelOfData();
		tree.setModel(collectionManagerController.getTreeOfPlaylists());
		for (int i = 0; i < tree.getRowCount(); i++) {
			tree.expandRow(i);
		}
		contextAddPlaylist.removeAll();
		collectionManagerController.getPlaylistContexts(contextAddPlaylist,this.contextListener);
		System.out.println("about to repaint");
		LibraryTable.setModel(model);
	}

	public void refreshSelected(Integer newRow)	{
		LibraryTable.setRowSelectionInterval(newRow,newRow);
	}

	public int getSelectedIndex() {
		return LibraryTable.getSelectedRow();
	}
	
	public void setTable(Object[][] data, Object[] columnNames) {
		LibraryTable.setModel(new DefaultTableModel(data, columnNames));
	}

	public class buttonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			switch (e.getActionCommand()) {
				case "play":
					controller.play();
					break;
				case "pause":
					controller.pause();
					break;
				case "stop":
					controller.stop();
					break;
				case "skipForward":
					controller.skipForward();
					break;
				case "skipBack":
					controller.skipBack();
					break;
				case "add":
					controller.addViaPopup();
					collectionManagerController.refreshWindows();
					break;
				case "remove":
					controller.remove();
					collectionManagerController.refreshWindows();
					break;
				case "createPlaylist":
					collectionManagerController.createPlaylist();
					collectionManagerController.refreshWindows();
					break;
			}
		}
	};

	public class contextListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			switch(e.getActionCommand()) {
				case "play":
					controller.playSelected();
					break;
				case "add":
					controller.addViaPopup();
					collectionManagerController.refreshWindows();
					break;
				case "remove":
					controller.removeSelected();
					collectionManagerController.refreshWindows();
					break;
				case "addToPlaylist":
					JMenuItem sourceObj = (JMenuItem) e.getSource();
					System.out.println("Option selected was " + sourceObj.getText());
					Song sng = controller.getSongFromIndex(tableRow);
					collectionManagerController.addSongToPlaylist(sng, sourceObj.getText());
					break;
				case "openCollection":
					System.out.println("In open collection");
					collectionManagerController.openCollection(treeString);
					controller.swtichCollectionForPlayer("Library");
					break;
				case "deleteCollection":
					collectionManagerController.deletePlaylist(treeString);
					break;
			}
		}
	}

	public class treeListener implements TreeSelectionListener {
		@Override
		public void valueChanged(TreeSelectionEvent e) {
			System.out.println("value changed");
			DefaultMutableTreeNode node = (DefaultMutableTreeNode)
					tree.getLastSelectedPathComponent();
			/* if nothing is selected */
			if (node == null) return;

			System.out.println("clicked on tree");
			Object nodeInfo = node.getUserObject();
			System.out.println(nodeInfo.toString());

			if (nodeInfo.toString() != "Playlists") {
				controller.swtichCollectionForPlayer(nodeInfo.toString());
				System.out.println("After call to switch collection");
			}
		}
	}

	public class sliderListener implements ChangeListener	{
		@Override
		public void stateChanged(ChangeEvent e) {
			JSlider source =(JSlider) e.getSource();
			double valueAsDouble = source.getValue();
			controller.updateVolume(valueAsDouble/100);
		}
	}

	public class LibraryDrop extends DropTarget {
		public void drop (DropTargetDropEvent evt) {
			try {
				evt.acceptDrop(DnDConstants.ACTION_COPY);

				//TODO Figure out what type this returns
				List result = (List) evt.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);

				for(Object o : result) {
					System.out.println("Dropped file: "+o.toString());
					controller.droppedOnToTable(o.toString());
					collectionManagerController.refreshWindows();
					//Can't write specifics since how you set things up hasn't been pushed to the git.
				}
			}
			catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	public class FileTrans implements Transferable	{
		ArrayList<String> paths;

		@Override
		public DataFlavor[] getTransferDataFlavors() {
			return new DataFlavor[]{DataFlavor.javaFileListFlavor};
		}

		@Override
		public boolean isDataFlavorSupported(DataFlavor flavor) {
			if (flavor == DataFlavor.javaFileListFlavor)	{
				return true;
			}
			return false;
		}

		@Override
		public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
			return paths;
		}

		public FileTrans(int[] rows)	{
			String songfile;
			paths = new ArrayList();
			for (int i = 0; i < rows.length; i++) {
				songfile = controller.getSongFromIndex(rows[i]).getPath();
				paths.add(songfile);
			}
		}
	}

	public ActionListener getContextListener() {
		return contextListener;
	}
}