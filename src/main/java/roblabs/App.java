package roblabs;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
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
        Menu menu1 = new Menu("Game");
        MenuItem newGameMenuItem = new MenuItem("New Game");
        MenuItem saveMenuItem = new MenuItem("Save");
        MenuItem loadMenuItem = new MenuItem("Load");
        MenuBar menuBar = new MenuBar();
        menu1.getItems().addAll(newGameMenuItem, saveMenuItem, loadMenuItem);
        menuBar.getMenus().add(menu1);

        VBox vBox = new VBox(menuBar, boardUI);
        scene = new Scene(vBox);
        stage.setScene(scene);
        stage.show();
        stage.setTitle("Robindoku 0.1");
        stage.setResizable(false);


    }

    public static void main(String[] args) {
        launch();
    }

}