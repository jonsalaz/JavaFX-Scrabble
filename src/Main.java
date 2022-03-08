import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Trie trie;

        File config;
        Scanner fileScanner;

        Scanner scanner = new Scanner(System.in);

        while(true) {
            try {
                trie = new Trie("testing/twl06.txt");
                break;
            } catch (FileNotFoundException e) {
                System.out.println("Dictionary config file not found");
            }
        }

        while(true) {
            System.out.println("Please enter your tray config files");
            try {
                config = new File(scanner.nextLine());
                fileScanner = new Scanner(config);
                break;
            } catch (Exception e) {
                System.out.println("File not found");
            }
        }

        while(fileScanner.hasNextLine()) {
            // Necessary objects to run solver.
            Board board;
            Tray tray;
            int dim;

            // Parse file for dimensions.
            dim = fileScanner.nextInt();

            // Parse file for row values.
            String[] rows = new String[dim];
            for(int i = 0; i < dim; i++) {
                rows[i] = fileScanner.nextLine();
            }

            // Create Necessary Objects for solver.
            board = new Board(dim, rows);
            tray = new Tray(scanner.nextLine());

            Solver solver = new Solver(board, tray, trie);

            solver.solve();
        }
    }
}
