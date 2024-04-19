package roblabs;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
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

    @Override
    public void start(Stage stage) throws IOException {
        this.game = new Game();
        this.boardUI = new BoardUI(this.game);
        this.game.giveBoardUI(boardUI);

        // Create menu
        Menu gameMenu = new Menu("Game");
        Menu settingsMenu = new Menu("Settings");
        MenuItem newGameMenuItem = new MenuItem("New Game");
        MenuItem saveMenuItem = new MenuItem("Save");
        MenuItem loadMenuItem = new MenuItem("Load");
        CheckMenuItem wrongNumberColorSettingItem = new CheckMenuItem("Show wrong numbers");
        wrongNumberColorSettingItem.setSelected(true);;
        saveMenuItem.setOnAction((event) -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save To File");
            fileChooser.getExtensionFilters().addAll(
                    new ExtensionFilter("Save Files", "*.ser"),
                    new ExtensionFilter("All Files", "*.*"));
            File file = fileChooser.showSaveDialog(stage);
            this.game.saveGame(file);
        });
        loadMenuItem.setOnAction((event) -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Save File");
            fileChooser.getExtensionFilters().add(
                    new ExtensionFilter("Save Files", "*.ser"));
            File file = fileChooser.showOpenDialog(stage);
            this.game.loadGame(file);
        });
        newGameMenuItem.setOnAction((event) -> {
            ChoiceDialog<Difficulty> difficultyChoice = new ChoiceDialog<>(Difficulty.MEDIUM, Difficulty.EASY, Difficulty.MEDIUM, Difficulty.HARD);
            difficultyChoice.setContentText("Difficulty: ");
            difficultyChoice.setHeaderText("Choose a difficulty for the new game");
            difficultyChoice.showAndWait();
            this.game.newGame(difficultyChoice.getSelectedItem());
            stage.setTitle("Robindoku 0.3 - Difficulty: " + this.game.getDifficulty());
        });
        wrongNumberColorSettingItem.setOnAction((event) -> {
            this.game.showWrongNumberSetting(wrongNumberColorSettingItem.isSelected());
            this.game.updateWholeUI();
        });
        // Implement new game functionality
        MenuBar menuBar = new MenuBar();
        gameMenu.getItems().addAll(newGameMenuItem, saveMenuItem, loadMenuItem);
        settingsMenu.getItems().add(wrongNumberColorSettingItem);
        menuBar.getMenus().addAll(gameMenu, settingsMenu);

        VBox vBox = new VBox(menuBar, boardUI);
        scene = new Scene(vBox);
        stage.setScene(scene);
        stage.show();
        stage.setTitle("Robindoku 0.3 - Difficulty: " + this.game.getDifficulty());
        stage.setResizable(false);
    }

    public static void main(String[] args) {
        launch();
    }

}