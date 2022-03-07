import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Board {
    private Tile[][] board;

    public Board(File file) throws FileNotFoundException {
        //TODO: Readjust parsing in order to take in multiple configs in main
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
}
