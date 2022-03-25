/**
 * Author: Jonathan Salazar
 * The Trie object is used as a representation of a dictionary to determine what constitutes a valid word.
 */
package gameplay;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

public class Trie {
    private final TrieNode ROOT = new TrieNode();

    /**
     * A constructor for the Trie.
     * @param dict The filename of the dictionary to be used.
     * @throws FileNotFoundException
     */
    public Trie(String dict) throws FileNotFoundException {
        File file = new File(dict);
        Scanner scanner = new Scanner(file);

        while (scanner.hasNextLine()) {
            String word = scanner.nextLine().toLowerCase();
            insert(word);
        }
        scanner.close();
    }

    /**
     * Constructor for Trie given an InputStream.
     * @param dict An input stream of the txt file with all valid words.
     * */
    public Trie(InputStream dict) {
        Scanner scanner = new Scanner(dict);

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
        //Set the last gameplay.TrieNode in the word as a terminal node.
        current.setTerminal();
    }

    /**
     * Checks if the given string is a valid word in the Trie.
     * @param word the word to be checked.
     * @return A boolean representing whether the word is valid.
     */
    public Boolean search(String word) {
        word = word.toLowerCase();
        int length = word.length();
        TrieNode nextNode = ROOT;
        for(int level = 0; level < length; level++) {
            int index = Character.toLowerCase(word.charAt(level)) - 'a';
            if(nextNode.getChildren()[index] == null) {
                return false;
            }
            else{
                nextNode = nextNode.getChildren()[index];
            }
        }
        return nextNode.isTerminal();
    }
    public TrieNode getRoot() {
        return ROOT;
    }
}
