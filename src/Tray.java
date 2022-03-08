import java.util.ArrayList;

public class Tray {
    private ArrayList<Tile> tray;

    public Tray(String letters) {
        this.tray = new ArrayList<>();
        for(int i = 0; i < letters.length(); i++) {
            tray.add(new Tile(letters.charAt(i)));
        }
    }

    public ArrayList<Tile> getTray() {
        return tray;
    }

    @Override
    public String toString() {
        StringBuilder temp = new StringBuilder("Tray: ");
        for (Tile letter: tray) {
            temp.append(letter.toString());
        }

        return temp.toString();
    }
}
