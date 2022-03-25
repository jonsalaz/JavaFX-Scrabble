package guiApplication;

import gameplay.*;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.InputStream;
import java.util.Scanner;


public class ScrabbleController {
    @FXML
    private HBox trayDisplay;
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
    private Tray computerTray;
    private Tray tray;
    private Square selected;


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
        computerTray = new Tray(tileBag);
        tray = new Tray(tileBag);
        updateTray();
    }

    public void playerMove() {
        moveWord.setText("PLAYER MOVE");
        int row = selected.getRow();
        int column = selected.getColumn();
        String word = moveWord.getText().toLowerCase();
        Boolean across = acrossButton.isFocused();

        //TODO: Check if it's a legal move.
        board.checkIfLegal(word, row, column, across, tray);
        //TODO: Calculate the score.
        //TODO: Play the move.

        updateBoard();
        updateTray();
    }

    private void updateBoard() {
        scrabbleGrid.getChildren().clear();
        for(int r = 0; r < board.getRowLength(); r++) {
            for(int c = 0; c < board.getColumnLength(); c++) {
                if(board.getSquare(r, c) == selected) {
                    Rectangle rectangle = new Rectangle();
                    rectangle.setHeight(20);
                    rectangle.setWidth(20);
                    rectangle.setFill(Color.RED);
                    scrabbleGrid.add(rectangle, c, r);
                    continue;
                }
                Node node = board.getSquare(r, c).toDisplay();
                int finalC = c;
                int finalR = r;
                node.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        setSelected(finalR, finalC);
                        updateBoard();
                    }
                });
                scrabbleGrid.add(node, c, r);
            }
        }
    }

    private void updateTray() {
        trayDisplay.getChildren().clear();
        for (Tile tile: tray.getChildren()) {
            trayDisplay.getChildren().add(tile.toDisplay());
        }
    }

    public void setSelected(int row, int column) {
        this.selected = board.getSquare(row, column);
    }
}
