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

    private Square[][] board;
    private BoardUI boardUI;
    private Difficulty difficulty;

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
        this.difficulty = Difficulty.MEDIUM;
        String puzzleString = getNewGame(difficulty);;
        int k = 0;
        for (int i = 0; i < puzzle[0].length; i++) {
            for (int j = 0; j < puzzle[0].length; j++) {
                this.board[i][j] = new Square(Character.getNumericValue(puzzleString.charAt(k)));
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

    // Returns an individual square number when given the boxId and squareId
    public Square getSquare(int boxId, int squareId) {
        int col = (boxId % 3) * 3 + (squareId % 3);
        int row = (int) Math.floor(boxId / 3) * 3 + (int) Math.floor(squareId / 3);
        return this.board[row][col];
    }

    // Checks if a newly added number is correct according to sudoku rules
    public boolean checkIfSquareIsWrong(int boxId, int squareId, int number) {
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

    // Returns all numbers of a row as an int array
    public int[] getRow(int boxId, int squareId) {
        int rowIndex = (int) Math.floor(boxId / 3) * 3 + (int) Math.floor(squareId / 3);
        int[] rowNums = new int[9];
        for (int i = 0; i < rowNums.length; i++) {
            rowNums[i] = this.board[rowIndex][i].getBigNumber();
        }
        return rowNums;
    }

    // Returns all numbers of a column as an int array
    public int[] getCol(int boxId, int squareId) {
        int colIndex = (boxId % 3) * 3 + (squareId % 3);
        int[] colNums = new int[9];
        for (int i = 0; i < colNums.length; i++) {
            colNums[i] = this.board[i][colIndex].getBigNumber();
        }
        return colNums;
    }

    // Resets a square and updates the UI
    public void clearSquare(int boxId, int squareId) {
        getSquare(boxId, squareId).clear();
        updateSquareUI(boxId, squareId, true);
    }

    // Writes a big number to a square and update the UI
    public void setBigNumber(int boxId, int squareId, int newNumber) {
        boolean isWrong = checkIfSquareIsWrong(boxId, squareId, newNumber);
        getSquare(boxId, squareId).setBigNumber(newNumber, isWrong);
        updateSquareUI(boxId, squareId, true);
    }

    // Adds (or removes if it was already there) a small number to a square and update the ui
    public void addSmallNumber(int boxId, int squareId, int newNumber) {
        getSquare(boxId, squareId).addSmallNumber(newNumber);
        updateSquareUI(boxId, squareId, false);
    }

    // Updates the UI of a given square by calling the updateSquareUI method of the boardUI
    public void updateSquareUI(int boxId, int squareId, boolean showBigNumber) {
        Square square = getSquare(boxId, squareId);
        int bigNumber = square.getBigNumber();
        boolean fixedNumber = square.getFixedNumber();
        boolean isWrong = square.getIsWrong();
        ArrayList<Integer> smallNumbers = square.getSmallNumbers();
        this.boardUI.updateSquareUI(boxId, squareId, bigNumber, fixedNumber, isWrong, smallNumbers, showBigNumber);
    }

    // Updates the whole UI by calling the updateSquareUI method for every square
    public void updateWholeUI() {
        for (int i = 0; i < this.board.length; i++) {
            for (int j = 0; j < this.board[0].length; j++) {
                boolean showBigNumber = false;
                if (this.getSquare(i, j).getSmallNumbers().isEmpty()) {
                    showBigNumber = true;
                }
                updateSquareUI(i, j, showBigNumber);
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
                this.board[i][j] = new Square(Character.getNumericValue(newGameBoardString.charAt(k)));
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
                    this.board[i][j] = (Square) in.readObject();
                }
            }
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

    // Sets the wrong number color to gray if the setting is changed
    public void showWrongNumberSetting(boolean showWrongNumber) {
        if (showWrongNumber) {
            SquareUI.wrongNumberColor = Color.RED;
        } else {
            SquareUI.wrongNumberColor = Color.GRAY;
        }

    }

    public Difficulty getDifficulty() {
        return this.difficulty;
    }
}
