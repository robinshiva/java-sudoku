package roblabs;

import java.lang.Math;
import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;

public class Game {

    private Square[][] board;
    private BoardUI boardUI;

    public Game() {

        this.board = new Square[9][9];

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
                this.board[i][j] = new Square(puzzle[i][j]);
            }
        }
    }

    public void giveBoardUI(BoardUI boardUI) {
        this.boardUI = boardUI;
    }

    public int[] getBoxNumbers(int boxId) {
        int[] boxNumbers = new int[9];
        int counter = 0;
        int col = (boxId % 3) * 3;
        int row = (int) Math.floor((boxId / 3)) * 3;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                boxNumbers[counter] = this.board[row][col].getBigNumber();
                counter++;
                col++;
            }
            col = (boxId % 3) * 3;
            row++;
        }
        return boxNumbers;
    }

    public Square getSquare(int boxId, int squareId) {
        int col = (boxId % 3) * 3 + (squareId % 3);
        int row = (int) Math.floor(boxId / 3) * 3 + (int) Math.floor(squareId / 3);
        return this.board[row][col];
    }

    public boolean checkSquareIsWrong(int boxId, int squareId, int number) {
        Set<Integer> nums = new HashSet<>();
        int[] boxNums = this.getBoxNumbers(boxId);

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

        this.getBoxNumbers(boxId)[squareId] = number;
        if (nums.contains(number)) {
            return true;
        } else {
            return false;
        }
    }

    public int[] getRow(int boxId, int squareId) {
        int rowIndex = (int) Math.floor(boxId / 3) + (int) Math.floor(squareId / 3);
        int[] rowNums = new int[9];
        for (int i = 0; i < rowNums.length; i++) {
            rowNums[i] = this.board[rowIndex][i].getBigNumber();
        }
        return rowNums;
    }

    public int[] getCol(int boxId, int squareId) {
        int colIndex = (boxId % 3) * 3 + (squareId % 3);
        int[] colNums = new int[9];
        for (int i = 0; i < colNums.length; i++) {
            colNums[i] = this.board[i][colIndex].getBigNumber();
        }
        return colNums;
    }

    public void clearSquare(int boxId, int squareId) {
        getSquare(boxId, squareId).clear();
        updateSquareUI(boxId, squareId, true);
    }

    public void setBigNumber(int boxId, int squareId, int newNumber) {
        boolean isWrong = checkSquareIsWrong(boxId, squareId, newNumber);
        getSquare(boxId, squareId).setBigNumber(newNumber, isWrong);
        updateSquareUI(boxId, squareId, true);
    }

    public void addSmallNumber(int boxId, int squareId, int newNumber) {
        getSquare(boxId, squareId).addSmallNumber(newNumber);
        updateSquareUI(boxId, squareId, false);
    }

    public void updateSquareUI(int boxId, int squareId, boolean showBigNumber) {
        Square square = getSquare(boxId, squareId);
        int bigNumber = square.getBigNumber();
        boolean isWrong = square.getIsWrong();
        ArrayList<Integer> smallNumbers = square.getSmallNumbers();
        this.boardUI.updateSquareUI(boxId, squareId, bigNumber, isWrong, smallNumbers, showBigNumber);
    }
}
