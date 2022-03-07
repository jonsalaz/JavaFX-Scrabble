import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Tray {
    private ArrayList<Tile> tray;

    public Tray(String letters) throws FileNotFoundException {
        this.tray = new ArrayList<>();
        for(int i = 0; i < letters.length(); i++) {
            tray.add(new Tile(letters.charAt(i)));
        }
    }

    public ArrayList<Tile> getTray() {
        return tray;
    }
}
