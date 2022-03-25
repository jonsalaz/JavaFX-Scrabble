package gameplay;

import java.util.ArrayList;
import java.util.Random;

public class TileBag {
    private ArrayList<Tile> tiles;

    public TileBag() {
        tiles = new ArrayList<>(100);
    }

    public void addTile(char c, int point, int freq) {
        Tile.addKey(c, point);
        for (int i = 0; i < freq; i++) {
            tiles.add(new Tile(c));
        }
    }

    public Tile draw() {
        return tiles.get(new Random().nextInt(tiles.size()));
    }
}
