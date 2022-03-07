import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Tray {
    Tile[] tray;

    public Tray(File file) throws FileNotFoundException {
        Scanner scanner = new Scanner(file);
        String letters = scanner.next();
        for(int i = 0; i < letters.length(); i++) {
            tray[i] = new Tile(letters.charAt(i));
        }
    }
}
