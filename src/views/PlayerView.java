package views;

import java.io.File;

public abstract class PlayerView {
    public abstract void repaint();

    public abstract void setPause();

    public abstract void setPlay();

    public abstract void refreshSelected(Integer row);

    public abstract File addPopup();

    public abstract int getSelectedIndex();

    public abstract int[] getTableRows();
}