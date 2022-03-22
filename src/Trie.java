import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Trie {
    private final TrieNode ROOT = new TrieNode();

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
        word = word.replace("[^A-Za-z]+", "");
        TrieNode current = ROOT;

        for (int level = 0; level < length; level ++) {
            //Adjusts ascii values for lower case letters to be 0-25 to match the indexing
            //for children of the current node.
            index = word.charAt(level) - 97;
            if (current.getChildren()[index] == null) {
                current.getChildren()[index] = new TrieNode();
            }
            current = current.getChildren()[index];
        }
        //Set the last TrieNode in the word as a terminal node.
        current.setTerminal();
    }

    public Boolean search(String word) {
        int length = word.length();
        TrieNode nextNode = ROOT;

        for(int level = 0; level < length; level++) {
            int index = word.charAt(level) - 'a';
            if(nextNode.getChildren()[index] == null) {
                return false;
            }
            else{
                nextNode = nextNode.getChildren()[index];
            }
        }
        return nextNode.isTerminal();
    }

    private TrieNode getRoot() {
        return ROOT;
    }
}
