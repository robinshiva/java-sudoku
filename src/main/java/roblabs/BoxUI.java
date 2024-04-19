package roblabs;

import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;

public class BoxUI extends GridPane {

    private SquareUI[] squares;
    private int id;
    private final int GAP = 5;
    private Game game;

    public BoxUI(int id, Game game) {
        this.game = game;
        this.squares = new SquareUI[9];
        this.id = id;
        for (int i = 0; i < 9; i++) {
            this.squares[i] = new SquareUI(i, this.id, this.game.getSquare(id, i).getBigNumber(), this.game);
            this.add(this.squares[i], i % 3, i / 3);
        }
        this.setHgap(GAP * 2);
        this.setVgap(GAP * 2);
        this.setPadding(new Insets(GAP, GAP, GAP, GAP));
        this.setStyle("-fx-border-style:solid; -fx-border-color:grey");
    }

    // Passes updatesSquareUI method through to each SquareUI instance
    public void updateSquareUI(int squareId, int bigNumber, boolean fixedNumber, boolean isWrong, ArrayList<Integer> smallNumbers, boolean showBigNumber) {
        this.squares[squareId].updateSquareUI(bigNumber, fixedNumber, isWrong, smallNumbers, showBigNumber);
    }

}
