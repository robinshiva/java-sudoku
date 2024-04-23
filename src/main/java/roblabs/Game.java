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

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.File;
import java.lang.Math;
import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;
import javafx.scene.paint.Color;

public class Game {

    private Cell[][] board;
    private BoardUI boardUI;
    private Difficulty difficulty;

    public Game() {

        this.board = new Cell[9][9];
        this.difficulty = Difficulty.MEDIUM;
        
        String puzzleString = getNewGame(difficulty);;
        int k = 0;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                this.board[i][j] = new Cell(Character.getNumericValue(puzzleString.charAt(k)));
                k++;
            }
        }
    }

    // Passes the boardUI into the Game class to enable UI control
    public void giveBoardUI(BoardUI boardUI) {
        this.boardUI = boardUI;
    }

    // Returns all numbers of a 3x3 box with a specified boxId as an int array
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

    // Returns an individual cell number when given the boxId and cellId
    public Cell geCell(int boxId, int cellId) {
        int col = (boxId % 3) * 3 + (cellId % 3);
        int row = (int) Math.floor(boxId / 3) * 3 + (int) Math.floor(cellId / 3);
        return this.board[row][col];
    }

    // Checks if a newly added number is correct according to sudoku rules
    public boolean checkIfCellIsWrong(int boxId, int cellId, int number) {
        Set<Integer> nums = new HashSet<>();
        int[] boxNums = this.getBoxNumbers(boxId);

        for (int i = 0; i < boxNums.length; i++) {
            nums.add(boxNums[i]);
        }

        int[] rowNums = this.getRow(boxId, cellId);
        for (int i = 0; i < rowNums.length; i++) {
            nums.add(rowNums[i]);
        }

        int[] colNums = this.getCol(boxId, cellId);
        for (int i = 0; i < colNums.length; i++) {
            nums.add(colNums[i]);
        }

        this.getBoxNumbers(boxId)[cellId] = number;
        if (nums.contains(number)) {
            return true;
        } else {
            return false;
        }
    }

    // Returns all numbers of a row as an int array
    public int[] getRow(int boxId, int cellId) {
        int rowIndex = (int) Math.floor(boxId / 3) * 3 + (int) Math.floor(cellId / 3);
        int[] rowNums = new int[9];
        for (int i = 0; i < rowNums.length; i++) {
            rowNums[i] = this.board[rowIndex][i].getBigNumber();
        }
        return rowNums;
    }

    // Returns all numbers of a column as an int array
    public int[] getCol(int boxId, int cellId) {
        int colIndex = (boxId % 3) * 3 + (cellId % 3);
        int[] colNums = new int[9];
        for (int i = 0; i < colNums.length; i++) {
            colNums[i] = this.board[i][colIndex].getBigNumber();
        }
        return colNums;
    }

    // Resets a cell and updates the UI
    public void clearCell(int boxId, int cellId) {
        geCell(boxId, cellId).clear();
        updateCellUI(boxId, cellId, true);
    }

    // Writes a big number to a cell and update the UI
    public void setBigNumber(int boxId, int cellId, int newNumber) {
        boolean isWrong = checkIfCellIsWrong(boxId, cellId, newNumber);
        geCell(boxId, cellId).setBigNumber(newNumber, isWrong);
        updateCellUI(boxId, cellId, true);
    }

    // Adds (or removes if it was already there) a small number to a cell and update the ui
    public void addSmallNumber(int boxId, int cellId, int newNumber) {
        geCell(boxId, cellId).addSmallNumber(newNumber);
        updateCellUI(boxId, cellId, false);
    }

    // Updates the UI of a given cell by calling the updateCellUI method of the boardUI
    public void updateCellUI(int boxId, int cellId, boolean showBigNumber) {
        Cell cell = geCell(boxId, cellId);
        int bigNumber = cell.getBigNumber();
        boolean fixedNumber = cell.getFixedNumber();
        boolean isWrong = cell.getIsWrong();
        ArrayList<Integer> smallNumbers = cell.getSmallNumbers();
        this.boardUI.updateCellUI(boxId, cellId, bigNumber, fixedNumber, isWrong, smallNumbers, showBigNumber);
    }

    // Updates the whole UI by calling the updateCellUI method for every cell
    public void updateWholeUI() {
        for (int i = 0; i < this.board.length; i++) {
            for (int j = 0; j < this.board[0].length; j++) {
                boolean showBigNumber = false;
                if (this.geCell(i, j).getSmallNumbers().isEmpty()) {
                    showBigNumber = true;
                }
                updateCellUI(i, j, showBigNumber);
            }
        }
    }

    // Generates a new game
    public String getNewGame(Difficulty difficulty) {
        String fileName = new String();
        String gamePuzzle = "";
        int lines = 100000;
        switch (difficulty) {
            case EASY:
                fileName = "easy.txt";
                lines = 100000;
                break;
            case MEDIUM:
                fileName = "medium.txt";
                lines = 352643;
                break;
            case HARD:
                fileName = "hard.txt";
                lines = 321592;
                break;
        }
        try {
            FileReader fileReader = new FileReader("./src/main/resources/roblabs/puzzles/" + fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String newLine;
            int lineChosen = (int) (Math.random() * lines);
            int counter = 0;
            while ((newLine = bufferedReader.readLine()) != null) {
                if (lineChosen == counter) {
                    gamePuzzle = newLine.split(" ")[1];
                    break;
                }
                counter++;
            }
            bufferedReader.close();
            fileReader.close();
        } catch (IOException i) {
            i.printStackTrace();
        }
        return gamePuzzle;
    }

    // Gets and starts a new game
    public void newGame(Difficulty difficulty) {
        String newGameBoardString = getNewGame(difficulty);;
        int k = 0;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                this.board[i][j] = new Cell(Character.getNumericValue(newGameBoardString.charAt(k)));
                k++;
            }
        }
        this.difficulty = difficulty;
        updateWholeUI();
    }

    // Saves a game to a file
    public void saveGame(File file) {
        try {
            FileOutputStream fileOut = new FileOutputStream(file);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            for (int i = 0; i < this.board.length; i++) {
                for (int j = 0; j < this.board[0].length; j++) {
                    out.writeObject(board[i][j]);
                }
            }
            out.writeObject(this.difficulty);
            out.close();
            fileOut.close();
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    // Loads a game from a file
    public void loadGame(File file) {
        try {
            FileInputStream fileIn = new FileInputStream(file);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            for (int i = 0; i < this.board.length; i++) {
                for (int j = 0; j < this.board[0].length; j++) {
                    this.board[i][j] = (Cell) in.readObject();
                }
            }
            this.difficulty = (Difficulty) in.readObject();
            in.close();
            fileIn.close();
            updateWholeUI();
        } catch (IOException i) {
            i.printStackTrace();
            return;
        } catch (ClassNotFoundException c) {
            c.printStackTrace();
            return;
        }
    }

    // Sets the wrong number color to gray if the setting is changed to false/off
    public void showWrongNumberSetting(boolean showWrongNumber) {
        if (showWrongNumber) {
            CellUI.wrongNumberColor = Color.RED;
        } else {
            CellUI.wrongNumberColor = Color.GRAY;
        }

    }

    public Difficulty getDifficulty() {
        return this.difficulty;
    }
}
