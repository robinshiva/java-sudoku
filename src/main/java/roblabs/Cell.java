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

import java.io.Serializable;
import java.util.ArrayList;

public class Cell implements Serializable {

    private int bigNumber;
    private ArrayList<Integer> smallNumbers;
    private boolean fixedNumber;
    private boolean isWrong;

    public Cell(int bigNumber) {
        this.bigNumber = bigNumber;
        this.smallNumbers = new ArrayList<>();
        this.isWrong = false;
        if (this.bigNumber == 0) {
            this.fixedNumber = false;
        } else {
            this.fixedNumber = true;
        }
    }

    public int getBigNumber() {
        return this.bigNumber;
    }

    public void setBigNumber(int newNumber, boolean isWrong) {
        this.bigNumber = newNumber;
        this.smallNumbers.clear();
        this.isWrong = isWrong;
    }

    public ArrayList<Integer> getSmallNumbers() {
        return this.smallNumbers;
    }

    public void addSmallNumber(int newNumber) {
        this.bigNumber = 0;
        if (this.smallNumbers.contains(newNumber)) {
            for (int i = 0; i < this.smallNumbers.size(); i++) {
                if (this.smallNumbers.get(i) == newNumber) {
                    this.smallNumbers.remove(i);
                }
            }
        } else {
            this.smallNumbers.add(newNumber);
        }
    }

    public void clear() {
        this.bigNumber = 0;
        this.smallNumbers.clear();
        this.isWrong = false;
    }

    public boolean getIsWrong() {
        return this.isWrong;
    }

    public boolean getFixedNumber() {
        return this.fixedNumber;
    }

}
