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
        }
        return false;
    }

    public Tile get(char c) {
        for (Tile tile:
                tray) {
            if (tile.getLetter() == c) {
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
        StringBuilder temp = new StringBuilder("Tray: ");
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
