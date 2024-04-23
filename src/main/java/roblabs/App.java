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

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Optional;
import java.io.File;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.layout.VBox;


public class App extends Application {

    private static Scene scene;
    private Game game;
    private BoardUI boardUI;
    private Stage stage;

    @Override
    public void start(Stage stage) throws IOException {

        this.game = new Game();
        this.boardUI = new BoardUI(this.game);
        this.game.giveBoardUI(boardUI);
        this.stage = stage;

        // Create menu
        Menu gameMenu = new Menu("Game");
        Menu settingsMenu = new Menu("Settings");
        MenuItem newGameMenuItem = new MenuItem("New Game");
        MenuItem saveMenuItem = new MenuItem("Save");
        MenuItem loadMenuItem = new MenuItem("Load");
        CheckMenuItem wrongNumberColorSettingItem = new CheckMenuItem("Show wrong numbers");

        // Option to save to file
        saveMenuItem.setOnAction((event) -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save To File");
            fileChooser.getExtensionFilters().addAll(
                    new ExtensionFilter("Save Files", "*.ser"),
                    new ExtensionFilter("All Files", "*.*"));
            File file = fileChooser.showSaveDialog(stage);
            this.game.saveGame(file);
        });

        // Option to load a game from a file
        loadMenuItem.setOnAction((event) -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Save File");
            fileChooser.getExtensionFilters().add(
                    new ExtensionFilter("Save Files", "*.ser"));
            File file = fileChooser.showOpenDialog(stage);
            this.game.loadGame(file);
            
        });

        // Option to start a new game and choosing a difficulty
        newGameMenuItem.setOnAction((event) -> {
            ChoiceDialog<Difficulty> difficultyChoice = new ChoiceDialog<>(Difficulty.MEDIUM, Difficulty.EASY, Difficulty.MEDIUM, Difficulty.HARD);
            difficultyChoice.setTitle("New Game");
            difficultyChoice.setContentText("Difficulty: ");
            difficultyChoice.setHeaderText("Choose a difficulty for the new game");
            Optional<Difficulty> choice = difficultyChoice.showAndWait();
            if (choice.isPresent()) {
                this.game.newGame(difficultyChoice.getSelectedItem());
                updateTitle();
            }
        });

        // Option to show that numbers are wrong
        wrongNumberColorSettingItem.setSelected(true);
        wrongNumberColorSettingItem.setOnAction((event) -> {
            this.game.showWrongNumberSetting(wrongNumberColorSettingItem.isSelected());
            this.game.updateWholeUI();
        });

        // Add all items to menubar
        MenuBar menuBar = new MenuBar();
        gameMenu.getItems().addAll(newGameMenuItem, saveMenuItem, loadMenuItem);
        settingsMenu.getItems().add(wrongNumberColorSettingItem);
        menuBar.getMenus().addAll(gameMenu, settingsMenu);

        // Draw scene
        VBox vBox = new VBox(menuBar, boardUI);
        scene = new Scene(vBox);
        this.stage.setScene(scene);
        this.stage.show();
        this.stage.setResizable(false);
        updateTitle();
    }

    public static void main(String[] args) {
        launch();
    }

    // Updates the title with difficulty of the current game
    public void updateTitle() {
        this.stage.setTitle("Robindoku v0.3 - " + this.game.getDifficulty());
    }
}