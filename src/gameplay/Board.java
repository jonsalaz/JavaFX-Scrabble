package gameplay;

import java.util.ArrayList;

public class Board {
    private Square[][] board;
    private boolean transpose;

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

    public Square getSquare(int row, int column) {
        return board[row][column];
    }

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

    public int getRowLength() {
        return board.length;
    }

    public int getColumnLength() {
        return board[0].length;
    }

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

    public boolean isTranpose() {
        return this.transpose;
    }

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