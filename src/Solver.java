import java.util.ArrayList;

public class Solver {
    private Board board;
    private Tray tray;
    private Trie trie;

    public Solver(Board board, Tray tray, Trie trie) {
        this.board = board;
        this.tray = tray;
        this.trie = trie;
    }

    //TODO: Consider transposing board when doing down plays instead of trying to track whether moving down or across.
    // This is how nextSquare is currently calculated.
    public void solve() {
        // TODO: Calculate AnchorSquares
        //TODO: Check for moves one row at a time.
        //TODO: Repeat checking for moves once the board has been transposed (dont forget to transpose again
        // to restore it's initial state.
//        To generate all moves from Anchorsquare, assuming
//        that there are k non-anchor squares to the left of it, we
//        call
//        Leftpart("", root of dawg, k)

        ArrayList<Square> anchorSquares = board.getAnchors();
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
        if(square.getPlacedLetter() == null) {
            if(N.isTerminal()) {
                legalMove(partialWord);
            }
            for(int i = 0; i < N.getChildren().length; i++) {
                if((N.getChildren()[i] != null)
                        && (tray.contains((char) (i+97)))
                        && square.getCrossCheck(board, trie)[i]) {
                    Tile l = tray.get((char) (i+97));
                    TrieNode newN = N.getChildren()[i];
                    Square nextSquare = board.getSquare(square.getRow(), square.getColumn() + 1);

                    extendRight(partialWord + l.getLetter(), newN, nextSquare);
                }
            }
        }
        else {
            Tile l = square.getPlacedLetter();
            for (int i = 0; i < N.getChildren().length; i++) {
                if(N.getChildren()[l.getLetter()-97] != null) {
                    Square nextSquare = board.getSquare(square.getRow(), square.getColumn() + 1);
                    extendRight(partialWord + l.getLetter(), N.getChildren()[l.getLetter()-97], nextSquare);
                }
            }
        }
    }

    private void legalMove(String move) {
        //TODO: Track the highest-scoring move.
    }
}
