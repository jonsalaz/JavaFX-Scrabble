public class TrieNode {
    private TrieNode[] children;
    private boolean terminal;

    public TrieNode() {
        terminal = false;
        int ALPHABET_SIZE = 26;
        children = new TrieNode[ALPHABET_SIZE];
        for (int i = 0; i < ALPHABET_SIZE; i++) {
            children[i] = null;
        }
    }

    public void setTerminal() {this.terminal = true;}

    public TrieNode[] getChildren() {
        return children;
    }

    public boolean isTerminal() {
        return terminal;
    }
}
