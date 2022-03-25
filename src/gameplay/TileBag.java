package gameplay;

import java.util.ArrayList;

public class TileBag {
    private ArrayList<Tile> tiles;

    public TileBag(char c, int point, int freq) {
        tiles = new ArrayList<>(100);
        Tile.addKey(c, point);
        for(int i = 0; i < freq; i++) {
            tiles.add(new Tile(c));
        }
    }
}
