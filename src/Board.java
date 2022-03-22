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
}
