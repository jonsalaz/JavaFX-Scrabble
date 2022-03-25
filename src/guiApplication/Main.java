package guiApplication;

import gameplay.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.InputStream;
import java.util.Scanner;

public class Main extends Application {
    private static Board board;
    private static Trie trie;
    private static TileBag tileBag;

    public static void main(String[] args) {
        //Initialize initialize tile bag.
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream is = classLoader.getResourceAsStream("scrabble_tiles.txt");
        Scanner freqScanner = new Scanner(is);
        while(freqScanner.hasNextLine()) {
            String line;
            line = freqScanner.nextLine();
            tileBag = new TileBag(line.charAt(0),
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

        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/scrabble.fxml"));
        primaryStage.setTitle("Scrabble!");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}
