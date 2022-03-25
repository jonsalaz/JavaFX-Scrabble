package guiApplication;

import gameplay.Board;
import gameplay.TileBag;
import gameplay.Tray;
import gameplay.Trie;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.io.InputStream;
import java.util.Scanner;


public class ScrabbleController {
    @FXML
    private TextField moveWord;
    @FXML
    private GridPane scrabbleGrid;
    @FXML
    private RadioButton downButton;
    @FXML
    private RadioButton acrossButton;

    private TileBag tileBag;
    private Trie trie;
    private Board board;
    private Tray tray;

    public void initialize() {
        //Initialize tiles.
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream is = classLoader.getResourceAsStream("scrabble_tiles.txt");
        Scanner freqScanner = new Scanner(is);
        tileBag = new TileBag();
        while(freqScanner.hasNextLine()) {
            String line;
            line = freqScanner.nextLine();
            tileBag.addTile(line.charAt(0),
                    Integer.parseInt(line.substring(2, 4).replace(" ", "")),
                    Integer.parseInt(line.substring(4).replace(" ", "")));
        }

        //Initialize Dictionary
        is = classLoader.getResourceAsStream("sowpods.txt");
        trie = new Trie(is);

        //Initialize Board
        is = classLoader.getResourceAsStream("scrabble_board.txt");
        Scanner boardScanner = new Scanner(is);
        int dim = boardScanner.nextInt();
        String[] rows = new String[dim];
        boardScanner.nextLine();
        for(int i = 0; i < dim; i++) {
            rows[i] = boardScanner.nextLine();
        }
        board = new Board(dim, rows);
        updateBoard();

        //Initialize players tray.
        tray = new Tray(tileBag);
    }

    public void playerMove() {
        moveWord.setText("PLAYER MOVE");
        updateBoard();
    }

    private void updateBoard() {
        GridPane tempGrid = new GridPane();
        for(int r = 0; r < board.getRowLength(); r++) {
            for(int c = 0; c < board.getColumnLength(); c++) {
                tempGrid.add(board.getSquare(r, c).toDisplay(), c, r);
            }
        }
        scrabbleGrid = tempGrid;
    }
}
