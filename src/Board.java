import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Board {
    private Tile[][] board;

    public Board(File file) throws FileNotFoundException {
        Scanner scanner = new Scanner(file);
        int dim = scanner.nextInt();
        board = new Tile[dim][dim];

        for(int i = 0; i < dim; i++) {
            String line = scanner.nextLine();
            String[] row = line.split("\\r+");

            int column = 0;
            for (String tile: row) {
                if(tile.length() == 2) {
                    board[i][column] = new Tile(tile.charAt(0), tile.charAt(1));
                }
                else {
                    board[i][column] = new Tile(tile.charAt(0));
                }
            }
        }
    }

    private class Tile{
        private int wm;
        private int lm;
        private char placedLetter;

        private Tile(char wm, char lm) {
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

        private Tile(char letter) {
            this.wm = 1;
            this.lm = 1;

            this.placedLetter = letter;
        }
    }
}
