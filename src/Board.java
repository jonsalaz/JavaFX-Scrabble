import java.util.Arrays;

public class Board {
    private Tile[][] board;

    public Board(int dim, String[] rows) {
        board = new Tile[dim][dim];

        for(int r = 0; r < dim; r++) {
            String[] places = rows[r].split(" ");
            for (int c = 0; c < dim; c++) {
                if(places[c].length() == 2) {
                    board[r][c] = new Tile(places[c].charAt(0), places[c].charAt(1));
                }
                else {
                    board[r][c] = new Tile(places[c].charAt(0));
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
}
