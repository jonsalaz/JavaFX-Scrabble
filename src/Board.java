import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Board {
    private Tile[][] board;

    public Board(int dim, String[] rows) {
        //TODO: Readjust parsing in order to take in multiple configs in main
        board = new Tile[dim][dim];
    }
}
