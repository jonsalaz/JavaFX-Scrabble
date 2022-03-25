/**
 * Author: Jonathan Salazar
 * The Square object represents a single square on the scrabble board which contains data
 * on the position, scoring multipliers, and currently placed Tile.
 */
package gameplay;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.Arrays;

public class Square {
    private int row;
    private int column;
    private int wm;
    private int lm;
    private Tile placedLetter;

    /**
     * Constructor class for Square.
     * @param wm The word multiplier of the square.
     * @param lm The letter multiplier of the square.
     * @param row The square's row.
     * @param column The square's column.
     */
    public Square(char wm, char lm, int row, int column) {
        if(wm == '.'){
            this.wm = 1;
        }
        else {
            this.wm = Character.getNumericValue(wm);
        }

        if(lm == '.' || lm == ' ') {
            this.lm = 1;
        }
        else {
            this.lm = Character.getNumericValue(lm);
        }
        this.placedLetter = null;
        this.row = row;
        this.column = column;
    }

    /**
     * Constructor class for Square.
     * @param letter The letter on the square.
     * @param row The square's row.
     * @param column The square's column.
     */
    public Square(char letter, int row, int column) {
        this.wm = 1;
        this.lm = 1;

        this.placedLetter = new Tile(letter);
        this.row = row;
        this.column = column;
    }

    /**
     * Getter for the currently placed tile.
     * @return The tile that is currently placed.
     */
    public Tile getPlacedLetter() {
        return placedLetter;
    }

    /**
     * Getter for the word multiplier of the square.
     * @return The word multiplier of the square.
     */
    public int getWordMultiplier() {
        return wm;
    }

    /**|
     * Getter for the letter multiplier of the square.
     * @return The letter multiplier of the square.
     */
    public int getLetterMultiplier() {
        return lm;
    }

    /**
     * Determines the crosschecks for the square given the current state of the board and the dictionary
     * currently being used.
     * @param board A reference to the board.
     * @param trie A reference to the Trie dictionary.
     * @return A boolean array representing whether a given letter is playable in the square.
     */
    public Boolean[] getCrossCheck(Board board, Trie trie) {
        Boolean[] crossCheck = new Boolean[26];

        if(this.row == 0) {
            if(board.getSquare(row + 1, column).getPlacedLetter() == null) {
                Arrays.fill(crossCheck, true);
            } else {
                //Build string out of already placed letters below the current square.
                StringBuilder alreadyPlaced = new StringBuilder();
                for (int i = row + 1; i < board.getRowLength(); i++) {
                    if (board.getSquare(i, column).getPlacedLetter() == null) {
                        break;
                    } else {
                        alreadyPlaced.append(board.getSquare(i, column).getPlacedLetter().getLetter());
                    }
                }
                //Check every letter combination combined with the prexisting letters below the square to create a list of
                // letters that can be placed on the current square.
                for (int i = 0; i < 26; i++) {
                    char currentLetter = (char) (i + 'a');
                    if (crossCheck[i] == null || crossCheck[i]) {
                        String searchWord = currentLetter + alreadyPlaced.toString();
                        crossCheck[i] = trie.search(searchWord);
                    }
                }
            }
        } else if(this.row == board.getRowLength()-1) {
            //Build word out previously placed letters from above.
            StringBuilder alreadyPlaced = new StringBuilder();
            if(board.getSquare(row - 1, column).getPlacedLetter() == null) {
                Arrays.fill(crossCheck, true);
            } else {
                for (int i = row - 1; i >= 0; i--) {
                    if (board.getSquare(i, column).getPlacedLetter() == null) {
                        break;
                    } else {
                        alreadyPlaced.insert(0, board.getSquare(i, column).getPlacedLetter().getLetter());
                    }
                }

                for (int i = 0; i < 26; i++) {
                    char currentLetter = (char) (i + 'a');
                    if (crossCheck[i] == null || crossCheck[i]) {
                        String searchWord = alreadyPlaced.toString() + currentLetter;
                        crossCheck[i] = trie.search(searchWord);
                    }
                }
            }
        } else {
            if(board.getSquare(row-1, column).getPlacedLetter() == null
                    && board.getSquare(row+1, column).getPlacedLetter() == null) {
                Arrays.fill(crossCheck, true);
            } else {
                StringBuilder firstHalf = new StringBuilder();
                StringBuilder secondHalf = new StringBuilder();
                //Build first half of word.
                for (int i = row - 1; i >= 0; i--) {
                    if (board.getSquare(i, column).getPlacedLetter() == null) {
                        break;
                    } else {
                        firstHalf.insert(0, board.getSquare(i, column).getPlacedLetter().getLetter());
                    }
                }

                for (int i = row + 1; i < board.getRowLength(); i++) {
                    if (board.getSquare(i, column).getPlacedLetter() == null) {
                        break;
                    } else {
                        secondHalf.append(board.getSquare(i, column).getPlacedLetter().getLetter());
                    }
                }

                for(int i = 0; i < 26; i++) {
                    char currentLetter = (char) (i+ 'a');
                    String searchWord = firstHalf.toString() + currentLetter + secondHalf.toString();
                    searchWord = searchWord.toLowerCase();
                    if(crossCheck[i] == null || crossCheck[i]) {
                        crossCheck[i] = trie.search(searchWord);
                    }
                }
            }
        }
        return crossCheck;
    }

    /**
     * Determines the representation of the square as a string.
     * @return The string representing the square.
     */
    @Override
    public String toString() {
        if (placedLetter != null) {
            return " " + placedLetter;
        }
        else {
            String temp = "";

            if (wm == 1) {
                temp += ".";
            }
            else {
                temp += Integer.toString(wm);
            }

            if (lm == 1) {
                temp += ".";
            }
            else {
                temp += Integer.toString(lm);
            }

            return temp;
        }
    }

    /**
     * Getter for the column of the square.
     * @return The column of the square.
     */
    public int getColumn() {
        return column;
    }

    /**
     * Getter for the row of the square.
     * @return The row of the square.
     */
    public int getRow() {
        return row;
    }

    /**
     * Updates the current square by swapping the row and the column values when the board is transposed.
     */
    public void transpose() {
        int temp = this.row;
        this.row = this.column;
        this.column = temp;
    }

    /**
     * Setter for the current placed Tile.
     * @param tile The tile to be placed on the square.
     */
    public void setPlacedLetter(Tile tile) {
        this.placedLetter = tile;
    }

    public Rectangle toDisplay() {
        Rectangle rectangle = new Rectangle();
        rectangle.setHeight(20);
        rectangle.setWidth(20);
        if(placedLetter == null) {
            rectangle.setFill(Color.BLUE);
            return rectangle;
        }
        else {
            rectangle.setFill(Color.BLUE);
            return rectangle;
        }
    }
}