import java.util.HashMap;

public class Tile {
    private static HashMap<Character, Integer> points = new HashMap<>();
    private char letter;

    public Tile(char letter) {
        this.letter = letter;
    }

    public static void addKey(char c, int p) {
        points.put(Character.toLowerCase(c), p);
    }

    public char getLetter() {
        return letter;
    }

    public static int getScore(char c) {
        //TODO: A character's value is 0 if it is a freshly placed wild card but not if it's a preplaced wildcard.
        if(Character.isUpperCase(c)) {
            return 0;
        }
        return points.get(c);
    }

    public void setLetter(char c) {
        letter = c;
    }

    @Override
    public String toString() {
        return Character.toString(letter);
    }
}
