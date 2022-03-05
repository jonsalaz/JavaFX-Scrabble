public class Trie {
    private static final int ALPHABET_SIZE = 26;
    public static TrieNode root;

    static class TrieNode {
        TrieNode[] children = new TrieNode[ALPHABET_SIZE];
        boolean terminal;

        public TrieNode() {
            terminal = false;
            for (int i = 0; i < ALPHABET_SIZE; i++) {
                children[i] = null;
            }
        }
    }
}
