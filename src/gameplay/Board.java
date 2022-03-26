/**
 * Author: Jonathan Salazar
 * The Board class is used to store data pertaining to the board and contains
 * any functions pertaining to playing moves to the board.
 */
package gameplay;

import java.util.ArrayList;

public class Board {
    private Square[][] board;
    private boolean transpose;
    private Trie trie;

    /**
     * Constructor class for the board object.
     * @param dim the dimension of the board. The board is assumed to be square.
     * @param rows An array consisting of strings representing the boards configuration.
     */
    public Board(int dim, String[] rows) {
        board = new Square[dim][dim];
        this.transpose = false;

        for(int r = 0; r < dim; r++) {
            if(rows[r].charAt(0) == ' ') {
                rows[r] = rows[r].substring(1);
            }

            String[] places = rows[r].split("\\s+");
            for (int c = 0; c < dim; c++) {
                if(places[c].length() == 2) {
                    board[r][c] = new Square(places[c].charAt(0), places[c].charAt(1), r, c);
                }
                else {
                    board[r][c] = new Square(places[c].charAt(0), r, c);
                }
            }
        }
    }

    public Board(int dim, String[] rows, Trie trie) {
        board = new Square[dim][dim];
        this.trie = trie;
        this.transpose = false;

        for(int r = 0; r < dim; r++) {
            if(rows[r].charAt(0) == ' ') {
                rows[r] = rows[r].substring(1);
            }

            String[] places = rows[r].split("\\s+");
            for (int c = 0; c < dim; c++) {
                if(places[c].length() == 2) {
                    board[r][c] = new Square(places[c].charAt(0), places[c].charAt(1), r, c);
                }
                else {
                    board[r][c] = new Square(places[c].charAt(0), r, c);
                }
            }
        }
    }


    /**
     * Handles the representation of the board object as a string.
     * @return the string representation of the board object.
     */
    @Override
    public String toString() {
        String temp = "";
        for(int r = 0; r < board.length; r++) {
            for (int c = 0; c < board[r].length; c++) {
                temp+= board[r][c].toString() + " ";
            }
            if(r != board.length-1) {
                temp += "\n";
            }
        }
        return temp;
    }

    /**
     * A getter for the square at a given row and column.
     * @param row The row of the desired square.
     * @param column The column of the desired square.
     * @return The desired square given row and column.
     */
    public Square getSquare(int row, int column) {
        return board[row][column];
    }

    /**
     * Determines what squares are candidate anchors dependent on if there is a placed letter
     * to the left, right, top, or bottom of a given square.
     * @return An ArrayList of squares containing every candidate anchor square on the board.
     */
    public ArrayList<Square> getAnchors() {
        ArrayList<Square> anchors = new ArrayList<>();
        for(int r = 0; r < board.length; r++) {
            for(int c = 0; c < board[r].length; c++) {
                if(board[r][c].getPlacedLetter() == null) {

                    if(r != 0) {
                        if(board[r-1][c].getPlacedLetter() != null) {
                            anchors.add(board[r][c]);
                            continue;
                        }
                    }

                    if(r != board.length-1) {
                        if(board[r+1][c].getPlacedLetter() != null) {
                            anchors.add(board[r][c]);
                            continue;
                        }
                    }

                    if(c != 0) {
                        if(board[r][c-1].getPlacedLetter() != null) {
                            anchors.add(board[r][c]);
                            continue;
                        }
                    }

                    if(c != board[r].length-1) {
                        if(board[r][c+1].getPlacedLetter() != null) {
                            anchors.add(board[r][c]);
                            continue;
                        }
                    }
                }
            }
        }
        return anchors;
    }

    /**
     * A getter for the row length of the board.
     * @return The row length.
     */
    public int getRowLength() {
        return board.length;
    }

    /**
     * A getter for the column length of the board.
     * @return The column length.
     */
    public int getColumnLength() {
        return board[0].length;
    }

    /**
     * Transposes the board and ensures that each squares row and column values are updated accordingly.
     */
    public void transpose() {
        Square[][] temp = new Square[board.length][board[0].length];
        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board[r].length; c++) {
                board[c][r].transpose();
                temp[r][c] = board[c][r];
            }
        }
        board = temp;
        //Switch tranpose tracker
        if(this.transpose) {
            this.transpose = false;
        } else {
            this.transpose = true;
        }
    }

    /**
     * Determines whether the board is currently in a transposed state.
     * @return A boolean representing whether or not the board is in a transposed state.
     */
    public boolean isTranpose() {
        return this.transpose;
    }

    /**
     * Given the conditions of a move, plays the move to the board.
     * @param moveWord The word to be played.
     * @param tray The players current tray.
     * @param moveScore The move's score.
     * @param moveRow The move's intended row.
     * @param moveCol The move's intended column.
     * @param transpose Whether the move was transposed when the move was found (assuming only across moves are
     *                  able to be calculated).
     */
    public void playMove(String moveWord, Tray tray, int moveScore, int moveRow, int moveCol, boolean transpose) {
        boolean flipped = false;
        if(transpose) {
            this.transpose();
            flipped = true;
        }

        //Play move across.
        Square placement = board[moveRow][moveCol];
        for(int i = 0; i < moveWord.length(); i++) {
            if(placement.getPlacedLetter() != null) {
                //If letter is already on board.
                if(i != moveWord.length()-1) {
                    placement = board[placement.getRow()][placement.getColumn() + 1];
                }
            }
            else if(tray.contains(moveWord.charAt(i))) {
                //If tray does not contain a placed letter.
                Tile tile = tray.get(moveWord.charAt(i));
                tray.remove(tile);
                placement.setPlacedLetter(tile);
                if(tile.getLetter() == '*') {
                    tile.setLetter(moveWord.charAt(i));
                }
                if(i != moveWord.length() - 1) {
                    placement = board[placement.getRow()][placement.getColumn() + 1];
                }
            } else {
                System.out.println("Invalid move");
            }
        }
        if(flipped) {
            this.transpose();
        }
    }

    public boolean checkIfLegal(String word, int row, int column, Boolean down, Tray tray, Square selected) {
        Boolean anchor = false;
        if(getAnchors().isEmpty()) {
            if (!(selected == board[7][7] && trie.search(word))) {
                System.out.println("false right here");
                return false;
            }
            anchor = true;
        }
        ArrayList<Tile> removedTiles = new ArrayList<>();
        Boolean legal = true;
        word = word.toLowerCase();
        Boolean flipped = false;
        if(down) {
            transpose();
            int temp = row;
            row = column;
            column = temp;
            flipped = true;
        }

        if(!trie.search(word)) {
            System.out.println("not a real word");
            transpose();
            return false;
        }

        for(int i = 0; i < word.length(); i++) {
            if(!board[row][column+i].getCrossCheck(this, trie)[Character.toLowerCase(word.charAt(i))-'a']) {
                legal = false;
                System.out.println("not in crosscheck");
                break;
            } else if(!tray.contains(word.charAt(i))
                    && board[row][column+i].getPlacedLetter() != null
                    && board[row][column+i].getPlacedLetter().getLetter() != word.charAt(i)) {
                System.out.println("dont have tray it in tray.");
                legal = false;
                break;
            }

            if(getAnchors().contains(board[row][column + i])){
                System.out.println("No anchor included");
                anchor = true;
            }
            removedTiles.add(tray.get(word.charAt(i)));
            tray.remove(tray.get(word.charAt(i)));
        }
        for (Tile tile :
                removedTiles) {
            if(tile != null) {
                tray.add(tile);
            }
        }

        if(flipped) {
            transpose();
        }
        return (legal && anchor);
    }

    public int calculateScore(String word, int row, int column, Boolean down, Tray tray) {
        Boolean flipped = false;
        if(down) {
            transpose();
            int temp = row;
            row = column;
            column = temp;
            flipped = true;
        }
        int tempScore = 0;
        int wordMult = 1;
        int counter = 0;

        //Letter and Word score for the word just placed.
        for (int i = 0; i < word.length(); i++) {
            wordMult *= getSquare(row, column + i).getWordMultiplier();
            tempScore += Tile.getScore(word.charAt(counter)) * getSquare(row,
                    column+i).getLetterMultiplier();
            counter++;
        }
        tempScore *= wordMult;

        //Letter and word score any additional words formed in the tangential direction.
        if(row == 0) {
            //Check for words formed in the down direction.
            counter = 0;
            for(int i = 0; i < word.length(); i++) {
                if(getSquare(row+1, column+i).getPlacedLetter() != null
                        && getSquare(row, column+i).getPlacedLetter() == null) {
                    //Re-add placed letter with letter multiplier.
                    tempScore += Tile.getScore(word.charAt(counter))
                            * getSquare(row, column + i).getLetterMultiplier();
                    //Get the square below the placed letter as our starting point.
                    Square temp = getSquare(row+1, column+i);
                    //Add the starting letters score.
                    tempScore += Tile.getScore(temp.getPlacedLetter().getLetter());
                    //Loop until a null character is reached or the index will be out of bounds.
                    while((temp.getRow()+1 < getRowLength())
                            && getSquare(temp.getRow()+1, column+i).getPlacedLetter() != null) {
                        //Get the next square (if it is not out of bounds and has a letter placed.)
                        temp = getSquare(temp.getRow()+1, column+i);
                        //Add the score of the square to the score.
                        tempScore += Tile.getScore(temp.getPlacedLetter().getLetter());
                    }
                }
                counter++;
            }
        }
        //If the row is the bottom row of the board. Only check for up words.
        else if(row == getRowLength()-1) {
            counter = 0;
            for(int i = 0; i < word.length(); i++) {
                if(getSquare(row-1, column+i).getPlacedLetter() != null
                        && getSquare(row, column+i).getPlacedLetter() == null) {
                    //Re-add placed letter with letter multiplier.
                    tempScore += Tile.getScore(word.charAt(counter))
                            * getSquare(row, column + i).getLetterMultiplier();
                    //Get the square above the placed letter as our starting point.
                    Square temp = getSquare(row-1, column+i);
                    //Add the starting letters score.
                    tempScore += Tile.getScore(temp.getPlacedLetter().getLetter());
                    //Loop until a null character is reached or the index will be out of bounds.
                    while((temp.getRow()-1 > 0)
                            && getSquare(temp.getRow()-1, column+i).getPlacedLetter() != null) {
                        //Get the next square (if it is not out of bounds and has a letter placed.)
                        temp = getSquare(temp.getRow()-1, column+i);
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
            for(int i = 0; i < word.length(); i++) {
                int localMult = 1;
                int localScore = 0;
                //Only read the placed letter once instead of twice if there are letters above AND below
                if((getSquare(row + 1, column+i).getPlacedLetter() != null
                        || getSquare(row-1, column+i).getPlacedLetter() != null)
                        && getSquare(row, column+i).getPlacedLetter() == null) {
                    //Re-add placed letter with letter multiplier.
                    localScore += Tile.getScore(word.charAt(counter))
                            * getSquare(row, column + i).getLetterMultiplier();
                    //Get word multiplier if there is one.
                    localMult *= getSquare(row, column + i).getWordMultiplier();
                }

                //Check if a word is formed in the down direction.
                if(getSquare(row+1, column+i).getPlacedLetter() != null
                        && getSquare(row, column+i).getPlacedLetter() == null) {
                    //Get the square below the placed letter as our starting point.
                    Square temp = getSquare(row+1, column+i);
                    //Add the starting letters score.
                    localScore += Tile.getScore(temp.getPlacedLetter().getLetter());
                    //Loop until a null character is reached or the index will be out of bounds.
                    while((temp.getRow()+1 < getRowLength())
                            && getSquare(temp.getRow()+1, column+i).getPlacedLetter() != null) {
                        //Get the next square (if it is not out of bounds and has a letter placed.)
                        temp = getSquare(temp.getRow()+1, column+i);
                        //Add the score of the square to the score.
                        localScore += Tile.getScore(temp.getPlacedLetter().getLetter());
                    }
                }

                //Check if a word was formed from the top.
                if(getSquare(row-1, column+i).getPlacedLetter() != null
                        && getSquare(row, column+i).getPlacedLetter() == null) {
                    //Get the square above the placed letter as our starting point.
                    Square temp = getSquare(row-1, column+i);
                    //Add the starting letters score.
                    localScore += Tile.getScore(temp.getPlacedLetter().getLetter());
                    //Loop until a null character is reached or the index will be out of bounds.
                    while((temp.getRow()-1 > 0)
                            && getSquare(temp.getRow()-1, column+i).getPlacedLetter() != null) {
                        //Get the next square (if it is not out of bounds and has a letter placed.)
                        temp = getSquare(temp.getRow()-1, column+i);
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

        if(flipped) {
            transpose();
        }
        return tempScore;
    }
}
