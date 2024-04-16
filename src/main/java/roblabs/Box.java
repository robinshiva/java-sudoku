package roblabs;

import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;

public class Box extends GridPane {

    private Square[] squares;
    private int id;
    private final int GAP = 5;
    private Grid grid;

    public Box(int id, Grid grid) {
        this.grid = grid;
        this.squares = new Square[9];
        this.id = id;
        for (int i = 0; i < 9; i++) {
            this.squares[i] = new Square(i, this.id, this.grid.getSquare(this.id, i), this.grid);
            this.add(this.squares[i], i % 3, i / 3);
        }
        this.setHgap(GAP * 2);
        this.setVgap(GAP * 2);
        this.setPadding(new Insets(GAP, GAP, GAP, GAP));
        this.setStyle("-fx-border-style:solid; -fx-border-color:grey");
    }

}
