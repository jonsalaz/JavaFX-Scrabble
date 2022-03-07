import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Tray {
    Tile[] tray;

    public Tray(String letters) throws FileNotFoundException {
        tray = new Tile[letters.length()];
        for(int i = 0; i < letters.length(); i++) {
            tray[i] = new Tile(letters.charAt(i));
        }
    }
}
