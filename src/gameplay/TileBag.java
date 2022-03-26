/**
 * Author: Jonathan Salazar
 * TileBag contains all the tiles necessary for a single game of scrabble.
 */
package gameplay;

import java.util.ArrayList;
import java.util.Collections;

public class TileBag {
    private ArrayList<Tile> tiles;

    /**
     * Constructor class for the tile bag.
     */
    public TileBag() {
        tiles = new ArrayList<>();
    }

    /**
     * Adds a new tile to the bag given a character, how many points the character is worth and the frequency of the
     * character.
     * @param c The character to be put on the tile.
     * @param point The points that the character c is worth.
     * @param freq The frequency that c appears on tiles.
     */
    public void addTile(char c, int point, int freq) {
        Tile.addKey(c, point);
        for (int i = 0; i < freq; i++) {
            tiles.add(new Tile(c));
        }
    }

    /**
     * A getter function to draw a tile from the TileBag.
     * @return A tile that is drawn from the tileBag. Returns null if the TileBag is empty.
     */
    public Tile draw() {
        if(tiles.isEmpty()) {
            return null;
        }
        Collections.shuffle(tiles);
        Tile tile = tiles.get(0);
        tiles.remove(tile);
        return tile;
    }
}
