import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) {
        try {
            Trie trie = new Trie("testing/twl06.txt");
            System.out.println(trie);
        }
        catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
