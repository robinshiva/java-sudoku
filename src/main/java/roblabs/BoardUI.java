package roblabs;

import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import java.util.ArrayList;

public class BoardUI extends GridPane {

    private BoxUI[] boxes;
    private final int GAP = 10;
    private Game game;

    public BoardUI(Game game) {
        this.boxes = new BoxUI[9];
        this.game = game;
        for (int i = 0; i < 9; i++) {
            this.boxes[i] = new BoxUI(i, this.game);
            this.add(this.boxes[i], i % 3, i / 3);
        }
        this.setHgap(GAP);
        this.setVgap(GAP);
        this.setPadding(new Insets(GAP, GAP, GAP, GAP));
    }

    public BoxUI getBox(int id) {
        return this.boxes[id];
    }
    // Passes updateSquareUI method to all boxUI instances
    public void updateSquareUI(int boxId, int squareId, int bigNumber, boolean fixedNumber, boolean isWrong, ArrayList<Integer> smallNumbers, boolean showBigNumber) {
        this.boxes[boxId].updateSquareUI(squareId, bigNumber, fixedNumber, isWrong, smallNumbers, showBigNumber);
    }

}
