package terminalApplication;
import gameplay.*;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // gameplay.Trie needed for word checking.
        Trie trie;

        // Freq List
        try{
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            InputStream is = classLoader.getResourceAsStream("scrabble_tiles.txt");
            Scanner freqScanner = new Scanner(is);
            while(freqScanner.hasNextLine()){
                String line;

                line = freqScanner.nextLine();
                Tile.addKey(line.charAt(0), Integer.parseInt(line.substring(2, 3)));
            }
        }
        catch(Exception e){
            e.printStackTrace();
            System.exit(1);
            return;
        }

        // gameplay.Trie Creation
        try {
            trie = new Trie(args[0]);
        } catch (FileNotFoundException e) {
            System.out.println("Dictionary config file not found");
            System.exit(1);
            return;
        }


        // User input for config file.
        Scanner fileScanner = new Scanner(System.in);
        // Perform solving for every game state given in config file.
        while(fileScanner.hasNextLine()) {
            // Necessary objects to run solver.
            Board board;
            Tray tray;
            int dim;

            // Parse file for dimensions.
            try {
                dim = fileScanner.nextInt();
            }
            catch(InputMismatchException e) {
                e.printStackTrace();
                System.exit(1);
                return;
            }

            // Parse file for row values.
            String[] rows = new String[dim];
            fileScanner.nextLine();
            for(int i = 0; i < dim; i++) {
                rows[i] = fileScanner.nextLine();
            }

            // Create Necessary Objects for solver.
            board = new Board(dim, rows);
            tray = new Tray(fileScanner.nextLine());

            Solver solver = new Solver(board, tray, trie);

            System.out.println("Input Board:");
            System.out.println(board);
            System.out.println(tray);
            solver.solve();
            System.out.println("Solution Board:");
            System.out.println(board);
            System.out.println();
        }
    }
}
