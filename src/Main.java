import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Trie trie;
        Board board;
        Scanner scanner = new Scanner(System.in);
        try {
            trie = new Trie("testing/twl06.txt");
            System.out.println(trie);
        }
        catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        try{
            System.out.println("Please enter the name of your board configuration file.");
            File file = new File(scanner.next());
            board = new Board(file);
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
    }
}
