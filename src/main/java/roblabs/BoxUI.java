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

import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;

public class BoxUI extends GridPane {

    private CellUI[] cells;
    private int id;
    private final int GAP = 5;
    private Game game;

    public BoxUI(int id, Game game) {
        this.game = game;
        this.cells = new CellUI[9];
        this.id = id;
        for (int i = 0; i < 9; i++) {
            this.cells[i] = new CellUI(i, this.id, this.game.geCell(id, i).getBigNumber(), this.game);
            this.add(this.cells[i], i % 3, i / 3);
        }
        this.setHgap(GAP * 2);
        this.setVgap(GAP * 2);
        this.setPadding(new Insets(GAP, GAP, GAP, GAP));
        this.setStyle("-fx-border-style:solid; -fx-border-color:grey");
    }

    // Passes updatesCellUI method through to each CellUI instance
    public void updateCellUI(int cellId, int bigNumber, boolean fixedNumber, boolean isWrong, ArrayList<Integer> smallNumbers, boolean showBigNumber) {
        this.cells[cellId].updateCellUI(bigNumber, fixedNumber, isWrong, smallNumbers, showBigNumber);
    }

}
