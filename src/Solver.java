import java.util.ArrayList;

public class Solver {
    private Board board;
    private Tray tray;
    private Trie trie;

    private int moveRow;
    private int moveCol;
    private String moveWord;
    private int moveScore;

    public Solver(Board board, Tray tray, Trie trie) {
        this.board = board;
        this.tray = tray;
        this.trie = trie;
    }

    public void solve() {
        ArrayList<Square> anchorSquares = board.getAnchors();
        int limit;

        //First time checking for possible moves with a non transposed board.
        //One row at a time, calculate all possible moves for each AnchorSquare in that row.
        for(int r = 0; r < board.getRowLength(); r++) {
            limit = 0;
            for (int c = 0; c < board.getColumnLength(); c++) {
                if(anchorSquares.contains(board.getSquare(r, c))) {
                    if(limit > 0 && board.getSquare(r, c - 1).getPlacedLetter() != null) {
                        Square temp = board.getSquare(r, c);
                        StringBuilder partialWordBuilder = new StringBuilder();
                        while(limit > 0) {
                            temp = board.getSquare(r, temp.getColumn()-1);
                            partialWordBuilder.insert(0, temp.getPlacedLetter().getLetter());
                            limit--;
                        }
                        String partialWord = partialWordBuilder.toString().toLowerCase();
                        TrieNode start = trie.getRoot();
                        for(int i = 0; i < partialWordBuilder.length(); i++) {
                            start = start.getChildren()[partialWord.charAt(i)-97];
                        }

                        leftPart(partialWord, start, limit, board.getSquare(r, c));
                    }
                    else {
                        leftPart("", trie.getRoot(), limit, board.getSquare(r, c));
                        limit = 0;
                        continue;
                    }
                }
                limit += 1;
            }
        }

        board.transpose();
        for(int r = 0; r < board.getRowLength(); r++) {
            limit = 0;
            for (int c = 0; c < board.getColumnLength(); c++) {
                if(anchorSquares.contains(board.getSquare(r, c))) {
                    if(limit > 0 && board.getSquare(r, c - 1).getPlacedLetter() != null) {
                        Square temp = board.getSquare(r, c);
                        StringBuilder partialWordBuilder = new StringBuilder();
                        while(limit > 0) {
                            temp = board.getSquare(r, temp.getColumn()-1);
                            partialWordBuilder.insert(0, temp.getPlacedLetter().getLetter());
                            limit--;
                        }
                        String partialWord = partialWordBuilder.toString().toLowerCase();
                        TrieNode start = trie.getRoot();
                        for(int i = 0; i < partialWordBuilder.length(); i++) {
                            start = start.getChildren()[partialWord.charAt(i)-97];
                        }

                        leftPart(partialWord, start, limit, board.getSquare(r, c));
                    }
                    else {
                        leftPart("", trie.getRoot(), limit, board.getSquare(r, c));
                        limit = 0;
                        continue;
                    }
                }
                limit += 1;
            }
        }
        System.out.println(board);
        board.transpose();
        //TODO: Play the move (taking into account whether or not the move is played across of down).
    }

    private void leftPart(String partialWord, TrieNode N, int limit, Square anchorSquare){
        extendRight(partialWord, N, anchorSquare);
        if(limit > 0) {
            for(int i = 0; i < N.getChildren().length; i++) {
                if ((N.getChildren()[i] != null) && tray.contains((char) (i+97))) {
                    Tile l = tray.get((char) (i+97));
                    tray.remove(l);
                    TrieNode newN = N.getChildren()[i];
                    leftPart(partialWord + Character.toLowerCase(l.getLetter()), newN, limit - 1, anchorSquare);
                    tray.add(l);
                }
            }
        }
    }

    private void extendRight(String partialWord, TrieNode N, Square square){
        if(square.getPlacedLetter() == null) {
            if(N.isTerminal()) {
                legalMove(partialWord, square.getRow(), square.getColumn());
            }

            for(int i = 0; i < N.getChildren().length; i++) {
                if((N.getChildren()[i] != null)
                        && (tray.contains((char) (i+97)))
                        && square.getCrossCheck(board, trie)[i]) {
                    Tile l = tray.get((char) (i+97));
                    tray.remove(l);
                    TrieNode newN = N.getChildren()[i];
                    Square nextSquare;
                    try {
                        nextSquare = board.getSquare(square.getRow(), square.getColumn() + 1);
                    } catch(IndexOutOfBoundsException e) {
                        if(newN.isTerminal()) {
                            legalMove(partialWord+l.getLetter(), square.getRow(), square.getColumn()+1);
                            tray.add(l);
                        }
                        break;
                    }
                    extendRight(partialWord + l.getLetter(), newN, nextSquare);
                    tray.add(l);
                }
            }
        }
        else {
            Tile l = square.getPlacedLetter();
            if(N.getChildren()[l.getLetter()-97] != null) {
                Square nextSquare;
                try {
                    nextSquare = board.getSquare(square.getRow(), square.getColumn() + 1);
                } catch(IndexOutOfBoundsException e) {
                    if(N.isTerminal()) {
                        legalMove(partialWord, square.getRow(), square.getColumn());
                    }
                    return;
                }
                extendRight(partialWord + l.getLetter(), N.getChildren()[l.getLetter()-97], nextSquare);
            }
        }
    }

    private void legalMove(String move, int row, int column) {
        int tempScore = 0;
        int wordMult = 1;
        int counter = 0;

        for (int i = move.length()-1; i >= 0; i--) {
            try {
                wordMult *= board.getSquare(row, column - i).getWordMultiplier();
                tempScore += Tile.getScore(move.charAt(counter)) * board.getSquare(row,
                        column-i).getLetterMultiplier();
            } catch(Exception e) {
                return;
            }
            counter++;
        }
        tempScore *= wordMult;

        System.out.println("PLAYABLE MOVE?:");
        System.out.println("ROW: " + row);
        System.out.println("COLUMN: " + column);
        System.out.println(move);
        if(tempScore > moveScore) {
            moveScore = tempScore;
            moveRow = row;
            moveCol = column - move.length();
            moveWord = move;
        }
    }
}
