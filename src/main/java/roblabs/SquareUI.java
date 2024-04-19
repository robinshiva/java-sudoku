package roblabs;

import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ContextMenu;
import java.util.ArrayList;

public class SquareUI extends GridPane {

    private Label[] smallNumbers;
    private Label bigNumber;
    private boolean fixedNumber;
    private int id;
    private int parentId;
    private ContextMenu contextMenuLeft;
    private ContextMenu contextMenuRight;
    private Game game;
    private static Color fixedNumberColor = Color.BLACK;
    private static Color changeableNumberColor = Color.GRAY;
    public static Color wrongNumberColor = Color.RED;

    public SquareUI(int id, int parentId, int number, Game game) {

        this.smallNumbers = new Label[9];
        this.bigNumber = new Label();
        this.id = id;
        this.parentId = parentId;
        this.game = game;
        this.setStyle("-fx-border-style:solid; -fx-background-color:white; -fx-border-color:grey;");

        // If number is 0, it should be an empty square that can be changed
        if (number == 0) {
            this.bigNumber.setTextFill(changeableNumberColor);
            this.bigNumber.setText(" ");
            this.fixedNumber = false;
        // If number is not 0, it should be non changeable
        } else {
            this.bigNumber.setTextFill(fixedNumberColor);
            this.bigNumber.setText(String.valueOf(number));
            this.fixedNumber = true;
        }

        // Create context menu for left mouse click
        this.contextMenuLeft = new ContextMenu();
        MenuItem[] menuItemsLeft = new MenuItem[10];
        // Add option to clear square
        menuItemsLeft[0] = new MenuItem("Clear");
        this.contextMenuLeft.getItems().add(menuItemsLeft[0]);
        menuItemsLeft[0].setOnAction((event) -> {
            this.game.clearSquare(this.parentId, this.id);
        });

        // Add numbers from 1 to 9
        for (int i = 1; i < 10; i++) {
            menuItemsLeft[i] = new MenuItem(String.valueOf(i));
            this.contextMenuLeft.getItems().add(menuItemsLeft[i]);
            final int num = i;
            menuItemsLeft[i].setOnAction((event) -> {
                this.game.setBigNumber(this.parentId, this.id, num);
            });
        }

        // Create context menu for right mouse click
        this.contextMenuRight = new ContextMenu();
        MenuItem[] menuItemsRight = new MenuItem[9];
        for (int i = 1; i < 10; i++) {
            menuItemsRight[i - 1] = new MenuItem(String.valueOf(i));
            this.contextMenuRight.getItems().add(menuItemsRight[i - 1]);
            final int num = i;
            menuItemsRight[i - 1].setOnAction((event) -> {
                this.game.addSmallNumber(this.parentId, this.id, num);
            });
        }

        // Enable context menus
        this.setOnMouseClicked((event) -> {
            if (!this.fixedNumber) {
                if (String.valueOf(event.getButton()).equals("PRIMARY")) {
                    this.contextMenuLeft.show(this, event.getScreenX(), event.getScreenY());
                } else if (String.valueOf(event.getButton()).equals("SECONDARY")) {
                    this.contextMenuRight.show(this, event.getScreenX(), event.getScreenY());
                } else if (String.valueOf(event.getButton()).equals("MIDDLE")) {
                    this.game.clearSquare(this.parentId, this.id);
                }
            }
        });

        // Configure and show bigNumber label
        this.add(this.bigNumber, 0, 0);
        this.bigNumber.setAlignment(Pos.CENTER);
        this.bigNumber.setFont(new Font("Arial", 28));
        this.bigNumber.setMinWidth(60);
        this.bigNumber.setMinHeight(60);

        // Configure smallNumber labels
        for (int i = 0; i < this.smallNumbers.length; i++) {
            this.smallNumbers[i] = new Label();
            this.smallNumbers[i].setAlignment(Pos.CENTER);
            this.smallNumbers[i].setTextFill(changeableNumberColor);
            this.smallNumbers[i].setFont(new Font("Arial", 15));
            this.smallNumbers[i].setMinWidth(20);
            this.smallNumbers[i].setMinHeight(20);
            this.smallNumbers[i].setText(" ");
        }
    }

    public void updateSquareUI(int bigNumber, boolean fixedNumber, boolean isWrong, ArrayList<Integer> smallNumbers, boolean showBigNumber) {
        this.fixedNumber = fixedNumber;
        if (showBigNumber) {
            displayBigNumber(bigNumber, isWrong);
        } else {
            displaySmallNumbers(smallNumbers);
        }
    }

    public void displayBigNumber(int newBigNumber, boolean isWrong) {
        this.getChildren().removeAll(smallNumbers);
        this.getChildren().removeAll(bigNumber);
        if (isWrong) {
            this.bigNumber.setTextFill(wrongNumberColor);
        } else if (this.fixedNumber) {
            this.bigNumber.setTextFill(fixedNumberColor);
        } else {
            this.bigNumber.setTextFill(changeableNumberColor);
        }

        if (newBigNumber == 0) {
            this.bigNumber.setText(" ");
        } else {
            this.bigNumber.setText(String.valueOf(newBigNumber));
        }

        this.add(this.bigNumber, 0, 0);
    }

    public void displaySmallNumbers(ArrayList<Integer> newSmallNumbers) {
        this.getChildren().removeAll(smallNumbers);
        this.getChildren().removeAll(bigNumber);
        for (int i = 0; i < this.smallNumbers.length; i++) {
            this.smallNumbers[i].setText(" ");
        }
        for (int i = 0; i < newSmallNumbers.size(); i++) {
            int j = newSmallNumbers.get(i);
            this.smallNumbers[j - 1].setText(String.valueOf(j));
        }
        for (int i = 0; i < this.smallNumbers.length; i++) {
            this.add(this.smallNumbers[i], i % 3, i / 3);
        }
    }
}
