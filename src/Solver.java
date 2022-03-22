import java.util.Map;

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

    //TODO: Consider passing in the index of the anchor square rather than the actual square, then accessing the square
    // once inside the function so that you can more easily navigate to the square to the right.
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
        if(square.getPlacedLetter() == null) {
            if(N.isTerminal()) {
                legalMove(partialWord);
            }
            for(int i = 0; i < N.getChildren().length; i++) {
                if((N.getChildren()[i] != null)
                        && (tray.contains((char) (i+97)))
                        && square.getCrossCheck().contains((char) (i+97))) {
                    Tile l = tray.get((char) (i+97));
                    TrieNode newN = N.getChildren()[i];
                    //TODO: Let nextSquare be the square to the right of square.
                    Square nextSquare = null;

                    extendRight(partialWord + l.getLetter(), newN, nextSquare);
                }
            }
        }
        else {
            Tile l = square.getPlacedLetter();
            for (int i = 0; i < N.getChildren().length; i++) {
                if(N.getChildren()[l.getLetter()-97] != null) {
                    //TODO: Let nextSquare be the square to the right of square.
                    Square nextSquare = null;
                    extendRight(partialWord + l.getLetter(), N.getChildren()[l.getLetter()-97], nextSquare);
                }
            }
        }
    }

    private void legalMove(String move) {
        //TODO: Track the highest-scoring move and discard the rest.
    }
}
