package guiApplication;

import gameplay.Board;
import gameplay.TileBag;
import gameplay.Tray;
import gameplay.Trie;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

import java.io.InputStream;
import java.util.Scanner;


public class ScrabbleController {
    @FXML
    private TextField moveWord;
    private RadioButton downButton;
    private RadioButton acrossButton;

    private static TileBag tileBag;
    private static Trie trie;
    private static Board board;
    private static Tray tray;

    public static void initializeGame() {
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

        //Initialize players tray.
        tray = new Tray(tileBag);
    }

    public void playerMove() {
        moveWord.setText("PLAYER MOVE");
    }
}
