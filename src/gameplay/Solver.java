/**
 * Author: Jonathan Salazar
 * The Solver class is used to find the optimal move given the boards current state, a tray,
 * and a dictionary represented as a Trie.
 */
package gameplay;

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

    /**
     * Construction class for Solver.
     * @param board A reference to the current board.
     * @param tray A reference to the tray.
     * @param trie A reference to the Trie dictionary.
     */
    public Solver(Board board, Tray tray, Trie trie) {
        this.board = board;
        this.tray = tray;
        this.trie = trie;
    }

    /**
     * Checks for all possible moves on every anchor square through a backtracking algorithm found
     * in the paper The World's Fastest Scrabble Program by Andrew W. Appel and Guy J. Jacobson.
     */
    public int solve() {
        ArrayList<Square> anchorSquares = board.getAnchors();
        int limit;

        //First time checking for possible moves with a non transposed board.
        //One row at a time, calculate all possible moves for each AnchorSquare in that row.
        for(int r = 0; r < board.getRowLength(); r++) {
            limit = 0;
            for (int c = 0; c < board.getColumnLength(); c++) {
                if(anchorSquares.contains(board.getSquare(r, c))) {
                    if(limit > 0 && board.getSquare(r, c - 1).getPlacedLetter() != null) {
                        StringBuilder firstPart = new StringBuilder();
                        for(int i = c-1; i >= 0; i--) {
                            if(board.getSquare(r, i).getPlacedLetter() == null) {
                                limit = 0;
                                break;
                            } else {
                                firstPart.insert(0, board.getSquare(r, i).getPlacedLetter());
                                limit--;
                            }
                        }
                        //Find the new starting node for the word checking in leftPart().
                        TrieNode start = trie.getRoot();
                        for(int i = 0; i < firstPart.length(); i++) {
                            start = start.getChildren()[Character.toLowerCase(firstPart.charAt(i))-'a'];
                        }
                        //Call leftPart on the partially started word.
                        leftPart(firstPart.toString(), start, limit, board.getSquare(r, c));
                    }
                    else {
                        leftPart("", trie.getRoot(), limit, board.getSquare(r, c));
                    }
                    limit = 0;
                    continue;
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
                        StringBuilder firstPart = new StringBuilder();
                        for(int i = c-1; i >= 0; i--) {
                            if(board.getSquare(r, i).getPlacedLetter() == null) {
                                limit = 0;
                                break;
                            } else {
                                firstPart.insert(0, board.getSquare(r, i).getPlacedLetter());
                                limit--;
                            }
                        }
                        //Find the new starting node for the word checking in leftPart().
                        TrieNode start = trie.getRoot();
                        for(int i = 0; i < firstPart.length(); i++) {
                            start = start.getChildren()[Character.toLowerCase(firstPart.charAt(i))-'a'];
                        }
                        //Call leftPart on the partially started word.
                        leftPart(firstPart.toString(), start, limit, board.getSquare(r, c));
                    }
                    else {
                        leftPart("", trie.getRoot(), limit, board.getSquare(r, c));
                    }
                    limit = 0;
                    continue;
                }
                limit += 1;
            }
        }
        board.transpose();
        System.out.println("Solution " + moveWord + " has " + moveScore + " points");
        board.playMove(moveWord, tray, moveRow, moveCol, transpose);
        int temp = moveScore;
        moveScore = 0;
        return temp;
    }

    private void leftPart(String partialWord, TrieNode N, int limit, Square anchorSquare){
        extendRight(partialWord, N, anchorSquare, 0);
        if(limit > 0) {
            for(int i = 0; i < 26; i++) {
                if ((N.getChildren()[i] != null) && tray.contains((char) (i+'a'))){
                    Tile l = tray.get((char) (i+'a'));
                    tray.remove(l);
                    TrieNode newN = N.getChildren()[i];
                    //If a wildcard is used, use the upper case version of the currently checked letter.
                    if(l.getLetter() == '*') {
                        partialWord += Character.toUpperCase((char) (i + 'a'));
                    }
                    else{
                        partialWord += Character.toLowerCase(l.getLetter());
                    }
                    leftPart(partialWord, newN, limit - 1, anchorSquare);
                    partialWord = partialWord.substring(0, partialWord.length()-1);
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
            for(int i = 0; i < 26; i++) {
                if((N.getChildren()[i] != null)
                        && (tray.contains((char) (i+'a')))
                        && square.getCrossCheck(board, trie)[i]) {
                    Tile l = tray.get((char) (i+'a'));
                    tray.remove(l);
                    TrieNode newN = N.getChildren()[i];
                    Square nextSquare;
                    try {
                        nextSquare = board.getSquare(square.getRow(), square.getColumn() + 1);
                    } catch(IndexOutOfBoundsException e) {
                        if(newN.isTerminal()) {
                            if(l.getLetter() == '*') {
                                partialWord += Character.toUpperCase((char) (i + 'a'));
                            }
                            else{
                                partialWord += Character.toLowerCase(l.getLetter());
                            }
                            legalMove(partialWord, square.getRow(), square.getColumn()+1);
                            partialWord = partialWord.substring(0, partialWord.length()-1);
                        }
                        tray.add(l);
                        continue;
                    }
                    if(l.getLetter() == '*') {
                        partialWord += Character.toUpperCase((char) (i + 'a'));
                    }
                    else{
                        partialWord += Character.toLowerCase(l.getLetter());
                    }
                    extendRight(partialWord, newN, nextSquare, depth + 1);
                    partialWord = partialWord.substring(0, partialWord.length()-1);
                    tray.add(l);
                }
            }
        }
        else {
            Tile l = square.getPlacedLetter();
            if(N.getChildren()[Character.toLowerCase(l.getLetter())-'a'] != null) {
                Square nextSquare;
                try {
                    nextSquare = board.getSquare(square.getRow(), square.getColumn() + 1);
                } catch(IndexOutOfBoundsException e) {
                    if(N.getChildren()[Character.toLowerCase(l.getLetter())-'a'].isTerminal()) {
                        legalMove(partialWord + l.getLetter(), square.getRow(), square.getColumn()+1);
                    }
                    return;
                }
                extendRight(partialWord + l.getLetter(),
                        N.getChildren()[Character.toLowerCase(l.getLetter())-'a'], nextSquare, depth + 1);
            }
        }
    }

    private void legalMove(String move, int row, int column) {
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
                int localMult = 1;
                int localScore = 0;
                //Only read the placed letter once instead of twice if there are letters above AND below
                if((board.getSquare(row + 1, column-i).getPlacedLetter() != null
                        || board.getSquare(row-1, column-i).getPlacedLetter() != null)
                        && board.getSquare(row, column-i).getPlacedLetter() == null) {
                    //Re-add placed letter with letter multiplier.
                    localScore += Tile.getScore(move.charAt(counter))
                            * board.getSquare(row, column - i).getLetterMultiplier();
                    //Get word multiplier if there is one.
                    localMult *= board.getSquare(row, column - i).getWordMultiplier();
                }

                //Check if a word is formed in the down direction.
                if(board.getSquare(row+1, column-i).getPlacedLetter() != null
                        && board.getSquare(row, column-i).getPlacedLetter() == null) {
                    //Get the square below the placed letter as our starting point.
                    Square temp = board.getSquare(row+1, column-i);
                    //Add the starting letters score.
                    localScore += Tile.getScore(temp.getPlacedLetter().getLetter());
                    //Loop until a null character is reached or the index will be out of bounds.
                    while((temp.getRow()+1 < board.getRowLength())
                            && board.getSquare(temp.getRow()+1, column-i).getPlacedLetter() != null) {
                        //Get the next square (if it is not out of bounds and has a letter placed.)
                        temp = board.getSquare(temp.getRow()+1, column-i);
                        //Add the score of the square to the score.
                        localScore += Tile.getScore(temp.getPlacedLetter().getLetter());
                    }
                }

                //Check if a word was formed from the top.
                if(board.getSquare(row-1, column-i).getPlacedLetter() != null
                        && board.getSquare(row, column-i).getPlacedLetter() == null) {
                    //Get the square above the placed letter as our starting point.
                    Square temp = board.getSquare(row-1, column-i);
                    //Add the starting letters score.
                    localScore += Tile.getScore(temp.getPlacedLetter().getLetter());
                    //Loop until a null character is reached or the index will be out of bounds.
                    while((temp.getRow()-1 > 0)
                            && board.getSquare(temp.getRow()-1, column-i).getPlacedLetter() != null) {
                        //Get the next square (if it is not out of bounds and has a letter placed.)
                        temp = board.getSquare(temp.getRow()-1, column-i);
                        //Add the score of the square to the score.
                        localScore += Tile.getScore(Character.toLowerCase(temp.getPlacedLetter().getLetter()));
                    }
                }
                localScore *= localMult;
                tempScore += localScore;
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
