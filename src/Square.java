import java.util.ArrayList;

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

    public ArrayList<Character> getCrossCheck() {
        ArrayList<Character> crossCheck = new ArrayList<>();
        //TODO: Determine CrossChecks either in function or precompute for each row as necessary.
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
}