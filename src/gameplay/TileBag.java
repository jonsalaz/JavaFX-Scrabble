package gameplay;

import java.util.ArrayList;
import java.util.Collections;

public class TileBag {
    private ArrayList<Tile> tiles;

    public TileBag() {
        tiles = new ArrayList<>();
    }

    public void addTile(char c, int point, int freq) {
        Tile.addKey(c, point);
        for (int i = 0; i < freq; i++) {
            tiles.add(new Tile(c));
        }
    }

    public Tile draw() {
        Collections.shuffle(tiles);
        Tile tile = tiles.get(0);
        tiles.remove(tile);
        return tile;
    }
}
