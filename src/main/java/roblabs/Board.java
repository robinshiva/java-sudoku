package roblabs;

import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;

public class Board extends GridPane {

    private Box[] boxes;
    private final int GAP = 10;
    private Grid grid;

    public Board(Grid grid) {
        this.boxes = new Box[9];
        this.grid = grid;
        for (int i = 0; i < 9; i++) {
            this.boxes[i] = new Box(i, this.grid);
            this.add(this.boxes[i], i % 3, i / 3);
        }
        this.setHgap(GAP);
        this.setVgap(GAP);
        this.setPadding(new Insets(GAP, GAP, GAP, GAP));
    }

    public Box getBox(int id) {
        return this.boxes[id];
    }

}
