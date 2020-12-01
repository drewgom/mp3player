package views;

import controllers.CollectionManagerController;
import controllers.LibraryController;
import controllers.PlayerController;
import models.Player;
import models.Playlist;
import models.Song;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.*;
import java.io.File;
import java.util.List;

public class PlaylistView extends PlayerView {
    private JFrame playlistWindow = null;
    private String title = null;

    private JTable LibraryTable = null;
    private JPanel PlayPauseFlip = null;
    private CardLayout PlayPauseCard = null;
    private PlayerController controller;
    private ActionListener listener = new buttonListener();
    private ChangeListener changeListener = new sliderListener();
    private LibraryController collectionController = new LibraryController();
    private CollectionManagerController collectionManagerController = new CollectionManagerController();
    private ActionListener contextListener = new contextListener();
    private Integer row = null;
    private PlaylistView view = this;
    private JMenu contextAddPlaylist = null;

    private JFrame confirmationWindow = null;

    private JButton [] playBackButtons = new JButton[5];

    public PlaylistView(Player player)	{
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch(Exception ex) {
            ex.printStackTrace();
        }

        controller = new PlayerController(this, player);
        Playlist pl = (Playlist) player.getCollection();
        title = pl.getName();
        this.playlistWindow = new JFrame(title + " - Now Playing: Nothing");
        playlistWindow.setMinimumSize(new Dimension(480, 400));
        playlistWindow.setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\Svona\\Desktop\\Git\\mp3player\\res\\window-icon.png"));
        playlistWindow.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        playlistWindow.getContentPane().setLayout(new BorderLayout(0, 0));

        JPanel Interface = new JPanel();
        playlistWindow.getContentPane().add(Interface, BorderLayout.CENTER);

        LibraryTable = new JTable();
        LibraryTable.setModel(controller.getTableModelOfData());
        LibraryTable.setPreferredSize(new Dimension(200, 200));
        LibraryTable.setFillsViewportHeight(true);
        LibraryTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        LibraryTable.setRowSelectionAllowed(true);
        LibraryTable.setShowVerticalLines(false);

        MouseListener mouseListener = new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                row = LibraryTable.getSelectedRow();
                System.out.println("Selected index = " + row);
            }
        };

        LibraryTable.addMouseListener(mouseListener);

        JPopupMenu LibraryContext = new JPopupMenu();

        JMenuItem contextPlay = new JMenuItem("Play");
        contextPlay.addActionListener(this.contextListener);
        contextPlay.setActionCommand("play");
        LibraryContext.add(contextPlay);

        contextAddPlaylist = new JMenu("Add to Playlist");
        collectionManagerController.getPlaylistContexts(contextAddPlaylist, this.contextListener);
        LibraryContext.add(contextAddPlaylist);

        LibraryTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if(e.getButton() == e.BUTTON3)
                    LibraryContext.show(LibraryTable , e.getX(), e.getY());
            }
        });


        LibraryTable.add(LibraryContext);
        LibraryTable.setDropTarget(new PlaylistView.LibraryDrop());

        JScrollPane LibraryScroll = new JScrollPane();
        LibraryScroll.setPreferredSize(new Dimension(300, 200));
        LibraryScroll.setViewportBorder(null);
        LibraryScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        LibraryScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        LibraryScroll.setViewportView(LibraryTable);

        JPanel LibraryControls = new JPanel();

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

        this.playlistWindow.pack();

        JMenuBar menuBar = new JMenuBar();
        playlistWindow.setJMenuBar(menuBar);

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

        collectionManagerController.attachWindow(view);
    }

    public File addPopup() {
        JFileChooser selector = new JFileChooser("..");
        selector.setName("Please select a song to add to the library.");
        selector.setFileFilter(new FileNameExtensionFilter("MP3 Files", "mp3"));
        selector.setMultiSelectionEnabled(false);

        File song = null;
        if(selector.showOpenDialog(playlistWindow) == JFileChooser.APPROVE_OPTION) {
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

    public Integer getRow()	{
        return row;
    }

    public void display()	{
        playlistWindow.setVisible(true);
    }

    public void repaint()	{
        DefaultTableModel model = controller.getTableModelOfData();
        contextAddPlaylist.removeAll();
        collectionManagerController.getPlaylistContexts(contextAddPlaylist,this.contextListener);
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
                    break;
                case "remove":
                    controller.remove();
                    break;
                case "createPlaylist":
                    collectionManagerController.createPlaylist();
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
                case "addToPlaylist":
                    JMenuItem sourceObj = (JMenuItem) e.getSource();
                    System.out.println("Option selected was " + sourceObj.getText());
                    Song sng = controller.getSongFromIndex(row);
                    collectionManagerController.addSongToPlaylist(sng, sourceObj.getText());
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

    public class windowActionListener implements WindowListener  {
        @Override
        public void windowOpened(WindowEvent e) { }

        @Override
        public void windowClosing(WindowEvent e) {
            collectionManagerController.detachWindow(view);
        }

        @Override
        public void windowClosed(WindowEvent e) { }

        @Override
        public void windowIconified(WindowEvent e) { }

        @Override
        public void windowDeiconified(WindowEvent e) { }

        @Override
        public void windowActivated(WindowEvent e) { }

        @Override
        public void windowDeactivated(WindowEvent e) { }
    }

    public class LibraryDrop extends DropTarget {
        public void drop (DropTargetDropEvent evt) {
            try {
                evt.acceptDrop(DnDConstants.ACTION_COPY);

                //TODO Figure out what type this returns
                java.util.List result = (List) evt.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);

                for(Object o : result) {
                    System.out.println("Dropped file: "+o.toString());
                    controller.addViaPath(o.toString());
                    //Can't write specifics since how you set things up hasn't been pushed to the git.
                }
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
