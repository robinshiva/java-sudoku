/*
<A small Sudoku app that comes with a library of puzzles to solve>
Copyright (C) 2024 Robin Hildebrand

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <https://www.gnu.org/licenses/>.
*/
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
    // Passes updateCellUI method to all boxUI instances
    public void updateCellUI(int boxId, int cellId, int bigNumber, boolean fixedNumber, boolean isWrong, ArrayList<Integer> smallNumbers, boolean showBigNumber) {
        this.boxes[boxId].updateCellUI(cellId, bigNumber, fixedNumber, isWrong, smallNumbers, showBigNumber);
    }

}
