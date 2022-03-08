public class Square {
    private int wm;
    private int lm;
    private char placedLetter;

    public Square(char wm, char lm) {
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
        this.placedLetter = ' ';
    }

    public Square(char letter) {
        this.wm = 1;
        this.lm = 1;

        this.placedLetter = letter;
    }

    @Override
    public String toString() {
        if (placedLetter != ' ') {
            return " " + Character.toString(placedLetter);
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