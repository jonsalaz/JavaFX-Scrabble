/**
 * Author: Jonathan Salazar
 * ScrabbleController is the controller connected to all the FXML components allowing for user interaction with the GUI.
 */
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
import javafx.scene.text.Text;

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
    private Text computerScoreText;
    @FXML
    private Text playerScoreText;

    private Solver computerPlayer;
    private TileBag tileBag;
    private Trie trie;
    private Board board;
    private Tray computerTray;
    private Tray tray;
    private Square selected;
    private int playerScore;
    private int computerScore;


    /**
     * The initialization of all objects and display components for the start of the game including the board, trie
     * player scores, solver, tilebag, and trie. As well as any graphical components necessary.
     */
    public void initialize() {
        //Initialize tiles.
        playerScore = 0;
        computerScore = 0;
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
        board = new Board(dim, rows, trie);
        selected = board.getSquare(7, 7);
        updateBoard();

        //Initialize players tray.
        computerTray = new Tray(tileBag);
        tray = new Tray(tileBag);
        updateTray();

        this.computerPlayer = new Solver(board, computerTray, trie);
    }

    /**
     * Invoked when the player is ready to place their move, this function handles the execution of the player's move
     * as well as the proceeding move made by the computer player. After both players have had a chance to make a move
     * it calls on helper functions to update the display components of the GUI.
     */
    public void playerMove() {
        if(selected == null) {
            return;
        }
        int row = selected.getRow();
        int column = selected.getColumn();
        String word = moveWord.getText().toLowerCase().replace(" ", "");
        Boolean down = downButton.isSelected();

        if (board.checkIfLegal(word, row, column, down, tray, selected)) {
            int score = board.calculateScore(word, row, column, down, tray);
            playerScore += score;
            if(down) {
                int temp = row;
                row = column;
                column = temp;
            }
            board.playMove(word, tray, row, column, down);
        } else {
            return;
        }

        int size = tray.size();
        for(int i = 0; i < (7-size); i++) {
            Tile tile = tileBag.draw();
            if(tile == null) {
                if(checkGameEnd(tray)) {
                    return;
                }
            } else {
                tray.add(tile);
            }
        }

        selected = null;

        computerScore += computerPlayer.solve();

        size = computerTray.size();
        for(int i = 0; i < (7-size); i++) {
            Tile tile = tileBag.draw();
            if(tile == null) {
                if(checkGameEnd(computerTray)) {
                    return;
                }
            } else {
                computerTray.add(tile);
            }
        }

        updateScores(playerScore, computerScore);
        updateBoard();
        updateTray();
    }

    /**
     * A function that is invoked to allow the computer player to make a move when the player wants to pass their turn.
     */
    public void passTurn() {
        computerScore += computerPlayer.solve();

        int size = computerTray.size();
        for(int i = 0; i < (7-size); i++) {
            Tile tile = tileBag.draw();
            if(tile == null) {
                if(checkGameEnd(computerTray)) {
                    return;
                }
            } else {
                computerTray.add(tile);
            }
        }

        updateScores(playerScore, computerScore);
        updateBoard();
    }

    private Boolean checkGameEnd(Tray tray) {
        System.out.println("Game has ended.");
        if(tray.isBingo()) {
            for (Tile tile :
                    this.tray.getChildren()) {
                playerScore -= Tile.getScore(tile.getLetter());
            }

            for(Tile tile: this.computerTray.getChildren()) {
                computerScore -= Tile.getScore(tile.getLetter());
            }

            if(computerScore > playerScore) {
                this.playerScoreText.setText("COMPUTER PLAYER WINS!");
                this.computerScoreText.setText("WITH A SCORE OF " + computerScore);
            } else if(computerScore == playerScore) {
                this.playerScoreText.setText("IT'S A TIE");
                this.computerScoreText.setText("WITH A SCORE OF " + computerScore);
            } else {
                this.playerScoreText.setText("HUMAN PLAYER WINS!");
                this.computerScoreText.setText("WITH A SCORE OF " + playerScore);
            }
            return true;
        }
        return false;
    }

    private void updateScores(int playerScore, int computerScore) {
        this.playerScore = playerScore;
        this.computerScore = computerScore;

        this.playerScoreText.setText("Player Score: " + playerScore);
        this.computerScoreText.setText("Computer Score: " + computerScore);
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
