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
        for(int c = 0; c < board.length; c++) {
            for(int r = 0; r < board[c].length; r++) {
                //Checks to see if the current square is empty and next to a non empty square.
                if ((board[r][c].getPlacedLetter() == null)
                        && board[r-1][c].getPlacedLetter() != null ||
                        board[r+1][c].getPlacedLetter() != null ||
                        board[r][c-1].getPlacedLetter() != null ||
                        board[r][c+1].getPlacedLetter() != null) {
                    //TODO: Consider implementing as a map that maps the row to an array of column values indicating
                    // which columns have an anchor point within a given row.
                    anchors.add(board[r][c]);
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
}
