import java.util.ArrayList;

public class Solver {
    private Board board;
    private Tray tray;
    private Trie trie;

    private int moveRow;
    private int moveCol;
    private String moveWord;
    private int moveScore;
    private boolean transpose;

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
        System.out.println(moveRow);
        System.out.println(moveCol);
        System.out.println(moveWord);
        board.transpose();
        //TODO: Play the move (taking into account whether or not the move is played across of down).
    }

    private void leftPart(String partialWord, TrieNode N, int limit, Square anchorSquare){
        extendRight(partialWord, N, anchorSquare, 0);
        if(limit > 0) {
            for(int i = 0; i < N.getChildren().length; i++) {
                if ((N.getChildren()[i] != null) && tray.contains((char) (i+97))){
                    Tile l = tray.get((char) (i+97));
                    tray.remove(l);
                    TrieNode newN = N.getChildren()[i];
                    leftPart(partialWord + Character.toLowerCase(l.getLetter()), newN, limit - 1, anchorSquare);
                    tray.add(l);
                }
            }
        }
    }

    private void extendRight(String partialWord, TrieNode N, Square square, int depth){
        if(square.getPlacedLetter() == null) {
            if(N.isTerminal() && depth != 0) {
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
                        }
                        tray.add(l);
                        continue;
                    }
                    extendRight(partialWord + l.getLetter(), newN, nextSquare, depth + 1);
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
                extendRight(partialWord + l.getLetter(),
                        N.getChildren()[l.getLetter()-97], nextSquare, depth + 1);
            }
        }
    }

    private void legalMove(String move, int row, int column) {
        //TODO: Fix score counting.
        //TODO: Consider moving code to a separate function for score keeping.
        int tempScore = 0;
        int wordMult = 1;
        int counter = 0;

        //Letter and Word score for the word just placed.
        for (int i = move.length(); i > 0; i--) {
            wordMult *= board.getSquare(row, column - i).getWordMultiplier();
            tempScore += Tile.getScore(move.charAt(counter)) * board.getSquare(row,
                    column-i).getLetterMultiplier();
            counter++;
        }
        tempScore *= wordMult;

        //Letter and word score any additional words formed in the tangential direction.
        if(row == 0) {
            //Check for words formed in the down direction.
            counter = 0;
            for(int i = move.length(); i > 0; i--) {
                if(board.getSquare(row+1, column-i).getPlacedLetter() != null
                        && board.getSquare(row, column-i).getPlacedLetter() == null) {
                    //Re-add placed letter with letter multiplier.
                    tempScore += Tile.getScore(move.charAt(counter))
                            * board.getSquare(row, column - i).getLetterMultiplier();
                    //Get the square below the placed letter as our starting point.
                    Square temp = board.getSquare(row+1, column-i);
                    //Add the starting letters score.
                    tempScore += Tile.getScore(temp.getPlacedLetter().getLetter());
                    //Loop until a null character is reached or the index will be out of bounds.
                    while((temp.getRow()+1 < board.getRowLength())
                            && board.getSquare(temp.getRow()+1, column-i).getPlacedLetter() != null) {
                        //Get the next square (if it is not out of bounds and has a letter placed.)
                        temp = board.getSquare(temp.getRow()+1, column-i);
                        //Add the score of the square to the score.
                        tempScore += Tile.getScore(temp.getPlacedLetter().getLetter());
                    }
                }
                counter++;
            }
        }
        //TODO: Check if indexing is right here.
        //If the row is the bottom row of the board. Only check for up words.
        else if(row == board.getRowLength()-1) {
            counter = 0;
            for(int i = move.length(); i > 0; i--) {
                if(board.getSquare(row-1, column-i).getPlacedLetter() != null
                        && board.getSquare(row, column-i).getPlacedLetter() == null) {
                    //Re-add placed letter with letter multiplier.
                    tempScore += Tile.getScore(move.charAt(counter))
                            * board.getSquare(row, column - i).getLetterMultiplier();
                    //Get the square above the placed letter as our starting point.
                    Square temp = board.getSquare(row-1, column-i);
                    //Add the starting letters score.
                    tempScore += Tile.getScore(temp.getPlacedLetter().getLetter());
                    //Loop until a null character is reached or the index will be out of bounds.
                    while((temp.getRow()-1 > 0)
                            && board.getSquare(temp.getRow()-1, column-i).getPlacedLetter() != null) {
                        //Get the next square (if it is not out of bounds and has a letter placed.)
                        temp = board.getSquare(temp.getRow()-1, column-i);
                        //Add the score of the square to the score.
                        tempScore += Tile.getScore(temp.getPlacedLetter().getLetter());
                    }
                }
                counter++;
            }
        }
        else {
            //Check for down words and up words.
            counter = 0;
            for(int i = move.length(); i > 0; i--) {
                //Only read the placed letter once instead of twice if there are letters above AND below
                if((board.getSquare(row + 1, column-i).getPlacedLetter() != null
                        || board.getSquare(row-1, column-i).getPlacedLetter() != null)
                        && board.getSquare(row, column-i).getPlacedLetter() == null) {
                    //Re-add placed letter with letter multiplier.
                    tempScore += Tile.getScore(move.charAt(counter))
                            * board.getSquare(row, column - i).getLetterMultiplier();
                }

                //Check if a word is formed in the down direction.
                if(board.getSquare(row+1, column-i).getPlacedLetter() != null
                        && board.getSquare(row, column-i).getPlacedLetter() == null) {
                    //Get the square below the placed letter as our starting point.
                    Square temp = board.getSquare(row+1, column-i);
                    //Add the starting letters score.
                    tempScore += Tile.getScore(temp.getPlacedLetter().getLetter());
                    //Loop until a null character is reached or the index will be out of bounds.
                    while((temp.getRow()+1 < board.getRowLength())
                            && board.getSquare(temp.getRow()+1, column-i).getPlacedLetter() != null) {
                        //Get the next square (if it is not out of bounds and has a letter placed.)
                        temp = board.getSquare(temp.getRow()+1, column-i);
                        //Add the score of the square to the score.
                        tempScore += Tile.getScore(temp.getPlacedLetter().getLetter());
                    }
                }

                //Check if a word was formed from the top.
                if(board.getSquare(row-1, column-i).getPlacedLetter() != null
                        && board.getSquare(row, column-i).getPlacedLetter() == null) {
                    //Get the square above the placed letter as our starting point.
                    Square temp = board.getSquare(row-1, column-i);
                    //Add the starting letters score.
                    tempScore += Tile.getScore(temp.getPlacedLetter().getLetter());
                    //Loop until a null character is reached or the index will be out of bounds.
                    while((temp.getRow()-1 > 0)
                            && board.getSquare(temp.getRow()-1, column-i).getPlacedLetter() != null) {
                        //Get the next square (if it is not out of bounds and has a letter placed.)
                        temp = board.getSquare(temp.getRow()-1, column-i);
                        //Add the score of the square to the score.
                        tempScore += Tile.getScore(Character.toLowerCase(temp.getPlacedLetter().getLetter()));
                    }
                }
                counter++;
            }
        }

        if(tray.isBingo()) {
            tempScore += 50;
        }

        if(tempScore > moveScore) {
            moveScore = tempScore;
            moveRow = row;
            moveCol = column - move.length();
            moveWord = move;
            transpose = board.isTranpose();
        }
    }
}

//TODO: Check crosschecks to see if they are accounting for when a letter is sandwhiched between two preplaced moves.