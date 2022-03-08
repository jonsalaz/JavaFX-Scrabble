import java.io.File;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Necessary objects for game state parsing.
        Scanner fileScanner;
        File config;
        // Trie needed for word checking.
        Trie trie;


        // Freq List
        try{
            File file = new File("testing/freqLetter.txt");
            Scanner freqScanner = new Scanner(file);
            while(freqScanner.hasNextLine()){
                char c;
                int p;
                String line;

                line = freqScanner.nextLine();
                Tile.addKey(line.charAt(0), line.charAt(2));
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }

        // Trie Creation
        while(true) {
            try {
                trie = new Trie("testing/twl06.txt");
                break;
            } catch (FileNotFoundException e) {
                System.out.println("Dictionary config file not found");
            }
        }

        // User input for config file.
        Scanner scanner = new Scanner(System.in);
        while(true) {
            System.out.println("Please enter your tray config files");
            try {
                config = new File("testing/testConfig.txt");
                fileScanner = new Scanner(config);
                break;
            } catch (Exception e) {
                System.out.println("File not found");
            }
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
                System.out.println(fileScanner.next());
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
        }
    }
}
