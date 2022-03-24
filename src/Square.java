import java.util.Arrays;

public class Square {
    private int row;
    private int column;
    private int wm;
    private int lm;
    private Tile placedLetter;

    public Square(char wm, char lm, int row, int column) {
        if(wm == '.'){
            this.wm = 1;
        }
        else {
            this.wm = Character.getNumericValue(wm);
        }

        if(lm == '.' || lm == ' ') {
            this.lm = 1;
        }
        else {
            this.lm = Character.getNumericValue(lm);
        }
        this.placedLetter = null;
        this.row = row;
        this.column = column;
    }

    public Square(char letter, int row, int column) {
        this.wm = 1;
        this.lm = 1;

        this.placedLetter = new Tile(letter);
        this.row = row;
        this.column = column;
    }

    public Tile getPlacedLetter() {
        return placedLetter;
    }

    public int getWordMultiplier() {
        return wm;
    }

    public int getLetterMultiplier() {
        return lm;
    }

    public Boolean[] getCrossCheck(Board board, Trie trie) {
        Boolean[] crossCheck = new Boolean[26];

        if(this.row == 0) {
            if(board.getSquare(row + 1, column) == null) {
                Arrays.fill(crossCheck, true);
            } else {
                //Build string out of already placed letters below the current square.
                StringBuilder alreadyPlaced = new StringBuilder();
                for (int i = row + 1; i < board.getRowLength(); i++) {
                    if (board.getSquare(i, column).getPlacedLetter() == null) {
                        break;
                    } else {
                        alreadyPlaced.append(board.getSquare(i, column).getPlacedLetter().getLetter());
                    }
                }
                //Check every letter combination combined with the prexisting letters below the square to create a list of
                // letters that can be placed on the current square.
                for (int i = 0; i < 26; i++) {
                    char currentLetter = (char) (i + 'a');
                    if (crossCheck[i] == null || crossCheck[i]) {
                        String searchWord = currentLetter + alreadyPlaced.toString();
                        crossCheck[i] = trie.search(searchWord);
                    }
                }
            }
        } else if(this.row == board.getRowLength()-1) {
            //Build word out previously placed letters from above.
            StringBuilder alreadyPlaced = new StringBuilder();
            if(board.getSquare(row - 1, column) == null) {
                Arrays.fill(crossCheck, true);
            } else {
                for (int i = row - 1; i >= 0; i--) {
                    if (board.getSquare(i, column).getPlacedLetter() == null) {
                        break;
                    } else {
                        alreadyPlaced.insert(0, board.getSquare(i, column).getPlacedLetter().getLetter());
                    }
                }

                for (int i = 0; i < 26; i++) {
                    char currentLetter = (char) (i + 'a');
                    if (crossCheck[i] == null || crossCheck[i]) {
                        String searchWord = alreadyPlaced.toString() + currentLetter;
                        crossCheck[i] = trie.search(searchWord);
                    }
                }
            }
        } else {
            if(board.getSquare(row-1, column).getPlacedLetter() == null
                    && board.getSquare(row+1, column).getPlacedLetter() == null) {
                Arrays.fill(crossCheck, true);
            } else {
                StringBuilder firstHalf = new StringBuilder();
                StringBuilder secondHalf = new StringBuilder();
                //Build first half of word.
                for (int i = row - 1; i >= 0; i--) {
                    if (board.getSquare(i, column).getPlacedLetter() == null) {
                        break;
                    } else {
                        firstHalf.insert(0, board.getSquare(i, column).getPlacedLetter().getLetter());
                    }
                }

                for (int i = row + 1; i < board.getRowLength(); i++) {
                    if (board.getSquare(i, column).getPlacedLetter() == null) {
                        break;
                    } else {
                        secondHalf.append(board.getSquare(i, column).getPlacedLetter().getLetter());
                    }
                }

                for(int i = 0; i < 26; i++) {
                    char currentLetter = (char) (i+ 'a');
                    String searchWord = firstHalf.toString() + currentLetter + secondHalf.toString();
                    searchWord = searchWord.toLowerCase();
                    if(crossCheck[i] == null || crossCheck[i]) {
                        crossCheck[i] = trie.search(searchWord);
                    }
                }
            }
        }
        return crossCheck;
    }

    @Override
    public String toString() {
        if (placedLetter != null) {
            return " " + placedLetter;
        }
        else {
            String temp = "";

            if (wm == 1) {
                temp += ".";
            }
            else {
                temp += Integer.toString(wm);
            }

            if (lm == 1) {
                temp += ".";
            }
            else {
                temp += Integer.toString(lm);
            }

            return temp;
        }
    }

    public int getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }

    public void transpose() {
        int temp = this.row;
        this.row = this.column;
        this.column = temp;
    }

    public void setPlacedLetter(Tile tile) {
        this.placedLetter = tile;
    }
}