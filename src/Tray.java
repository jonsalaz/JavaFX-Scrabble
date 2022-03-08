import java.util.ArrayList;

public class Tray {
    private ArrayList<Character> tray;

    public Tray(String letters) {
        //TODO: Make this a playable letter not a tile space for game tiles...
        this.tray = new ArrayList<>();
        for(int i = 0; i < letters.length(); i++) {
            tray.add(letters.charAt(i));
        }
    }

    public ArrayList<Character> getTray() {
        return tray;
    }

    @Override
    public String toString() {
        StringBuilder temp = new StringBuilder("Tray: ");
        for (Character letter: tray) {
            temp.append(letter);
        }

        return temp.toString();
    }
}
