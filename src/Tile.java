import java.util.HashMap;

public class Tile {
    private static HashMap<Character, Integer> points = new HashMap<>();
    private char letter;

    public Tile(char letter) {
        this.letter = letter;
    }

    public static void addKey(char c, int p) {
        points.put(c, p);
    }

    @Override
    public String toString() {
        return Character.toString(letter);
    }
}
