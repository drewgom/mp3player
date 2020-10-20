package models;

enum State  {
    PLAYING,
    PAUSED,
    IDLE
}
public class Player {
    private State state;
    private Song currentSong;
    private Integer timestamp;
    private Library library;


    public Player() {
        state = State.IDLE;
        library = new Library();
        library.getLibrary();
    }

    private void playNewSong(Song song)  {
        state = State.PLAYING;

    }

    private void pause()    {

    }

    private void stop() {

    }

    private void nextSong() {

    }

    private void previousSong() {

    }

    private void restartSong() {

    }
}