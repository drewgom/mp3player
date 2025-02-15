package models;

import javazoom.jlgui.basicplayer.BasicPlayer;
import javazoom.jlgui.basicplayer.BasicPlayerException;

import java.io.File;
import java.util.ArrayList;

public class Player {
    private BasicPlayer basePlayer;
    private State state;
    private Song currentSong;
    private SongCollection collection;


    public Player(SongCollection collection) {
        state = State.IDLE;
        basePlayer = new BasicPlayer();
        this.collection = collection;
    }

    public void play(Song song)  {
        try {
            if (currentSong != null) {
                stop();
            }
            basePlayer.open(new File(song.getPath()).getAbsoluteFile());
            basePlayer.play();
            currentSong = song;
            state = State.PLAYING;
        } catch (Exception e)   {
            System.out.println(e.getMessage());
        }
    }

    public void pause()    {
        try {
            if (currentSong != null) {
                basePlayer.pause();
                state = State.PAUSED;
            } else  {
                throw new IllegalStateException("Current song is null but the player attempted to pause the current song");
            }
        } catch (Exception e)   {
            System.out.println(e.getMessage());
        }
    }

    public void play()  {
        try {
            if (currentSong != null) {
                basePlayer.resume();
                state = State.PLAYING;
            } else  {
                throw new IllegalStateException("Current song is null but the player attempted to resume the current song");
            }
        } catch (Exception e)   {
            System.out.println(e.getMessage());
        }
    }

    public void stop() {
        try {
            basePlayer.stop();
            currentSong = null;
            state = State.IDLE;
        } catch (Exception e)   {
            System.out.println(e.getMessage());
        }
    }

    public void next() {
        try {
            ArrayList<Song> songs = collection.getSongsInCollection();
            Song newSong = null;
            if (currentSong == null) {
                newSong = songs.get(0);
            } else {
                for (int i = 0; i < songs.size(); i++) {
                    if ((songs.get(i).getPk() == currentSong.getPk()) && (i+1 != songs.size())) {
                        newSong = songs.get(i+1);
                    } else if (songs.get(i).getPk() == currentSong.getPk() && i+1 == songs.size())  {
                        newSong = songs.get(0);
                    }
                }
                if (newSong == null)    {
                    newSong = songs.get(0);
                }
            }
            if (newSong != null) {
                play(newSong);
            } else {
                throw new IllegalStateException("No next song could be selected for some reason");
            }
        } catch(Exception e)    {
            System.out.println(e.getMessage());
        }
    }

    public void previous() {
        try {
            ArrayList<Song> songs = collection.getSongsInCollection();
            Song newSong = null;
            if (currentSong == null) {
                newSong = songs.get(0);
            } else {
                for (int i = 0; i < songs.size(); i++) {
                    if ((songs.get(i).getPk() == currentSong.getPk()) && (i-1 > 0)) {
                        newSong = songs.get(i-1);
                    } else if (songs.get(i).getPk() == currentSong.getPk() && i == 0) {
                        newSong = songs.get(songs.size()-1);
                    }
                }
                if (newSong == null)    {
                    newSong = songs.get(0);
                }
            }
            if (newSong != null) {
                play(newSong);
            } else {
                throw new IllegalStateException("No next song could be selected for some reason");
            }
        } catch(Exception e)    {
            System.out.println(e.getMessage());
        }
    }

    public void updateVolume(Double gain)    {
        try {
            basePlayer.setGain(gain);
        } catch(BasicPlayerException e)   {
            System.out.println("BasicPlayerException");
            System.out.println(e.getMessage());
        } catch(Exception e)   {
            System.out.println("Exception thrown");
            System.out.println(e.getMessage());
        }
    }

    public State getState() {
        return state;
    }

    public SongCollection getCollection() {
        return collection;
    }

    public void setCollection(SongCollection sc) {
        collection = sc;
    }

    public Song getCurrentSong() {
        return currentSong;
    }
}