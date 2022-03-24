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
}
