package roblabs;

import java.lang.Math;
import java.util.HashSet;
import java.util.Set;

public class Grid {

    private int[][] puzzleGrid;

    public Grid() {

        this.puzzleGrid = new int[9][9];

        int[][] puzzle = { 
            {1, 9, 0, 0, 0, 0, 0, 0, 0},
            {5, 0, 0, 0, 0, 0, 0, 0, 0},
            {7, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 5, 0, 0, 6, 0, 7, 2},
            {0, 6, 1, 8, 7, 0, 0, 4, 0},
            {0, 8, 7, 2, 0, 0, 0, 1, 5},
            {3, 0, 2, 6, 0, 0, 0, 0, 9},
            {0, 0, 4, 0, 0, 0, 3, 8, 0},
            {0, 0, 0, 0, 0, 1, 0, 0, 0},
        };

        for (int i = 0; i < puzzle[0].length; i++) {
            for (int j = 0; j < puzzle[0].length; j++) {
                this.puzzleGrid[i][j] = puzzle[i][j];
            }
        }
    }

    public int[] getBox(int boxId) {
        int[] boxNumbers = new int[9];
        int counter = 0;
        int col = (boxId % 3) * 3;
        int row = (int) Math.floor((boxId / 3)) * 3;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                boxNumbers[counter] = this.puzzleGrid[row][col];
                counter++;
                col++;
            }
            col = (boxId % 3) * 3;
            row++;
        }
        return boxNumbers;

    }

    public int getSquare(int boxId, int squareId) {
        return this.getBox(boxId)[squareId];
    }

    public boolean setAndCheckSquare(int boxId, int squareId, int number) {
        Set<Integer> nums = new HashSet<>();

        int[] boxNums = this.getBox(boxId);
        for (int i = 0; i < boxNums.length; i++) {
            nums.add(boxNums[i]);
        }

        int[] rowNums = this.getRow(boxId, squareId);
        for (int i = 0; i < rowNums.length; i++) {
            nums.add(rowNums[i]);
        }

        int[] colNums = this.getCol(boxId, squareId);
        for (int i = 0; i < colNums.length; i++) {
            nums.add(colNums[i]);
        }

        this.getBox(boxId)[squareId] = number;
        if (nums.contains(number)) {
            return false;
        } else {
            return true;
        }
    }

    public int[] getRow(int boxId, int squareId) {
        int rowIndex = (int) Math.floor(boxId / 3) + (int) Math.floor(squareId / 3);
        return this.puzzleGrid[rowIndex];
    }

    public int[] getCol(int boxId, int squareId) {
        int colIndex = (boxId % 3) * 3 + (squareId % 3);
        int[] colNums = new int[9];
        for (int i = 0; i < colNums.length; i++) {
            colNums[i] = this.puzzleGrid[i][colIndex];
        }
        return colNums;
    }
    


}
