import java.util.ArrayList;
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

    public Boolean[] getCrossCheck(Board board) {
        Boolean[] crossCheck = new Boolean[26];
        //TODO: Determine CrossChecks either in function or precompute for each row as necessary.

        if(board.getSquare(row-1, column).getPlacedLetter() == null
                && board.getSquare(row+1, column).getPlacedLetter() == null) {
            Arrays.fill(crossCheck, true);
            return crossCheck;
        }
        else if(board.getSquare(row-1,column).getPlacedLetter() != null) {
            //TODO: Accoutn for crosschecks when the above is not empty.
        }
        else if(board.getSquare(row+1, column).getPlacedLetter() != null) {
            //TODO: Account for crosschecks when the below is not empty.
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