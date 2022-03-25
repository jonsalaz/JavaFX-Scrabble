/**
 * Author: Jonathan Salazar
 * The tray object tracks the tiles in the tray as well as any functions needed to alter or check the tray.
 */
package gameplay;

import java.util.ArrayList;

public class Tray {
    private ArrayList<Tile> tray;

    /**
     * Constructor class for the tray.
     * @param letters A string of letters that the tray will contain on construction.
     */
    public Tray(String letters) {
        this.tray = new ArrayList<>();
        for(int i = 0; i < letters.length(); i++) {
            tray.add(new Tile(letters.charAt(i)));
        }
    }

    public Tray(TileBag tileBag) {
        this.tray = new ArrayList<>();
        for(int i = 0; i < 7; i++) {
            tray.add(tileBag.draw());
        }
    }

    /**
     * Checks whether the tray contains the currently letter or contains a wild card that can
     * be used as the letter.
     * @param c The character searched for.
     * @return A boolean representing whether the tray has a Tile that can be played as c.
     */
    public boolean contains(char c) {
        for (Tile tile:
             tray) {
            if (tile.getLetter() == c) {
                return true;
            }
            else if(tile.getLetter() == '*') {
                return true;
            }
        }
        return false;
    }

    /**
     * A getter to get the Tile that can be represented as c.
     * @param c The character that needs to be represented.
     * @return The tile that can represent c.
     */
    public Tile get(char c) {
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

    /**
     * Adds a tile to the tray.
     * @param tile The tile to be added to the tray.
     */
    public void add(Tile tile) {
        tray.add(tile);
    }

    /**
     * Removes a tile from the tray.
     * @param tile The tile to be removed from the tray.
     */
    public void remove(Tile tile) {
        tray.remove(tile);
    }

    /**
     * Handles representing a Tile as a string.
     * @return String representing the tile.
     */
    @Override
    public String toString() {
        StringBuilder temp = new StringBuilder("Tray: ");
        for (Tile letter: tray) {
            temp.append(letter.toString());
        }
        return temp.toString();
    }

    /**
     * Checks whether the player has bingo on the placement of a word.
     * @return A boolean representing whether a player has bingo.
     */
    public boolean isBingo() {
        return tray.isEmpty();
    }
}
