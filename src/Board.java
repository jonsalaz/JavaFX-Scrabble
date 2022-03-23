import java.util.ArrayList;

public class Board {
    private Square[][] board;

    public Board(int dim, String[] rows) {
        board = new Square[dim][dim];

        for(int r = 0; r < dim; r++) {
            String[] places = rows[r].split(" ");
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
                    try{
                        if(board[r-1][c].getPlacedLetter() != null) {
                            anchors.add(board[r][c]);
                            continue;
                        }
                    }catch (Exception e) {
                    }
                    try{
                        if(board[r+1][c].getPlacedLetter() != null) {
                            anchors.add(board[r][c]);
                            continue;
                        }
                    }catch (Exception e) {
                    }
                    try{
                        if(board[r][c-1].getPlacedLetter() != null) {
                            anchors.add(board[r][c]);
                            continue;
                        }
                    }catch (Exception e) {
                    }
                    try{
                        if(board[r][c+1].getPlacedLetter() != null) {
                            anchors.add(board[r][c]);
                            continue;
                        }
                    }catch (Exception e) {
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
    }
}
