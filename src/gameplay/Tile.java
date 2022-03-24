/**
 * Author: Jonathan Salazar
 * The Tile object holds information pertaining to the score of the Tile and the letter on the Tile.
 */
package gameplay;

import java.util.HashMap;

public class Tile {
    private static HashMap<Character, Integer> points = new HashMap<>();
    private char letter;

    /**
     * Constructor class for the Tile.
     * @param letter The letter to be on the tile.
     */
    public Tile(char letter) {
        this.letter = letter;
    }

    /**
     * Adds a key and value to the points ArrayList.
     * @param c The character key.
     * @param p The value of the character.
     */
    public static void addKey(char c, int p) {
        points.put(Character.toLowerCase(c), p);
    }

    /**
     * Returns the letter on the tile.
     * @return The letter on the tile.
     */
    public char getLetter() {
        return letter;
    }

    /**
     * Determines the score of a given letter.
     * @param c The letter whose score needs to be determined.
     * @return The value of the letter.
     */
    public static int getScore(char c) {
        if(Character.isUpperCase(c)) {
            return 0;
        }
        return points.get(c);
    }

    /**
     * A setter for the letter on the Tile.
     * @param c The character for the tile to represent.
     */
    public void setLetter(char c) {
        letter = c;
    }

    /**
     * Handles the string representation of a Tile.
     * @return The string representation of the tile.
     */
    @Override
    public String toString() {
        return Character.toString(letter);
    }
}
