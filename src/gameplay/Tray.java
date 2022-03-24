package gameplay;

import java.util.ArrayList;

public class Tray {
    private ArrayList<Tile> tray;

    public Tray(String letters) {
        this.tray = new ArrayList<>();
        for(int i = 0; i < letters.length(); i++) {
            tray.add(new Tile(letters.charAt(i)));
        }
    }

    public boolean contains(char c) {
        for (Tile tile:
             tray) {
            if (tile.getLetter() == c) {
                return true;
            }
            //TODO: Check if contains works with wild card.
            else if(tile.getLetter() == '*') {
                return true;
            }
        }

        return false;
    }

    public Tile get(char c) {
        //TODO: Check if get function works with wild cards.
        //First check if the tray contains a tile with the letter.
        for (Tile tile:
                tray) {
            if (tile.getLetter() == c) {
                return tile;
            }
        }
        //If the tray does not contain the exact letter but does contain a wild card. Use the wild card.
        for (Tile tile: tray) {
            if(tile.getLetter() == '*') {
                return tile;
            }
        }
        return null;
    }

    public void add(Tile tile) {
        tray.add(tile);
    }

    public void remove(Tile tile) {
        tray.remove(tile);
    }

    @Override
    public String toString() {
        StringBuilder temp = new StringBuilder("gameplay.Tray: ");
        for (Tile letter: tray) {
            temp.append(letter.toString());
        }

        return temp.toString();
    }

    public boolean isBingo() {
        if (tray.isEmpty()) {
            return true;
        }
        else {
            return false;
        }
    }
}
