public class Tile{
    private int wm;
    private int lm;
    private char placedLetter;

    public Tile(char wm, char lm) {
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

    public Tile(char letter) {
        this.wm = 1;
        this.lm = 1;

        this.placedLetter = letter;
    }
}