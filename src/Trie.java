import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Trie {
    private static final int ALPHABET_SIZE = 26;
    private final TrieNode ROOT = new TrieNode();

    private static class TrieNode {
        TrieNode[] children;
        boolean terminal;

        public TrieNode() {
            terminal = false;
            children = new TrieNode[ALPHABET_SIZE];
            for (int i = 0; i < ALPHABET_SIZE; i++) {
                children[i] = null;
            }
        }

        public void setTerminal() {this.terminal = true;}
    }

    public Trie(String dict) throws FileNotFoundException {
        File file = new File(dict);
        Scanner scanner = new Scanner(file);

        while (scanner.hasNextLine()) {
            String word = scanner.nextLine().toLowerCase();
            insert(word);
        }
        scanner.close();
    }

    private void insert(String word) {
        int index;
        int length = word.length();
        TrieNode current = ROOT;

        for (int level = 0; level < length; level ++) {
            //Adjusts ascii values for lower case letters to be 0-25 to match the index
            //"pointer array".
            index = word.charAt(level) - 97;
            if (current.children[index] == null) {
                current.children[index] = new TrieNode();
            }
            current = current.children[index];
        }
        //Set the last TrieNode in the word as a terminal node.
        current.setTerminal();
    }
}
