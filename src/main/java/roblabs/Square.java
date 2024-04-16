package roblabs;

import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ContextMenu;

public class Square extends GridPane {

    private Label[] smallNumbers;
    private Label bigNumber;
    private int id;
    private int parentId;
    private ContextMenu contextMenuLeft;
    private ContextMenu contextMenuRight;
    private boolean fixedNumber;
    private Grid grid;
    private static Color fixedNumberColor = Color.BLACK;
    private static Color changeableNumberColor = Color.GRAY;
    private static Color wrongNumberColor = Color.RED;

    public Square(int id, int parentId, int number, Grid grid) {

        this.smallNumbers = new Label[9];
        this.bigNumber = new Label();
        this.id = id;
        this.parentId = parentId;
        this.grid = grid;
        this.setStyle("-fx-border-style:solid; -fx-background-color:white; -fx-border-color:grey;");

        // If number is 0, it should be an empty square that can be changed
        if (number == 0) {
            this.fixedNumber = false;
            this.bigNumber.setTextFill(changeableNumberColor);
            this.bigNumber.setText(" ");

            // Create context menu for left mouse click
            this.contextMenuLeft = new ContextMenu();
            MenuItem[] menuItemsLeft = new MenuItem[10];
            // Add option to clear square
            menuItemsLeft[0] = new MenuItem("Clear");
            this.contextMenuLeft.getItems().add(menuItemsLeft[0]);
            menuItemsLeft[0].setOnAction((event) -> {
                clearSquare();
                showBigNumber();
            });
            // Add numbers from 1 to 9
            for (int i = 1; i < 10; i++) {
                menuItemsLeft[i] = new MenuItem(String.valueOf(i));
                this.contextMenuLeft.getItems().add(menuItemsLeft[i]);
                final int num = i;
                menuItemsLeft[i].setOnAction((event) -> {
                    setBigNumber(num);
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
                    addSmallNumber(num);
                });
            }

            // Enable context menus
            this.setOnMouseClicked((event) -> {
                if (String.valueOf(event.getButton()).equals("PRIMARY")) {
                    this.contextMenuLeft.show(this, event.getScreenX(), event.getScreenY());
                } else if (String.valueOf(event.getButton()).equals("SECONDARY")) {
                    this.contextMenuRight.show(this, event.getScreenX(), event.getScreenY());
                } else if (String.valueOf(event.getButton()).equals("MIDDLE")) {
                    clearSquare();
                    showBigNumber();
                }
            });


        // If number is not 0, it should be non changeable
        } else {
            this.fixedNumber = true;
            this.bigNumber.setTextFill(fixedNumberColor);
            this.bigNumber.setText(String.valueOf(number));
        }

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

    public void clearSquare() {
        this.bigNumber.setText(" ");
        this.grid.setAndCheckSquare(this.id, this.parentId, 0);
        for (int i = 0; i < this.smallNumbers.length; i++) {
            this.smallNumbers[i].setText(" ");
        }
    }

    public void setBigNumber(int number) {
        this.bigNumber.setTextFill(changeableNumberColor);
        this.bigNumber.setText(String.valueOf(number));
        boolean numberIsCorrect = this.grid.setAndCheckSquare(this.parentId, this.id, number);
        if (numberIsCorrect) {
            this.bigNumber.setTextFill(changeableNumberColor);
        } else {
            this.bigNumber.setTextFill(wrongNumberColor);
        }
        showBigNumber();
    }

    public void addSmallNumber(int number) {
        if (this.smallNumbers[number - 1].getText().equals(" ")) {
            this.smallNumbers[number - 1].setText(String.valueOf(number));
        } else {
            this.smallNumbers[number - 1].setText(" ");
        }
        showSmallNumbers();
    }

    public void showBigNumber() {
        this.getChildren().removeAll(smallNumbers);
        this.getChildren().removeAll(bigNumber);
        for (int i = 0; i < this.smallNumbers.length; i++) {
            this.smallNumbers[i].setText(" ");
        }
        this.add(this.bigNumber, 0, 0);
    }

    public void showSmallNumbers() {
        this.getChildren().removeAll(smallNumbers);
        this.getChildren().removeAll(bigNumber);
        for (int i = 0; i < this.smallNumbers.length; i++) {
            this.add(this.smallNumbers[i], i % 3, i / 3);
        }
    }
}
