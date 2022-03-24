/**
 * Author: Jonathan Salazar
 * The TrieNode object is used to represent a valid letter and contains edges to letters that are valid extensions of
 * the current path.
 */
package gameplay;

public class TrieNode {
    private TrieNode[] children;
    private boolean terminal;

    /**
     * Constructor class for TrieNode.
     */
    public TrieNode() {
        terminal = false;
        int ALPHABET_SIZE = 26;
        children = new TrieNode[ALPHABET_SIZE];
        for (int i = 0; i < ALPHABET_SIZE; i++) {
            children[i] = null;
        }
    }

    /**
     * Sets the current node as a terminal node.
     */
    public void setTerminal() {this.terminal = true;}

    /**
     * Gets the edges of the current node.
     * @return A TrieNode array representing the edges of the node.
     */
    public TrieNode[] getChildren() {
        return children;
    }

    /**
     * Determines whether a node is terminal.
     * @return A boolean representing whether a node is terminal.
     */
    public boolean isTerminal() {
        return terminal;
    }
}
