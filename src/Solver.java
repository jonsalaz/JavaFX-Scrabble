public class Solver {
    private Board board;
    private Tray tray;
    private Trie trie;

    public Solver(Board board, Tray tray, Trie trie) {
        this.board = board;
        this.tray = tray;
        this.trie = trie;
    }

    public void solve() {
        // TODO: solve
    }

    private void leftPart(String partialWord, TrieNode N, int limit, Square anchorSquare){
        extendRight(partialWord, N, anchorSquare);
        if(limit > 0) {
            for(int i = 0; i < N.getChildren().length; i++) {
                if ((N.getChildren()[i] == null) && tray.contains((char) (i+97))) {
                    Tile l = tray.get((char) (i+97));
                    TrieNode newN = N.getChildren()[i];
                    leftPart(partialWord + l.getLetter(), newN, limit - 1, anchorSquare);
                    tray.add(l);
                }
            }
        }
    }

    private void extendRight(String partialWord, TrieNode N, Square square){

    }
}
