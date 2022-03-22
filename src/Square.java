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
        //If the above and below squares are emtpy all characters are valid moves.
        if(board.getSquare(row-1, column).getPlacedLetter() == null
                && board.getSquare(row+1, column).getPlacedLetter() == null) {
            Arrays.fill(crossCheck, true);
            return crossCheck;
        }
        //It is necessary to check both above and below in case the square is "sandwiched" between two already complete
        //words.

        //If the below tile is not null, must search the binary tree for letters that work with the current tile
        //as our leading character of the word.
        if(board.getSquare(row+1,column).getPlacedLetter() != null) {
            Square temp = board.getSquare(row+1,column);
            StringBuilder word = new StringBuilder();
            while(temp.getPlacedLetter() != null) {
                word.append(temp.getPlacedLetter());
                temp = board.getSquare(temp.getRow()+1, column);
            }
            String finalWord = word.toString();

            for(int i = 0; i < 26; i++) {
                String key = ((char) (i+'a')) + finalWord;
                crossCheck[i] = trie.search(key);
            }
        }
        //If the above tile is not null, must search the binary tree for letters that work with the current tile as
        //our tail character of the word.
        if(board.getSquare(row-1, column).getPlacedLetter() != null) {
            Square temp = board.getSquare(row-1,column);
            StringBuilder word = new StringBuilder();
            while(temp.getPlacedLetter() != null) {
                word.insert(0, temp.getPlacedLetter());
                temp = board.getSquare(temp.getRow()-1, column);
            }
            String finalWord = word.toString();
            for(int i = 0; i < 26; i++) {
                String key = finalWord + ((char) (i+'a'));
                crossCheck[i] = trie.search(key);
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
}