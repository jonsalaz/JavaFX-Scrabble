package terminalApplication;
import gameplay.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Necessary objects for game state parsing.
        Scanner fileScanner;
        File config;
        // gameplay.Trie needed for word checking.
        Trie trie;

        // Freq List
        try{
            File file = new File("resources/scrabble_tiles.txt");
            Scanner freqScanner = new Scanner(file);
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
            trie = new Trie(args[1]);
        } catch (FileNotFoundException e) {
            System.out.println("Dictionary config file not found");
            System.exit(1);
            return;
        }

        // User input for config file.
        Scanner scanner = new Scanner(System.in);
        try {
            config = new File(scanner.next());
            fileScanner = new Scanner(config);
        } catch (Exception e) {
            System.out.println("Config File not found.");
            System.exit(1);
            return;
        }

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
