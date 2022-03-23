import java.util.Arrays;

public class Square {
    private int row;
    private int column;
    private int wm;
    private int lm;
    private Tile placedLetter;

    public Square(char wm, char lm, int row, int column) {
        if(wm == '.'){
            this.wm = 1;
        }
        else {
            this.wm = Character.getNumericValue(wm);
        }

        if(lm == '.') {
            this.lm = 1;
        }
        else {
            this.lm = Character.getNumericValue(lm);
        }
        this.placedLetter = null;
        this.row = row;
        this.column = column;
    }

    public Square(char letter, int row, int column) {
        this.wm = 1;
        this.lm = 1;

        this.placedLetter = new Tile(letter);
        this.row = row;
        this.column = column;
    }

    public Tile getPlacedLetter() {
        return placedLetter;
    }

    public int getWordMultiplier() {
        return wm;
    }

    public int getLetterMultiplier() {
        return lm;
    }

    public Boolean[] getCrossCheck(Board board, Trie trie) {
        Boolean[] crossCheck = new Boolean[26];
        //If the above and below squares are empty all characters are valid moves.
        Arrays.fill(crossCheck, true);
        //It is necessary to check both above and below in case the square is "sandwiched" between two already complete
        //words.

        //If the below tile is not null, must search the binary tree for letters that work with the current tile
        //as our leading character of the word.
        if(row != board.getRowLength()-1) {
            Square temp = board.getSquare(row + 1, column);
            if (temp.getPlacedLetter() != null) {
                StringBuilder word = new StringBuilder();
                while (temp.getPlacedLetter() != null) {
                    word.append(temp.getPlacedLetter());
                    try {
                        temp = board.getSquare(temp.getRow() + 1, column);
                    } catch (IndexOutOfBoundsException e) {
                        break;
                    }
                }
                String finalWord = word.toString();

                for (int i = 0; i < 26; i++) {
                    String key = ((char) (i + 'a')) + finalWord;
                    crossCheck[i] = trie.search(key.toLowerCase());
                }
            }
        }
        //If the above tile is not null, must search the binary tree for letters that work with the current tile as
        //our tail character of the word.
        if(row != 0) {
            if (board.getSquare(row - 1, column).getPlacedLetter() != null) {
                Square temp = board.getSquare(row - 1, column);
                StringBuilder word = new StringBuilder();
                while (temp.getPlacedLetter() != null) {
                    word.insert(0, temp.getPlacedLetter());
                    try {
                        temp = board.getSquare(temp.getRow() - 1, column);
                    } catch (IndexOutOfBoundsException e) {
                        break;
                    }
                }
                String finalWord = word.toString();
                for (int i = 0; i < 26; i++) {
                    String key = finalWord + ((char) (i + 'a'));
                    crossCheck[i] = trie.search(key.toLowerCase());
                }
            }
        }

        if(column != 0) {
            if(board.getSquare(row, column - 1).getPlacedLetter() != null) {
                Square temp = board.getSquare(row, column-1);
                StringBuilder word = new StringBuilder();
                while(temp.getPlacedLetter() != null) {
                    word.insert(0, temp.getPlacedLetter());
                    try {
                        temp = board.getSquare(temp.getRow(), temp.getColumn() - 1);
                    } catch (IndexOutOfBoundsException e) {
                        break;
                    }
                }
                String finalWord = word.toString();
                for (int i = 0; i < 26; i++) {
                    String key = finalWord + ((char) (i + 'a'));
                    crossCheck[i] = trie.search(key.toLowerCase());
                }
            }
        }

        if(column != board.getColumnLength()-1) {
            if(board.getSquare(row, column + 1).getPlacedLetter() != null) {
                Square temp = board.getSquare(row, column + 1);
                StringBuilder word = new StringBuilder();
                while(temp.getPlacedLetter() != null) {
                    word.append(temp.getPlacedLetter());
                    try {
                        temp = board.getSquare(temp.getRow(), temp.getColumn() + 1);
                    } catch (IndexOutOfBoundsException e) {
                        break;
                    }
                }
                String finalWord = word.toString();
                for (int i = 0; i < 26; i++) {
                    String key = finalWord + ((char) (i + 'a'));
                    crossCheck[i] = trie.search(key.toLowerCase());
                }
            }
        }
        return crossCheck;
    }

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

    public int getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }

    public void transpose() {
        int temp = this.row;
        this.row = this.column;
        this.column = temp;
    }

    public void setPlacedLetter(Tile tile) {
        this.placedLetter = tile;
    }
}